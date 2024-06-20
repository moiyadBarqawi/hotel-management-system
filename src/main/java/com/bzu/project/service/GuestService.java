package com.bzu.project.service;

import com.bzu.project.dto.GuestDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.Guest;
import com.bzu.project.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public Page<GuestDTO> getAllGuests(PageRequest pageRequest) {
        Page<Guest> guests = guestRepository.findAll(pageRequest);
        return guests.map(this::convertToDTO);
    }

    public GuestDTO getGuestById(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + id));
        return convertToDTO(guest);
    }

    public GuestDTO createGuest(GuestDTO guestDTO) {
        Guest guest = convertToEntity(guestDTO);
        return convertToDTO(guestRepository.save(guest));
    }

    public GuestDTO updateGuest(Long id, GuestDTO guestDTO) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + id));
        guest.setFirstName(guestDTO.getFirstName());
        guest.setLastName(guestDTO.getLastName());
        guest.setEmailAddress(guestDTO.getEmailAddress());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());
        return convertToDTO(guestRepository.save(guest));
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    private Guest convertToEntity(GuestDTO guestDTO) {
        Guest guest = new Guest();
        guest.setId(guestDTO.getId());
        guest.setFirstName(guestDTO.getFirstName());
        guest.setLastName(guestDTO.getLastName());
        guest.setEmailAddress(guestDTO.getEmailAddress());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());
        return guest;
    }

    private GuestDTO convertToDTO(Guest guest) {
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(guest.getId());
        guestDTO.setFirstName(guest.getFirstName());
        guestDTO.setLastName(guest.getLastName());
        guestDTO.setEmailAddress(guest.getEmailAddress());
        guestDTO.setPhoneNumber(guest.getPhoneNumber());
        return guestDTO;
    }
}
