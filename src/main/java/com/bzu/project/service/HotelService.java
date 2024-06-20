package com.bzu.project.service;

import com.bzu.project.dto.HotelDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.Hotel;
import com.bzu.project.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public Page<Hotel> getAllHotels(PageRequest pageRequest) {
        return hotelRepository.findAll(pageRequest);
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + id));
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
