package com.example.mainapplication.controller;

import com.example.mainapplication.dto.UserInfo;
import com.example.mainapplication.model.ActiveUser;
import com.example.mainapplication.model.MyUser;

import com.example.mainapplication.security.PasswordHasher;
import com.example.mainapplication.service.ActiveUserService;
import com.example.mainapplication.service.GPSService;

import com.example.mainapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;
import java.util.Date;


@Controller
public class AuthController {



    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHasher passwordHasher;
    @Autowired
    private ActiveUserService activeUserService;
    @Autowired
    private GPSService gpsService;

    @GetMapping("/")
    public String start() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") MyUser user) throws NoSuchAlgorithmException {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return "userExistError";
        }

        UserInfo info = gpsService.loadLocation();



        user.setBlocked(true);
        user.setPassword(passwordHasher.getHash(user.getPassword()));



        ActiveUser activeUser = new ActiveUser();
        activeUser.setAsNumber(info.getAs());
        activeUser.setIp(info.getIp());
        activeUser.setCity(info.getCity());
        activeUser.setCountry(info.getCountry());
        activeUser.setUsername(user.getUsername());
        activeUser.setCountry(info.getCountry());
        activeUser.setDate(new Date());
        activeUser.setOrganization(info.getOrg());
        activeUserService.createActiveUser(activeUser);
        userService.createUser(user);
        return "login";
    }


}
