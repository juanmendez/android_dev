package info.juanmendez.android.intentservice.model.pojo;

import info.juanmendez.android.intentservice.helper.rx.SubscriptionShell;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 11/29/2015.
 */
public class MagazineNotificationSubject extends SubscriptionShell<MagazinesNotification> {
    public MagazineNotificationSubject() {
        super( PublishSubject.create() );
    }
}
