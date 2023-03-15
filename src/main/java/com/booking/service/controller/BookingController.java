package com.booking.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.booking.service.dto.BookingDto;
import com.booking.service.dto.BookingTheaterDto;
import com.booking.service.dto.Booking_Id;
import com.booking.service.entity.Booking;
import com.booking.service.feign.util.FeignServiceUtil;
import com.booking.service.services.BookingServices;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private FeignServiceUtil feignservice;

	@Autowired
	private BookingServices bookingServices;

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/home")
	public ResponseEntity<String> homepage() {
		return ResponseEntity.ok("Welcome to Booking page");
	}

	@GetMapping("/moviehomepage")
	public ResponseEntity<String> welcomepage() {
		String str = feignservice.moviepage();
		return new ResponseEntity<String>(str, HttpStatus.FOUND);
	}

	@GetMapping("/saveDetails")
	public ResponseEntity<String> saveDetails(Booking_Id bookingid, BookingDto bookingDto) {
		bookingServices.saveDetails(bookingid, bookingDto);
		return (ResponseEntity<String>) ResponseEntity.accepted();
	}

	@PostMapping("/enterdetails")
	public ResponseEntity<String> enterBookingDetails(@RequestBody BookingDto bookingdto) {

		String str = bookingServices.enterBookingDetails(bookingdto);
		return new ResponseEntity<String>(str, HttpStatus.CREATED);

	}

//	@DeleteMapping("/deletemovie/{moviename}")
//	public String deleteMoviename(@PathVariable String moviename) {
//		return feignservice.deleteMovie(moviename);
//	}

	@ResponseStatus(value = HttpStatus.ACCEPTED)
	@GetMapping("/get/{bookingId}")
	public ResponseEntity<BookingDto> fetchbookingdetails(@PathVariable int bookingId) {
		BookingDto bookingDto = bookingServices.fetchbookingdetails(bookingId);
		return ResponseEntity.ok(bookingDto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping("/delete/{bookingId}")
	public ResponseEntity<String> cancelBooking(@PathVariable int bookingId) {
		bookingServices.cancelBooking(bookingId);
		return ResponseEntity.ok("Deleted booking Id: " + bookingId);
	}

	@PutMapping("update/{bookingId}")
	public ResponseEntity<String> updateBooking(@PathVariable Integer bookingId, @RequestBody Integer noOfSeats) {
		String str = bookingServices.updateBooking(bookingId, noOfSeats);
		return new ResponseEntity<String>(str, HttpStatus.CREATED);

	}

}
