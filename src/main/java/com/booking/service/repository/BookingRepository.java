package com.booking.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.service.entity.Booking;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BookingRepository extends JpaRepository<Booking, String> {

	public Booking findBybookingId(int bookingId);

	public void deleteBybookingId(int bookingId);

	public int findBynoofseats(int noofseats);
	
//	public Boolean findBybookingId(int bookingId);

//	public boolean getByBookingId(int bookingId);


}
