package info.juanmendez.android.hellorx.service;

import info.juanmendez.android.hellorx.model.UserEvent;

/**
 * Created by Juan on 9/27/2015.
 */
public class CreateUserEvent extends UserEvent {

    public CreateUserEvent(String username, String emailAddress) {
        super(username, emailAddress);
    }
}