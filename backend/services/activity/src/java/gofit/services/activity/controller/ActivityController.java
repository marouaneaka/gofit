package gofit.services.activity.controller;

import gofit.services.activity.model.Activity;
import gofit.services.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService service;

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity savedActivity = service.saveActivity(activity);
        return ResponseEntity.status(201).body(savedActivity);
    }
}
