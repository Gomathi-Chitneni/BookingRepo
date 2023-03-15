package com.booking.service.services;

import java.util.Random;

import javax.management.AttributeNotFoundException;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.booking.service.dto.BookingDto;
import com.booking.service.dto.BookingTheaterDto;
import com.booking.service.dto.Booking_Id;
import com.booking.service.dto.GetPrice;
import com.booking.service.entity.Booking;
import com.booking.service.exception.BussinessException;
import com.booking.service.feign.util.FeignServiceUtil;
import com.booking.service.feign.util.IdNotfound;
import com.booking.service.repository.BookingRepository;

@Service
public class ImpBookingService implements BookingServices {

	@Autowired
	private BookingRepository bookRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FeignServiceUtil feignserviceutil;

	private BookingDto convertEntitytoDto(Booking booking) {
		BookingDto bookingDto = new BookingDto();
		bookingDto = modelMapper.map(booking, bookingDto.getClass());
		return bookingDto;

	}

	private Booking convertDtotoEntity(BookingDto bookingDto) {
		Booking booking = new Booking();
		booking = modelMapper.map(bookingDto, booking.getClass());
		return booking;
	}

	@Override
	public String enterBookingDetails(BookingDto bookingdto) {

		if (bookingdto.getName().isEmpty() || bookingdto.getMoviename().isEmpty() || bookingdto.getEmail().isEmpty()
				|| bookingdto.getTheatername().isEmpty() || bookingdto.getNoofseats() == 0) {
			throw new NullPointerException("Feild is empty,please check all fields");
		}
		BookingDto bookingDto = new BookingDto();

		bookingDto.setName(bookingdto.getName());
		bookingDto.setMoviename(bookingdto.getMoviename());
		bookingDto.setTheatername(bookingdto.getTheatername());
		bookingDto.setEmail(bookingdto.getEmail());
		bookingDto.setNoofseats(bookingdto.getNoofseats());
		bookingDto.setTotalprice(bookingdto.getTotalprice());

		BookingTheaterDto bookingtheaterdto = new BookingTheaterDto();

		bookingtheaterdto.setMoviename(bookingDto.getMoviename());
		bookingtheaterdto.setTheatername(bookingDto.getTheatername());
		bookingtheaterdto.setNoofseats(bookingDto.getNoofseats());

		try {
			Booking_Id booking_id = feignserviceutil.checkingDetails(bookingtheaterdto);
			System.out.println(booking_id.getTotal_price());
			saveDetails(booking_id, bookingdto);
			return "Your Booking is Succesful" + "\n" + "Bookingid: " + booking_id.getBooking_Id() + "\n"
					+ "Total Price: " + booking_id.getTotal_price();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return "Error cannot book ticket,wrong information";
		} catch (Exception e) {
			throw new BussinessException("Cannot find the movie in the theater,please choose the available theater");
		}

	}

	@Override
	public BookingDto fetchbookingdetails(int bookingId) {

		if (bookRepo.findBybookingId(bookingId) != null) {
			Booking booking = bookRepo.findBybookingId(bookingId);
			BookingDto bookingdto = convertEntitytoDto(booking);
			return bookingdto;
		} else {
			throw new IdNotfound("Sorry,Request has denied,please chcek the bookingId");
		}

	}

	public String cancelBooking(int bookingId) {

		if (bookRepo.findBybookingId(bookingId) != null) {
			bookRepo.deleteBybookingId(bookingId);
		} else {
			throw new IdNotfound("The given ID is not found,please check");
		}

		try {
			bookRepo.deleteBybookingId(bookingId);
		} catch (Exception exception) {
			throw new BussinessException("Something went wrong,please try again later");
		}
		return null;

	}

	@Override
	public String updateBooking(int bookingId, int noofseats) {

		if (bookRepo.findBybookingId(bookingId) == null) {
			throw new IdNotfound("ID not found,please enter correct id");
		} else {
			Booking booking = bookRepo.findBybookingId(bookingId);
			BookingDto bookingdto = convertEntitytoDto(booking);
			int incre_seats = bookingdto.getNoofseats() + noofseats;
			String theatername = booking.getTheaterName();
			GetPrice getprice = feignserviceutil.getprice(theatername);

			Random random = new Random();
			int booking_id = 10000 + random.nextInt(90000);

			float cal_price = getprice.getPrice();
			float total_price = noofseats * cal_price;
			float add_price = total_price + bookingdto.getTotalprice();

			bookingdto.setNoofseats(incre_seats);
			bookingdto.setTotalprice(add_price);
			Booking booking2 = convertDtotoEntity(bookingdto);
			booking2.setBookingId(booking_id);
			bookRepo.save(booking2);
			return "Your update has been changed:" + "\n" + "Total price: " + booking2.getTotalprice() + "\n"
					+ "New Bookingid: " + booking2.getBookingId();

		}

//		Booking_Id bookingid=new Booking_Id();
//		bookingid.setBooking_Id(booking_id);

	}

	public String saveDetails(Booking_Id bookingid, BookingDto bookingDto) {
		
		try {
		Booking booking = new Booking();

		booking.setBookingId(bookingid.getBooking_Id());
		booking.setTotalprice(bookingid.getTotal_price());

		booking.setEmail(bookingDto.getEmail());
		booking.setName(bookingDto.getName());
		booking.setTheaterName(bookingDto.getTheatername());
		booking.setMovieName(bookingDto.getMoviename());
		booking.setNoofseats(bookingDto.getNoofseats());

		bookRepo.save(booking);
		return null;
		}catch(Exception exception)
		{
			throw new BussinessException("Something went wrong,unable to process the request");
		}

	}

}
