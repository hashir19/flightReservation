package com.hashir.flightreservation.services;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hashir.flightreservation.dto.ReservationRequest;
import com.hashir.flightreservation.entities.Flight;
import com.hashir.flightreservation.entities.Passenger;
import com.hashir.flightreservation.entities.Reservation;
import com.hashir.flightreservation.repos.FlightRepository;
import com.hashir.flightreservation.repos.PassengerRepository;
import com.hashir.flightreservation.repos.ReservationRepository;
import com.hashir.flightreservation.util.EmailUtil;
import com.hashir.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Value("${com.hashir.flightreservation.itinerary.dirpath}")
	private  String ITINERARY_DIR;

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	PDFGenerator pdfGenerator;
	
	@Autowired
	EmailUtil emailUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	
	@Override
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {
		
		LOGGER.info("Inside bookFlight()");
		//Make Payment
		
		Long flightId = request.getFlightId();
		LOGGER.info("Fetching Flight for Flight ID :"+flightId);
		Flight flight = flightRepository.findById(flightId).get();
		
		Passenger passenger= new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());
		LOGGER.info("Saving the passenger:"+passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation=new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		
		LOGGER.info("Saving the reservation:"+reservation);
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String filePath = ITINERARY_DIR +savedReservation.getId()+".pdf";
		LOGGER.info("Generating the Itinerary");
		pdfGenerator.generateItinerary(savedReservation,filePath);
		LOGGER.info("Emailing the itinerary");
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		
		
		return savedReservation;
	}

}
