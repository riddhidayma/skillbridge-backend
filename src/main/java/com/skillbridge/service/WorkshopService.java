package com.skillbridge.service;

import com.skillbridge.entity.User;
import com.skillbridge.entity.Workshop;
import com.skillbridge.repository.UserRepository;
import com.skillbridge.repository.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final UserRepository userRepository;

    // Injecting both repositories
    public WorkshopService(WorkshopRepository workshopRepository, UserRepository userRepository) {
        this.workshopRepository = workshopRepository;
        this.userRepository = userRepository;
    }

    // CREATE WORKSHOP
    public Workshop createWorkshop(Long mentorId, Workshop workshop) {
        // 1. Find the user by ID
        User mentor = userRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + mentorId));

        // 2. Business Rule: Ensure the user is actually a Mentor
        if (!"MENTOR".equalsIgnoreCase(mentor.getRole())) {
            throw new RuntimeException("Only users with the MENTOR role can create workshops.");
        }

        // 3. Attach the mentor to the workshop and save
        workshop.setMentor(mentor);
        return workshopRepository.save(workshop);
    }

    // GET ALL WORKSHOPS
    public List<Workshop> getAllWorkshops() {
        return workshopRepository.findAll();
    }

    // GET WORKSHOP BY ID
    public Workshop getWorkshopById(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workshop not found with ID: " + id));
    }
}