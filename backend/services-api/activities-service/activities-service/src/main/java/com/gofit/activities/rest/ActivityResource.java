package com.gofit.activities.rest;

import com.gofit.activities.kafka.KafkaProducerService;
import com.gofit.activities.model.Activity;
import com.gofit.activities.repository.ActivityRepository;
import com.gofit.shared.ActivityEvent;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.inject.Inject;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivityResource {

    @Inject
    private ActivityRepository repository;
    
    @Inject
    private KafkaProducerService kafkaProducerService;

    @POST
    public Response create(Activity activity) {
        // Sauvegarder l'activité dans la base de données
        repository.save(activity);     

        // Créer un événement pour Kafka
        ActivityEvent event = new ActivityEvent(
            activity.getType(), 
            activity.getDistance(),
            activity.getDuration(), 
            activity.getCalories()
        );

        // Envoyer l'événement à Kafka
        kafkaProducerService.sendActivityEvent("activities-events", event);

        return Response.status(Response.Status.CREATED).entity(activity).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Activity activity = repository.findById(id);
        if (activity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(activity).build();
    }

    @GET
    public Response getAll() {
        List<Activity> activities = repository.findAll();
        return Response.ok(activities).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteActivity(@PathParam("id") Long id) {
        try {
            boolean deleted = repository.delete(id);
            if (deleted) {
                return Response.noContent().build(); // 204 No Content
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Activité non trouvée avec l'ID : " + id)
                               .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erreur lors de la suppression de l'activité.")
                           .build();
        }
    }

    // Méthode pour gérer les requêtes OPTIONS
    @OPTIONS
    @Path("{path:.*}")
    public Response handleOptions() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:8000")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .build();
    }
}

