package com.apress.prospring6.twelve.config;

import com.apress.prospring6.twelve.entities.Car;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class DBInitializer {

    private static Logger LOGGER = LoggerFactory.getLogger(DBInitializer.class);

    private final CarRepository carRepository;

    public DBInitializer(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @PostConstruct
    public void initDB() {
        LOGGER.info("Starting database initialization...");

        var car = new Car();
        car.setLicensePlate("GRAVITY-0405");
        car.setManufacturer("Ford");
        car.setManufactureDate(LocalDate.of(2006, 9, 12));
        carRepository.save(car);

        car = new Car();
        car.setLicensePlate("CLARITY-0432");
        car.setManufacturer("Toyota");

        car.setManufactureDate(LocalDate.of(2003, 9, 9));
        carRepository.save(car);

        car = new Car();
        car.setLicensePlate("ROISE-0402");
        car.setManufacturer("Toyota");
        car.setManufactureDate(LocalDate.of(2017, 4, 16));
        carRepository.save(car);


        //....

        LOGGER.info("Database initialization finished.");
    }
}
