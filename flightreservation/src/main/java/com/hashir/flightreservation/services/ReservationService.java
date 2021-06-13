package com.hashir.flightreservation.services;

import com.hashir.flightreservation.dto.ReservationRequest;
import com.hashir.flightreservation.entities.Reservation;

public interface ReservationService {
	
	public Reservation bookFlight(ReservationRequest request);

}
