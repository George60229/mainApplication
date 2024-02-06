package com.example.mainapplication.service;

import com.example.mainapplication.converter.UserConverter;
import com.example.mainapplication.dto.UserDTO;
import com.example.mainapplication.model.MyUser;
import com.example.mainapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActiveUserService activeUserService;

    @Autowired
    UserConverter userConverter;

    public MyUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createUser(MyUser user) {
        userRepository.save(user);
    }

    public void updateUser(MyUser user) {
        userRepository.updateIsBlockedByUsername(user.getUsername(),user.isBlocked());
    }

    public MyUser findUserByLogin(String login) {
        return userRepository.findByUsername(login);
    }

    public List<UserDTO> getAllUsers() {
        List<MyUser> myUsers = userRepository.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();

        for (MyUser myUser : myUsers) {
            userDTOList.add(userConverter.convertUserToUserDTO(myUser,
                    activeUserService.getLocalCity(myUser.getUsername())));
        }

        return userDTOList;
    }

}
