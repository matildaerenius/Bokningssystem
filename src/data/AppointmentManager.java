package data;

import models.Booking;
import models.Customer;
import models.TimeFrame;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentManager {

    private static AppointmentManager instance;
    private final DatabaseDao databaseDao;

    public AppointmentManager(DatabaseDao dataBaseDao) {
        this.databaseDao = dataBaseDao;
    }

    public static synchronized AppointmentManager getInstance(DatabaseDao databaseDao) {
        if (instance == null) {
            instance = new AppointmentManager(databaseDao);
        }
        return instance;
    }

    public boolean bookAppointment(Customer customer, String date, String startTime, String endTime) {
        TimeFrame timeFrame = new TimeFrame(date, startTime, endTime);
        Booking booking = new Booking(timeFrame, "Booked", customer);

        if (!timeFrameOverlaps(LocalDate.parse(date), timeFrame)) {
            databaseDao.createBooking(booking);
            sendConfirmation(booking);
            return true;
        }
        return false;
    }

    public boolean cancelAppointment(Customer customer, LocalDate date, LocalTime startTime) {
        List<Booking> bookings = databaseDao.getAppointmentsForUser(customer);
        for (Booking booking : bookings) {
            if (booking.getTimeFrame().getDate().equals(date) &&
                    booking.getTimeFrame().getStartTime().equals(startTime)) {
                // Gör bokningen tillgänglig
                booking.setCustomer(null);
                booking.setDescription("Available");
                databaseDao.updateBookingStatus(booking.getTimeFrame(), null);
                return true;
            }
        }
        return false;
    }

    private boolean timeFrameOverlaps(LocalDate date, TimeFrame newTimeFrame) {
        List<Booking> bookings = databaseDao.getAllBookings();
        for (Booking booking : bookings) {
            if (booking.getTimeFrame().getDate().equals(date)) {
                TimeFrame existingFrame = booking.getTimeFrame();
                if (existingFrame.getEndTime().isAfter(newTimeFrame.getStartTime()) &&
                        existingFrame.getStartTime().isBefore(newTimeFrame.getEndTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void sendConfirmation(Booking booking) {
        System.out.println("Confirmation sent for booking: " + booking.getTimeFrame());
    }

    public List<Booking> getAppointmentsForUser(Customer customer) {
        return DatabaseManager.getInstance().getAppointmentsForUser(customer);
    }
}

