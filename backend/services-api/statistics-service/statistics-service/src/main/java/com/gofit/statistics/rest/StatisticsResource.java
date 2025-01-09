package com.gofit.statistics.rest;

import com.gofit.statistics.model.Statistics;
import com.gofit.statistics.service.StatisticsService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatisticsResource {

    @Inject
    private StatisticsService statisticsService;

    @POST
    public void calculateStatistics() {
        statisticsService.calculateAndStoreStatistics();
    }

    @GET
    public Response getAllStatistics() {
        List<Statistics> statistics = statisticsService.getAllStatistics();
        return Response.ok(statistics).build();
    }

    @GET
    @Path("/{id}")
    public Response getStatisticById(@PathParam("id") Long id) {
        Statistics statistic = statisticsService.getStatisticById(id);
        if (statistic != null) {
            return Response.ok(statistic).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Statistic with ID " + id + " not found")
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


