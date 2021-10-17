package com.pharmacyANC.PharmacyANC.busines.service;

import com.pharmacyANC.PharmacyANC.busines.model.User;
import com.pharmacyANC.PharmacyANC.connectionDb.ConectionDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final ConectionDBService conectionDBService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(ConectionDBService conectionDBService, PasswordEncoder passwordEncoder) {
        this.conectionDBService = conectionDBService;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> getUsers() {
        return conectionDBService.getUsers();
    }

    public User registration(User user) {
        String pass = user.getPassword();
        String codePass = passwordEncoder.encode(pass);
        user.setPassword(codePass);
        return conectionDBService.addUser(user);
    }

    public User getUserName(String name) {
        return conectionDBService.findByName(name);
    }

}
