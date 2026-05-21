package com.skillbridge.controller;

import com.skillbridge.entity.Booking;
import com.skillbridge.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://skillbridge-sepia.vercel.app"
})
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // API: POST http://localhost:8080/api/bookings/book?learnerId=1&workshopId=2
    @PostMapping("/book")
    public ResponseEntity<Booking> bookWorkshop(@RequestParam Long learnerId, @RequestParam Long workshopId) {
        Booking booking = bookingService.createBooking(learnerId, workshopId);
        return ResponseEntity.ok(booking);
    }

    // API: GET http://localhost:8080/api/bookings/learner/1
    @GetMapping("/learner/{learnerId}")
    public ResponseEntity<List<Booking>> getLearnerBookings(@PathVariable Long learnerId) {
        return ResponseEntity.ok(bookingService.getBookingsByLearner(learnerId));
    }
}