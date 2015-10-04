package info.juanmendez.android.hellorx.service;

import java.util.List;

/**
 * Created by Juan on 9/27/2015.
 */
public class EmailService implements IEmailService {

    @Override
    public void sendEmail(List<String> recipientList, String fromEmail, String subject, String text) {

        System.out.println();
        System.out.println("--------------------------------------------------------------");
        System.out.println("Sending Email");
        System.out.println("--------------------------------------------------------------");

        System.out.print("To     : ");
        for (String nextEmail : recipientList) {
            System.out.print(nextEmail);
            System.out.print("; ");
        }
        System.out.println();

        System.out.println("From   : " + fromEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Text   : ");
        System.out.println();
        System.out.print(text);
        System.out.println();

        System.out.println("--------------------------------------------------------------");

    }
}