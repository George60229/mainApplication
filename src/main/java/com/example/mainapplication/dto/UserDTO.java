package com.example.mainapplication.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    String email;
    String login;

    boolean isBlocked;

    String popularTown;
}
