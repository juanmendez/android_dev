package info.juanmendez.android.hellorx.service;

import java.util.ArrayList;

import info.juanmendez.android.hellorx.model.UserEvent;

/**
 * Created by Juan on 9/27/2015.
 */
public class EmailMonitor {

    private final IEmailService iEmailService;

    public EmailMonitor(IEmailService emailService, IUserService iUserService) {
        this.iEmailService = emailService;

        // Subscribe to UserEvents in the userService
        iUserService.subscribeToUserEvents(this::userEventHandler);
    }

    private void userEventHandler(UserEvent t) {
        System.out.println( "EmailMonitor::handleUserEvent - " + Thread.currentThread().getName() );

        ArrayList<String> recipList = new ArrayList<>();
        recipList.add(t.getEmailAddress());
        String text = "Hi " + t.getUsername() + ", Welcome to PluralSight!";
        iEmailService.sendEmail(recipList, "noreply@mySystem.com", "Welcome to PluralSight!", text);
    }
}