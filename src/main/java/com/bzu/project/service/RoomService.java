package com.bzu.project.service;

import com.bzu.project.dto.RoomDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.Room;
import com.bzu.project.repository.FloorRepository;
import com.bzu.project.repository.RoomClassRepository;
import com.bzu.project.repository.RoomRepository;
import com.bzu.project.repository.RoomStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Page<Room> getAllRooms(PageRequest pageRequest) {
        return roomRepository.findAll(pageRequest);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
    }

    public Room createRoom(RoomDTO roomDTO) {
        Room room = convertToEntity(roomDTO);
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
        room.setFloor(floorRepository.findById(roomDTO.getFloorId()).orElse(null));
        room.setRoomClass(roomClassRepository.findById(roomDTO.getRoomClassId()).orElse(null));
        room.setStatus(roomStatusRepository.findById(roomDTO.getStatusId()).orElse(null));
        room.setRoomNumber(roomDTO.getRoomNumber());
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
        roomRepository.delete(room);
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
