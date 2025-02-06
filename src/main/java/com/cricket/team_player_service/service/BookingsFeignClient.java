package com.cricket.team_player_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cricket.team_player_service.DTO.BookingDTO;

@FeignClient(name = "bookings-service", url = "http://localhost:1729/bookings")
public interface BookingsFeignClient {

    @GetMapping("/{bookingId}")
    BookingDTO getBookingById(@PathVariable int bookingId);
}
