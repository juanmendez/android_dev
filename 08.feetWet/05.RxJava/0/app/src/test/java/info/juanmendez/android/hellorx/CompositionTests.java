package info.juanmendez.android.hellorx;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import info.juanmendez.android.hellorx.model.UserSecurityStatus;
import info.juanmendez.android.hellorx.model.UserService;
import info.juanmendez.android.hellorx.utils.ThreadUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class CompositionTests {
    @Test
    public void testComposition(){
        Object waitMonitor = new Object();

        synchronized (waitMonitor){

            UserService userService = new UserService();

            // Write out a little snippet to make the json look nice...just as an example
            System.out.println( "{ \"userList\" : [ " );

            // Call the user service and fetch a list of users.
            Observable.from(userService.fetchUserList()).filter(user -> {
                return user.getSecurityStatus() != UserSecurityStatus.ADMINISTRATOR;
            }).toSortedList((user1, user2) -> {
                return user1.getSecurityStatus().compareTo(user2.getSecurityStatus());
            })
                    // Make the observable run on the io scheduler since the userservice
                    // might have to go over the wire (it doesn't in this example)
                    .subscribeOn(Schedulers.io())
                    .doOnCompleted(() -> {
                        // Since we have completed...we sync on the waitMonitor
                        // and then call notify to wake up the "main" thread.
                        synchronized (waitMonitor) {
                            waitMonitor.notify();
                        }
                    })
                    .subscribe(
                            // onNext function - receives a sorted list of users
                            // due to the "toSortedList" operation
                            (userList) -> {
                                for (int i = 0; i < userList.size(); i++ ) {
                                    System.out.println( userList.get(i).toJSON() );
                                }
                            },
                            throwable -> {
                                throwable.printStackTrace();
                            },
                            () -> {

                            }
                    );

            // Wait until the onCompleted method wakes us up.
            ThreadUtils.wait(waitMonitor);

            // Close the json
            System.out.println("] }");


        }


    }

}
