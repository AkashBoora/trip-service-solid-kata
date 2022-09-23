package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSessionInterface;

public class MockUserSession implements UserSessionInterface {
    public User loggedUser;
    @Override
    public User getLoggedUser() {
        return loggedUser;
    }
}
