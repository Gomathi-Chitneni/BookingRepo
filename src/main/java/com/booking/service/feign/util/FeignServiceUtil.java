package com.booking.service.feign.util;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.booking.service.dto.BookingTheaterDto;
import com.booking.service.dto.Booking_Id;
import com.booking.service.dto.GetPrice;

@FeignClient(value = "feign", url = "http://localhost:8089/movie")
public interface FeignServiceUtil {

	@GetMapping("/showMovies")
	String moviepage();

//	@DeleteMapping("/delete/{moviename}")
//	String deleteMovie(@PathVariable String moviename);

	@PostMapping("/checkingdetails")
	public Booking_Id checkingDetails(@RequestBody BookingTheaterDto bookingtheaterdto);

	@PutMapping("/getprice")
	public GetPrice getprice(String theatername);

}
