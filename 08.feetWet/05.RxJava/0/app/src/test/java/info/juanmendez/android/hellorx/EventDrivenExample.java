package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import info.juanmendez.android.hellorx.service.EmailMonitor;
import info.juanmendez.android.hellorx.service.EmailService;
import info.juanmendez.android.hellorx.service.IEmailService;
import info.juanmendez.android.hellorx.service.IUserService;
import info.juanmendez.android.hellorx.service.UserService;

/**
 * Created by Juan on 9/27/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class EventDrivenExample
{


    @Test
    public void testMonitoring() throws InterruptedException {

        try {
            IEmailService emailService = new EmailService();

            IUserService userService = new UserService();

            new EmailMonitor( emailService, userService );
            userService.addUser("xyz", "xyz@xzy.com");
            userService.addUser("abc", "abc@abc.com");

            Thread.sleep(2000);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

}
