package com.vkr.datastorageservice.service.db;

import com.vkr.datastorageservice.data.db.Room;
import com.vkr.datastorageservice.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> getAllRooms(){
        return repository.findAll();
    }

    public Room getRoomById(UUID id){
        return repository.findById(id).orElseThrow();
    }

    public void updateRoom(Room room){
        repository.save(room);
    }

    public void deleteRoom(UUID id){
        repository.deleteById(id);
    }
}
