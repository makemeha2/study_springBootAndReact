package com.packl.cardatabase.web;

import com.packl.cardatabase.domain.Car;
import com.packl.cardatabase.domain.CarRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
    private final CarRepository repository;

    public CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public Iterable<Car> home() {
        return repository.findAll();
    }

    // 이를 어떻게 해야 하나?????

    @GetMapping("/cars")
    public Iterable<Car> getCars() {
        return repository.findAll();
    }
}
