package com.skillbridge.service;

import com.skillbridge.entity.Booking;
import com.skillbridge.entity.User;
import com.skillbridge.entity.Workshop;
import com.skillbridge.repository.BookingRepository;
import com.skillbridge.repository.UserRepository;
import com.skillbridge.repository.WorkshopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final WorkshopRepository workshopRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, WorkshopRepository workshopRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.workshopRepository = workshopRepository;
    }

    // @Transactional is crucial here! If saving the booking fails,
    // the seat reduction is automatically undone.
    @Transactional
    public Booking createBooking(Long learnerId, Long workshopId) {
        User learner = userRepository.findById(learnerId)
                .orElseThrow(() -> new RuntimeException("Learner not found"));

        Workshop workshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new RuntimeException("Workshop not found"));

        if (workshop.getAvailableSeats() <= 0) {
            throw new RuntimeException("Workshop is completely sold out!");
        }

        // 1. Reduce the available seats and update the workshop
        workshop.setAvailableSeats(workshop.getAvailableSeats() - 1);
        workshopRepository.save(workshop);

        // 2. Create the booking record
        Booking booking = new Booking();
        booking.setLearner(learner);
        booking.setWorkshop(workshop);
        booking.setStatus("CONFIRMED");

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByLearner(Long learnerId) {
        return bookingRepository.findByLearnerId(learnerId);
    }
}