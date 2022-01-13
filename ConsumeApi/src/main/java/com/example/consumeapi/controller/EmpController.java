package com.example.consumeapi.controller;

import java.util.List;

import com.example.consumeapi.entity.Employee;
import com.example.consumeapi.exception.ResourceNotFoundException;
import com.example.consumeapi.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emp")
public class EmpController {
    @Autowired
    private EmpRepository empRepository;

    // get all users
    @GetMapping
    public List<Employee> getAllUsers() {
        return this.empRepository.findAll();
    }

    // get user by id
    @GetMapping("/{id}")
    public Employee getUserById(@PathVariable (value = "id") long userId) {
        return this.empRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    }

    // create user
    @PostMapping
    public Employee createUser(@RequestBody Employee user) {
        return this.empRepository.save(user);
    }

    // update user
    @PutMapping("/{id}")
    public Employee updateUser(@RequestBody Employee user, @PathVariable ("id") long userId) {
        Employee existingUser = this.empRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.empRepository.save(existingUser);
    }

    // delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteUser(@PathVariable ("id") long userId){
        Employee existingUser = this.empRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        this.empRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
