package com.hashir.flightreservation.controllers;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hashir.flightreservation.dto.ReservationUpdateRequest;
import com.hashir.flightreservation.entities.Reservation;
import com.hashir.flightreservation.repos.ReservationRepository;

@RestController
@CrossOrigin
public class ReservationRestController {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRestController.class);

	
	@RequestMapping("/reservations/{id}")
	public Reservation findReservation(@PathVariable("id")Long id ) {
		LOGGER.info("Inside findReservation for Id:"+id);
		return reservationRepository.findById(id).get();
		
	}
	
	@RequestMapping("/reservations")
	public Reservation reservationUpdate(@RequestBody ReservationUpdateRequest request) {
		LOGGER.info("Inside findReservation for:"+request);

		Reservation reservation = reservationRepository.findById(request.getId()).get();
		reservation.setCheckedIn(request.getCheckedIn());
		reservation.setNumberOfBags(request.getNumberOfBags());
		LOGGER.info("Saving Reservation");
		return reservationRepository.save(reservation);
	}
	

}
