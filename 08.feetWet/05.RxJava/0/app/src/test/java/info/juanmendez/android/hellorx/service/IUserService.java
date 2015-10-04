package info.juanmendez.android.hellorx.service;

import info.juanmendez.android.hellorx.model.UserEvent;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by Juan on 9/27/2015.
 */
public interface IUserService {

    void addUser(String username, String emailAddress);

    void subscribeToUserEvents(Observer<UserEvent> subscriber);
    void subscribeToUserEvents(Action1<UserEvent> onNext);
}
