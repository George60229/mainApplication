package com.example.mainapplication.controller;

import com.example.mainapplication.dto.UserDTO;
import com.example.mainapplication.model.ActiveUser;
import com.example.mainapplication.model.MyUser;
import com.example.mainapplication.service.ActiveUserService;
import com.example.mainapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    ActiveUserService activeUserService;
    @Autowired
    private UserService userService;


    @GetMapping("/adminPage")
    public String adminPage() {
        return "adminPage";
    }

    @GetMapping("/users")
    public String userList(Model model) {

        List<ActiveUser> users = activeUserService.getAllActiveUsers();

        model.addAttribute("users", users);
        return "activeUsersList";
    }

    @GetMapping("/blockUser")
    public String userListToBlock(Model model) {

        List<UserDTO> usersToBlock = userService.getAllUsers();
        usersToBlock.sort(Comparator.comparing(UserDTO::getLogin));
        model.addAttribute("users", usersToBlock);
        return "usersToBlock";
    }

    @PostMapping("/blockUser/{login}/{block}")
    public String blockUser(@PathVariable String login, @PathVariable boolean block, Model model) {
        MyUser res = userService.findUserByLogin(login);
        res.setBlocked(!block);

        userService.updateUser(res);

        return userListToBlock(model);
    }


}
