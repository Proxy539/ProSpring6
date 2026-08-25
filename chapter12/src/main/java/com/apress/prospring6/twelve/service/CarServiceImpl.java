package com.apress.prospring6.twelve.service;

import com.apress.prospring6.twelve.entities.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service("carService")
@Repository
@Transactional
public class CarServiceImpl implements CarService {

    public boolean done;

    final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<Car> findAll() {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false);
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    @Scheduled(fixedDelay = 10000)
    public void updateCarAgeJob() {
        var cars = findAll();

        var currentDate = LocalDate.now();
        LOGGER.info("Car age update job starter");

        cars.forEach(car -> {
            var p = Period.between(car.getManufactureDate(), currentDate);
            int age = p.getYears();

            car.setAge(age);
            save(car);
            LOGGER.info("Car age update --> {}", car);
        });

        LOGGER.info("Car age update job completed successfully");
        done = true;
    }


    @Override
    public boolean isDone() {
        return done;
    }
}
