package com.example.mainapplication.controller;


import com.example.mainapplication.dto.UserInfo;
import com.example.mainapplication.model.ActiveUser;
import com.example.mainapplication.model.MyUser;
import com.example.mainapplication.repository.ActiveUserRepository;
import com.example.mainapplication.security.PasswordHasher;
import com.example.mainapplication.service.ActiveUserService;
import com.example.mainapplication.service.EmailService;
import com.example.mainapplication.service.GPSService;
import com.example.mainapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class EmailController {
    @Autowired
    private PasswordHasher passwordHasher;
    private final Map<String, Integer> savedValue = new HashMap<>();
    private final String adminEmail = "admin123@gmail.com";


    @Autowired
    private EmailService emailService;

    @Autowired
    private GPSService gpsService;
    @Autowired
    private UserService userService;

    @Autowired
    private ActiveUserService activeUsersService;


    @PostMapping("/checkAccess/{email}/{value}")
    public String checkValue(@PathVariable String email, @PathVariable int value) {


        if (savedValue.get(email) != null) {

            boolean result = savedValue.get(email) == value;
            savedValue.remove(email, value);
            if (result) {
                return "success";
            }

        }
        return "loginError";
    }


    // todo fix password here
    @PostMapping("/sendEmail/{login}/{password}")
    public String sendEmail(@PathVariable String login, @PathVariable String password, Model model) throws NoSuchAlgorithmException {
        password = passwordHasher.getHash(password);
        MyUser user = userService.findUserByLogin(login);
        if (user == null) {
            return "error";
        }
        if (!user.getPassword().equals(password)) {
            return "error";
        }
        if (user.isBlocked()) {
            return "blockError";
        }

        if (user.getEmail().equals(adminEmail) && userService.findUserByLogin(login).getPassword().equals(password)) {
            return "adminPage";
        }


        String email = user.getEmail();
        UserInfo info = gpsService.loadLocation();
        if (!activeUsersService.getLocalCity(user.getUsername()).equals(info.getCity())) {


            return "orderPage";
        }
        ActiveUser activeUser = new ActiveUser();
        activeUser.setAsNumber(info.getAs());
        activeUser.setIp(info.getIp());
        activeUser.setCity(info.getCity());
        activeUser.setCountry(info.getCountry());
        activeUser.setUsername(userService.findUserByEmail(user.getEmail()).getUsername());
        activeUser.setCountry(info.getCountry());
        activeUser.setDate(new Date());
        activeUser.setOrganization(info.getOrg());

        activeUsersService.createActiveUser(activeUser);
        Random random = new Random();
        int value = random.nextInt(100000, 999999);
        String text = "\n" +
                "Welcome to Our Website!\n" +
                "\n" +
                "For your security, we kindly ask you to confirm the number or code you received.\n" +
                "\n" +
                "Please enter the number that was sent to you on the provided contact address or phone.\n" +
                "\n" +
                "Your number:" + value + "\n" +
                "\n" +
                "Thank you for your cooperation.";
        savedValue.put(email, value);

        emailService.sendEmail(email, "Confirm your login", text);
        model.addAttribute("email", email);

        UserInfo userInfo = gpsService.loadLocation();
        model.addAttribute(userInfo);
        return "map";
    }


}
