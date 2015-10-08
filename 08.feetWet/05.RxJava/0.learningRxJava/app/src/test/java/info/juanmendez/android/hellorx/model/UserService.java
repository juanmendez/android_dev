package info.juanmendez.android.hellorx.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Juan on 9/21/2015.
 */
public class UserService {

    private final ArrayList<User> allUsers;

    public UserService() {
        allUsers = new ArrayList<>();

        allUsers.add(new User("sskyprancer", "scott.skyprancer@awesomehairgoodguys.org", UserSecurityStatus.GUEST));
        allUsers.add(new User("hmono", "harold.mono@ishotfirst.com", UserSecurityStatus.MODERATOR));
        allUsers.add(new User("jttent", "jarvis.t.tent@spacegangsta.com", UserSecurityStatus.ADMINISTRATOR));
        allUsers.add(new User("dvader", "darin.vader@misunderstoodempire.org", UserSecurityStatus.GUEST));
        allUsers.add(new User("lcarlosian", "londo.carlosian@bigskymining.com", UserSecurityStatus.MODERATOR));
    }

    public List<User> fetchUserList() {
        return Collections.unmodifiableList(allUsers);
    }

}
