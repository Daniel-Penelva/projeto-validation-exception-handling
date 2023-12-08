package com.api.projetovalidationexceptionhandling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.projetovalidationexceptionhandling.dto.UserRequest;
import com.api.projetovalidationexceptionhandling.entities.User;
import com.api.projetovalidationexceptionhandling.exception.UserNotFoundException;
import com.api.projetovalidationexceptionhandling.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserRequest userRequest) {

        User user = User.build(0, userRequest.getName(), userRequest.getEmail(), userRequest.getMobile(),
                userRequest.getGender(), userRequest.getAge(), userRequest.getNationality());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) throws UserNotFoundException{
        User user = userRepository.findByUserId(id);

        if(user != null){
            return user;

        }else{
            throw new UserNotFoundException("user not found with id: " + id);
        }
    }

}
