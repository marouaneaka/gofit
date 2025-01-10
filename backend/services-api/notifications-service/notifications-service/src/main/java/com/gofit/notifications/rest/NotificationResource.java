package com.gofit.notifications.rest;

import com.gofit.notifications.model.Notification;
import com.gofit.notifications.service.NotificationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    private NotificationService notificationService;

    @POST
    public Response createNotification(Notification notification) {
        notificationService.createNotification(notification);
        return Response.status(Response.Status.CREATED).entity(notification).build();
    }

    @GET
    public Response getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return Response.ok(notifications).build();
    }

    @GET
    @Path("/{id}")
    public Response getNotificationById(@PathParam("id") Long id) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification != null) {
            return Response.ok(notification).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Notification with ID " + id + " not found")
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNotification(@PathParam("id") Long id) {
        boolean deleted = notificationService.deleteNotification(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Notification with ID " + id + " not found")
                           .build();
        }
    }

    // Méthode pour gérer les requêtes OPTIONS
    @OPTIONS
    @Path("{path:.*}")
    public Response handleOptions() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS")
                .build();
    }
}