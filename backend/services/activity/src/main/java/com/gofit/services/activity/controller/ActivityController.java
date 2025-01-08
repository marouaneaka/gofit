package com.gofit.services.activity.controller;

import com.gofit.services.activity.model.Activity;
import com.gofit.services.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class ActivityController {

    @Autowired
    private ActivityService service;
    
    @GetMapping
    public String mainRequest() {
        return "GoFit Activity Microservice";
    }
    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity savedActivity = service.saveActivity(activity);
        return ResponseEntity.status(201).body(savedActivity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        Optional<Activity> updatedActivity = service.updateActivity(id, activity);
        return updatedActivity.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        boolean deleted = service.deleteActivity(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}