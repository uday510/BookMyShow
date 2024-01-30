package com.lld.bookmyshow.controllers;

import com.lld.bookmyshow.dto.BookMovieRequestDTO;
import com.lld.bookmyshow.dto.BookMovieResponseDTO;
import com.lld.bookmyshow.dto.ResponseStatus;
import com.lld.bookmyshow.models.Booking;
import com.lld.bookmyshow.models.Movie;
import com.lld.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class BookingController {
    private final BookingService bookingService;
    @Autowired // Automatically find the object in the params,
               // create it if not already created  and pass it over
    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    public BookMovieResponseDTO bookMovie(BookMovieRequestDTO bookMovieRequestDTO) {
        BookMovieResponseDTO response = new BookMovieResponseDTO();

        try {
           Booking booking = bookingService.bookMovie(bookMovieRequestDTO.getUserId(),
                   bookMovieRequestDTO.getShowSeatsIds(), bookMovieRequestDTO.getShowId());

           response.setBookingId(booking.getId());
           response.setResponseStatus(ResponseStatus.SUCCESS);
           response.setAmount(booking.getAmount());
        } catch (Exception exception) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
