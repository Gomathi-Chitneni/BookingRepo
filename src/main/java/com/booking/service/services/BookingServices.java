package com.booking.service.services;

import org.springframework.stereotype.Service;

import com.booking.service.dto.BookingDto;
import com.booking.service.dto.Booking_Id;

@Service
public interface BookingServices {

	public String enterBookingDetails(BookingDto bookingdto);

	public BookingDto fetchbookingdetails(int bookingId);

	public String cancelBooking(int bookingId);

	public String updateBooking(int bookingId, int noofseats);

	public String saveDetails(Booking_Id bookingid, BookingDto bookingDto);

}
