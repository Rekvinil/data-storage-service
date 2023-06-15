package com.vkr.datastorageservice.service.db;

import com.vkr.datastorageservice.data.db.Room;
import com.vkr.datastorageservice.data.db.User;
import com.vkr.datastorageservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(UUID id){
        return userRepository.findById(id).orElseThrow();
    }

    public List<Room> getUsersRooms(UUID id){
        return userRepository.findById(id).orElseThrow().getRooms();
    }

    public void updateUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(UUID id){
        userRepository.deleteById(id);
    }
}
