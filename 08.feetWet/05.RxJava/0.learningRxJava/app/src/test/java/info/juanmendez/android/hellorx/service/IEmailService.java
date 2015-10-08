package info.juanmendez.android.hellorx.service;

import java.util.List;

/**
 * Created by Juan on 9/27/2015.
 */
public interface IEmailService {
    void sendEmail(List<String> recipientList, String fromEmail, String subject, String text);
}
