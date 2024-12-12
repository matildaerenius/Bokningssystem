package filter;

import models.Booking;

import java.util.List;

public interface Criteria {
    List<Booking> meetCriteria(List<Booking> bookings);
}
