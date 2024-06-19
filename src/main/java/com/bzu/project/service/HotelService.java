package com.bzu.project.service;


import com.bzu.project.dto.HotelDTO;
import com.bzu.project.model.Hotel;
import com.bzu.project.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    public Hotel createHotel(HotelDTO hotelDTO) {
        Hotel hotel = Hotel.builder()
                .name(hotelDTO.getName())
                .location(hotelDTO.getLocation())
                .rating(hotelDTO.getRating())
                .build();
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, HotelDTO hotelDTO) {
        Hotel hotel = getHotelById(id);
        hotel.setName(hotelDTO.getName());
        hotel.setLocation(hotelDTO.getLocation());
        hotel.setRating(hotelDTO.getRating());
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}