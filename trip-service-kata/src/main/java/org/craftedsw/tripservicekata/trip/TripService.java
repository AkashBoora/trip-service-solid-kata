package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.craftedsw.tripservicekata.user.UserSessionInterface;

public class TripService {
	private final TripDAOInterface tripDAO;

	public TripService(TripDAOInterface tripDAO) {
		this.tripDAO = tripDAO;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		return getTripsByUser(user, UserSession.getInstance());
	}

	List<Trip> getTripsByUser(User user, UserSessionInterface session) {
		User loggedUser = session.getLoggedUser();
		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}
		if (isFriend(user, loggedUser)) {
			return tripDAO.findTripsByUser(user);
		}
		return new ArrayList<>();
	}

	private boolean isFriend(User user, User maybeFriend) {
		return user.getFriends().stream().anyMatch(f -> f.equals(maybeFriend));
	}

}
