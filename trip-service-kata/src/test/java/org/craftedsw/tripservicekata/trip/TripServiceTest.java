package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class TripServiceTest {
    private TripService service;
    private MockTripDAO tripDAO;
    private MockUserSession session;
    @BeforeEach
    public void setup() {
        tripDAO = new MockTripDAO();
        service = new TripService(tripDAO);
        session = new MockUserSession();
    }

    @Test
    public void findUserTripsWhenLoggedInUserIsFriend() {
        session.loggedUser = new User();
        User user = new User();
        user.addFriend(session.getLoggedUser());
        Trip trip = new Trip();
        tripDAO.foundTrips.add(trip);

        List<Trip> result = service.getTripsByUser(user, session);

        Assertions.assertEquals(1, result.size());
        Assertions.assertSame(trip, result.get(0));
        Assertions.assertSame(user, tripDAO.passedUser);
    }

    @Test
    public void findNoUserTripsWhenLoggedInUserIsNotFriend() {
        session.loggedUser = new User();
        User user = new User();
        Trip trip = new Trip();
        tripDAO.foundTrips.add(trip);

        List<Trip> result = service.getTripsByUser(user, session);

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void throwsWhenNoLoggedInUser() {
        User user = new User();
        Trip trip = new Trip();
        tripDAO.foundTrips.add(trip);

        Assertions.assertThrows(UserNotLoggedInException.class, () -> {
            service.getTripsByUser(user, session);
        });
    }
}
