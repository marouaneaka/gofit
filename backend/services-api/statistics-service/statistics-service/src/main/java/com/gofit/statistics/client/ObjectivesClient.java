package com.gofit.statistics.client;

import com.gofit.statistics.model.Objective;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
public class ObjectivesClient {
    private static final String BASE_URL = "http://localhost:8080/objectives-service";

    public List<Objective> getObjectives() {
        Client client = ClientBuilder.newClient();
        return client.target(BASE_URL + "/objectives")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Objective>>() {});
    }
}
