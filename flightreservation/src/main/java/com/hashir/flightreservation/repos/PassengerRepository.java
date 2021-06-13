package com.hashir.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hashir.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
