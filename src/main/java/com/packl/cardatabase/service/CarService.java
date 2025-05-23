package com.packl.cardatabase.service;

import com.packl.cardatabase.domain.Car;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @PreAuthorize("USER")
    public void updateCar(Car car) {

    }

    @PreAuthorize("ADMIN")
    public void deleteOwner(Car car) {

    }
}
