package com.lld.bookmyshow.services;

import com.lld.bookmyshow.models.Picture;
import com.lld.bookmyshow.models.ShowSeat;
import com.lld.bookmyshow.models.ShowSeatType;
import com.lld.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculator {
    private final ShowSeatTypeRepository showSeatTypeRepository;
    @Autowired
    PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }
    public int calculatePrice(List<ShowSeat> showSeats, Picture picture) {

        // 1. Find out the type of seats in the given show
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(picture);
        // 2. get seatTypes for all the selected seats
        int amount = 0;
        for (ShowSeat showSeat : showSeats) {
            for (ShowSeatType showSeatType : showSeatTypes) {
                if (showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    // 3. calculate the amount
                    amount += showSeatType.getPrice();
                }
            }
        }
        return amount;
    }
}
