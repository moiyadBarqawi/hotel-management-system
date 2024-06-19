package com.bzu.project.service;

import com.bzu.project.dto.GuestDTO;
import com.bzu.project.model.Guest;
import com.bzu.project.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public List<GuestDTO> getAllGuests() {
        return guestRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public GuestDTO getGuestById(Long id) {
        Optional<Guest> guest = guestRepository.findById(id);
        return guest.map(this::convertToDTO).orElse(null);
    }

    public GuestDTO createGuest(GuestDTO guestDTO) {
        Guest guest = convertToEntity(guestDTO);
        return convertToDTO(guestRepository.save(guest));
    }

    public GuestDTO updateGuest(Long id, GuestDTO guestDTO) {
        Optional<Guest> existingGuest = guestRepository.findById(id);
        if (existingGuest.isPresent()) {
            Guest guest = existingGuest.get();
            guest.setFirstName(guestDTO.getFirstName());
            guest.setLastName(guestDTO.getLastName());
            guest.setEmailAddress(guestDTO.getEmailAddress());
            guest.setPhoneNumber(guestDTO.getPhoneNumber());
            return convertToDTO(guestRepository.save(guest));
        } else {
            return null;
        }
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
