package com.gofit.objectives.rest;

import com.gofit.objectives.kafka.KafkaProducerService;
import com.gofit.objectives.model.Objective;
import com.gofit.objectives.repository.ObjectiveRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObjectiveResource {

    @Inject
    private ObjectiveRepository objectiveRepository;

    @Inject
    private KafkaProducerService kafkaProducerService;

    @GET
    public Response getAllObjectives() {
        List<Objective> objectives = objectiveRepository.findAll();
        return Response.ok(objectives).header("Cache-Control", "no-cache, no-store, must-revalidate")
                   .header("Pragma", "no-cache")
                   .header("Expires", "0")
                   .build();

    }

    @GET
    @Path("/{id}")
    public Response getObjectiveById(@PathParam("id") Long id) {
        Objective objective = objectiveRepository.findById(id);
        if (objective == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(objective).header("Cache-Control", "no-cache, no-store, must-revalidate")
                   .header("Pragma", "no-cache")
                   .header("Expires", "0")
                   .build();
    }

    @POST
    public Response createObjective(Objective objective) {
        objectiveRepository.save(objective);

        // Produire un événement Kafka
        kafkaProducerService.sendMessage("objectives-events", null, "New objective created: " + objective);

        return Response.status(Response.Status.CREATED).entity(objective).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateObjective(@PathParam("id") Long id, Objective updatedObjective) {
        Objective existingObjective = objectiveRepository.findById(id);
        if (existingObjective == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingObjective.setGoalType(updatedObjective.getGoalType());
        existingObjective.setTargetValue(updatedObjective.getTargetValue());

        objectiveRepository.update(existingObjective);

        // Produire un événement Kafka
        kafkaProducerService.sendMessage("objectives-events", null, "Objective updated: " + existingObjective);

        return Response.ok(existingObjective).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteObjective(@PathParam("id") Long id) {
        Objective existingObjective = objectiveRepository.findById(id);
        if (existingObjective == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        objectiveRepository.delete(id);

        // Produire un événement Kafka
        kafkaProducerService.sendMessage("objectives-events", null, "Objective deleted with ID: " + id);

        return Response.noContent().build();
    }
}

