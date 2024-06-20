package com.bzu.project.service;

import com.bzu.project.dto.RoomDTO;
import com.bzu.project.model.Room;
import com.bzu.project.repository.FloorRepository;
import com.bzu.project.repository.RoomClassRepository;
import com.bzu.project.repository.RoomRepository;
import com.bzu.project.repository.RoomStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private RoomClassRepository roomClassRepository;

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(this::convertToDTO).orElse(null);
    }

    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = convertToEntity(roomDTO);
        return convertToDTO(roomRepository.save(room));
    }

    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Optional<Room> existingRoom = roomRepository.findById(id);
        if (existingRoom.isPresent()) {
            Room room = existingRoom.get();
            room.setFloor(floorRepository.findById(roomDTO.getFloorId()).orElse(null));
            room.setRoomClass(roomClassRepository.findById(roomDTO.getRoomClassId()).orElse(null));
            room.setStatus(roomStatusRepository.findById(roomDTO.getStatusId()).orElse(null));
            room.setRoomNumber(roomDTO.getRoomNumber());
            return convertToDTO(roomRepository.save(room));
        } else {
            return null;
        }
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    private Room convertToEntity(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setFloor(floorRepository.findById(roomDTO.getFloorId()).orElse(null));
        room.setRoomClass(roomClassRepository.findById(roomDTO.getRoomClassId()).orElse(null));
        room.setStatus(roomStatusRepository.findById(roomDTO.getStatusId()).orElse(null));
        room.setRoomNumber(roomDTO.getRoomNumber());
        return room;
    }

    private RoomDTO convertToDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setFloorId(room.getFloor().getId());
        roomDTO.setRoomClassId(room.getRoomClass().getId());
        roomDTO.setStatusId(room.getStatus().getId());
        roomDTO.setRoomNumber(room.getRoomNumber());
        return roomDTO;
    }
}
