package com.api.projetovalidationexceptionhandling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.projetovalidationexceptionhandling.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUserId(int id);
    
}
