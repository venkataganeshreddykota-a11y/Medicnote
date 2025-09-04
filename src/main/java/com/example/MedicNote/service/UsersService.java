package com.example.MedicNote.service;

import com.example.MedicNote.entity.Users;
import com.example.MedicNote.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(String id) {
        return usersRepository.findById(id);
    }

    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    public void deleteUser(String id) {
        usersRepository.deleteById(id);
    }
} 