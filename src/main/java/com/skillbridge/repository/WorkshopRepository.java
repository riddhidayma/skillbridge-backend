package com.skillbridge.repository;

import com.skillbridge.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {

    // Custom query to find all workshops hosted by a specific mentor
    List<Workshop> findByMentorId(Long mentorId);
}