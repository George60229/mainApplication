package com.example.mainapplication.converter;

import com.example.mainapplication.dto.UserDTO;
import com.example.mainapplication.model.MyUser;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {


    public UserDTO convertUserToUserDTO(MyUser myUser,String city) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(myUser.getEmail());
        userDTO.setLogin(myUser.getUsername());
        userDTO.setBlocked(myUser.isBlocked());
        userDTO.setPopularTown(city);
        return userDTO;
    }
}
