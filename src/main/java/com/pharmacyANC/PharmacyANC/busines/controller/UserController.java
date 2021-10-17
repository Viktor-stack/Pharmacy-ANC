package com.pharmacyANC.PharmacyANC.busines.controller;

import com.pharmacyANC.PharmacyANC.busines.model.User;
import com.pharmacyANC.PharmacyANC.busines.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("{name}")
    public ResponseEntity<User> getUserName(@PathVariable("name") String name) {
        return ResponseEntity.ok(userService.getUserName(name));
    }


    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.registration(user));
    }

}
