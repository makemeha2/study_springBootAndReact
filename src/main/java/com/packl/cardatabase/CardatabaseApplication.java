package com.packl.cardatabase;

import com.packl.cardatabase.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
@EnableMethodSecurity
public class CardatabaseApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);

	private final CarRepository repository;
	private final OwnerRepository orepository;
	private final AppUserRepository urepository;


	public CardatabaseApplication(CarRepository repository, OwnerRepository orepository, AppUserRepository urepository) {
		this.repository = repository;
		this.orepository = orepository;
		this.urepository = urepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Car car1 = new Car("Ford", "Mustang", "Red", "ADF-1121", 2023, 59000);
		Car car2 = new Car("Nissan", "Leaf", "White", "SSJ-3002", 2020, 29000);
		Car car3 = new Car("Toyota", "Prius", "Silver", "KKO-0212", 2022, 39000);

		Owner owner1 = new Owner("John", "Johnson");
		owner1.addCar(car1);
		Owner owner2 = new Owner("Mary", "Robinson");
		owner2.addCar(car2);
		owner2.addCar(car3);
		orepository.saveAll(Arrays.asList(owner1, owner2));

		urepository.save(new AppUser("user1", new BCryptPasswordEncoder().encode("password"), "USER"));
		urepository.save(new AppUser("admin", new BCryptPasswordEncoder().encode("password"), "ADMIN"));
	}
}
