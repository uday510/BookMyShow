package com.lld.bookmyshow.services;

import com.lld.bookmyshow.models.*;
import com.lld.bookmyshow.repositories.BookingRepository;
import com.lld.bookmyshow.repositories.ShowRepository;
import com.lld.bookmyshow.repositories.ShowSeatRepository;
import com.lld.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final PriceCalculator priceCalculator;
    @Autowired
    BookingService(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository, PriceCalculator priceCalculator) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculator = priceCalculator;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> showSeatIds, Long showId) {

       // 1. Get the user by userId
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("No such user found");
        }
        User bookedBy = userOptional.get();
       // 2. Get the show with the showId
        Optional<Picture> showOptional = showRepository.findById(showId);
        if (showOptional.isEmpty()) {
            throw new RuntimeException("No show found.");
        }
        Picture bookedPicture = showOptional.get();
       // 3. ----- Take Lock ------- (start transaction)
       // 4. get the showSeat using seatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
       // 5. if no, throw error
        for(ShowSeat showSeat : showSeats){
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                            Duration.between(showSeat.getLockedAt().toInstant() , new Date().toInstant()).toMinutes() > 15))){
                // 5. if no , throw error
                throw new RuntimeException("Selected seats are not available!");
            }
        }
       // 6. if yes, mark all the selected seats as locked.
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setLockedAt(new Date());
            // 7. save it in the database.
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }
       // ------- release the lock: end transaction -------
       // 8. create the corresponding Booking object
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShowSeats(savedShowSeats);
        booking.setUser(bookedBy);
        booking.setBookedAt(new Date());
        booking.setPicture(bookedPicture);
        booking.setAmount(priceCalculator.calculatePrice(savedShowSeats, bookedPicture));
        booking.setPayments(new ArrayList<>());

       // 9. save the booking details in the database
       booking =  bookingRepository.save(booking);

       // 10. return the booking object
        return booking;
    }
}
