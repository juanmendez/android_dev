package info.juanmendez.android.hellorx.model;

import java.util.Date;

/**
 * Created by Juan on 9/27/2015.
 */
public class UserEvent {
    private final String username;
    private final String emailAddress;
    private final Date eventDate;

    public UserEvent(String username, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.eventDate = new Date();
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getEventDate() {
        return new Date(eventDate.getTime());
    }
}
