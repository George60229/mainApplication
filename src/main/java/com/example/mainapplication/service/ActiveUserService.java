package com.example.mainapplication.service;

import com.example.mainapplication.model.ActiveUser;
import com.example.mainapplication.repository.ActiveUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActiveUserService {
    @Autowired
    private ActiveUserRepository activeUserRepository;

    public void createActiveUser(ActiveUser activeUser) {
        activeUserRepository.save(activeUser);
    }

    public String getLocalCity(String username){
        List<ActiveUser> activeUsers=activeUserRepository.findByUsername(username);
        // Используем HashMap для подсчета частоты каждого города
        Map<String, Integer> cities = new HashMap<>();

        // Подсчет частоты каждого города
        for (ActiveUser user : activeUsers) {
            String city = user.getCity();
            cities.put(city, cities.getOrDefault(city, 0) + 1);
        }

        // Нахождение города с наибольшей частотой
        String mainCity = null;
        int max = 0;

        for (Map.Entry<String, Integer> entry : cities.entrySet()) {
            if (entry.getValue() > max) {
                mainCity = entry.getKey();
                max = entry.getValue();
            }
        }

        // Вывод наиболее частого города (если есть)
       return mainCity;



    }

    public List<ActiveUser> getAllActiveUsers() {
        return activeUserRepository.findAll();
    }
}
