package com.booking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.BDDMockito.given;

import com.booking.service.controller.BookingController;
import com.booking.service.dto.BookingDto;
import com.booking.service.entity.Booking;
import com.booking.service.repository.BookingRepository;
import com.booking.service.services.BookingServices;
import com.booking.service.services.ImpBookingService;

import net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType;

@ExtendWith(MockitoExtension.class)
class TestCase {

	@Mock
	private BookingServices bookingservices;

//	@Mock
//	private BookingRepository bookingrepo;

	@InjectMocks
	private BookingController bookingcontroller;

//	@InjectMocks
//	private ImpBookingService impbookingservice;

	private Booking booking;

//	@BeforeEach
//	public void setup() {
//		
//	   booking.setBookingId(12345);
//	   booking.setMovieName("Nun");
//	   booking.setName("Sai");
//	   booking.setNoofseats(4);
//	   booking.setTheaterName("BSR Mall");
//	   booking.setEmail("Sai12@com");
//	   
//	}

	@Test
	public void testEnterBookingDetails() throws Exception {
		BookingDto bookingdto = new BookingDto();
		ReflectionTestUtils.setField(booking, "theatername", "BSR Mall");
		ReflectionTestUtils.setField(booking, "name", "goms");
		ReflectionTestUtils.setField(booking, "moviename", "Nun");
		ReflectionTestUtils.setField(booking, "email", "goms@com");
		ReflectionTestUtils.setField(booking, "totalprice", 360);
		ReflectionTestUtils.setField(booking, "noOfSeats", 3);

		when(bookingservices.enterBookingDetails(any())).thenReturn("saved");
		bookingcontroller.enterBookingDetails(bookingdto);

		assertEquals("Your booking is successful", bookingcontroller.enterBookingDetails(bookingdto).getBody());

	}

//	@Test
//	public void testCancelBooking() {
//	
//	}

//	@Test
//	public void testFetchbookingdetails()
//	{
//		
//		ReflectionTestUtils.setField(booking, "bookingId", 12345);
//		given(bookingrepo.findBybookingId(12345)).willReturn(booking);
//		
//		BookingDto savedBooking=impbookingservice.fetchbookingdetails(booking.getBookingId());
//		
//		assertThat(savedBooking).isNotNull();
//		
//	}
}
