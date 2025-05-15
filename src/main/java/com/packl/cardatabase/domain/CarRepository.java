package com.packl.cardatabase.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    @Query("select c from Car c where c.brand = :brand")
    List<Car> findByBrand(@Param("brand")String brand);

    @Query("select c from Car c where c.brand like %:brand%")
    List<Car> findByBrandEndsWith(@Param("brand")String brand);

    List<Car> findByColor(String color);

    List<Car> findByModelYear(int modelYear);

    List<Car> findByBrandAndModel(String brand, String model);

    List<Car> findByBrandOrColor(String brand, String color);

    List<Car> findByBrandOrderByModelYearAsc(String brand);
}
