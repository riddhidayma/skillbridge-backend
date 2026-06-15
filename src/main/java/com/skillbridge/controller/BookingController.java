package com.skillbridge.controller;

import com.skillbridge.dto.BookingRequest;
import com.skillbridge.entity.Booking;
import com.skillbridge.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<Booking> bookWorkshop(@RequestBody BookingRequest request) {
        Booking booking = bookingService.createBooking(request.getLearnerId(), request.getWorkshopId());
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/learner/{learnerId}")
    public ResponseEntity<List<Booking>> getLearnerBookings(@PathVariable Long learnerId) {
        return ResponseEntity.ok(bookingService.getBookingsByLearner(learnerId));
    }
}
