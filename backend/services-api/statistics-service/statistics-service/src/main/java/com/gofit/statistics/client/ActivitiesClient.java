package com.gofit.statistics.client;

import com.gofit.statistics.model.Activity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
public class ActivitiesClient {
    private static final String BASE_URL = "http://localhost:8080/activities-service";

    public List<Activity> getActivities() {
        Client client = ClientBuilder.newClient();
        return client.target(BASE_URL + "/activities")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }
}
