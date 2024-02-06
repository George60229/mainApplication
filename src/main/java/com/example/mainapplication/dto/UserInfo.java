package com.example.mainapplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private Coordinates coordinates;

    private String country;

    private String city;
    private String ip;


    private String org;

    private String as;

    private double latitude;

    private double longitude;


}
