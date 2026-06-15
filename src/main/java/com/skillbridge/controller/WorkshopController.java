package com.skillbridge.controller;

import com.skillbridge.entity.Workshop;
import com.skillbridge.service.WorkshopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final WorkshopService workshopService;

    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    // API: POST http://localhost:8080/api/workshops/mentor/1
    // Notice how we pass the mentorId in the URL path
    @PostMapping("/mentor/{mentorId}")
    public ResponseEntity<Workshop> createWorkshop(@PathVariable Long mentorId, @RequestBody Workshop workshop) {
        Workshop savedWorkshop = workshopService.createWorkshop(mentorId, workshop);
        return ResponseEntity.ok(savedWorkshop);
    }

    // API: GET http://localhost:8080/api/workshops
    @GetMapping
    public ResponseEntity<List<Workshop>> getAllWorkshops() {
        List<Workshop> workshops = workshopService.getAllWorkshops();
        return ResponseEntity.ok(workshops);
    }

    // API: GET http://localhost:8080/api/workshops/1
    @GetMapping("/{id}")
    public ResponseEntity<Workshop> getWorkshopById(@PathVariable Long id) {
        Workshop workshop = workshopService.getWorkshopById(id);
        return ResponseEntity.ok(workshop);
    }
}