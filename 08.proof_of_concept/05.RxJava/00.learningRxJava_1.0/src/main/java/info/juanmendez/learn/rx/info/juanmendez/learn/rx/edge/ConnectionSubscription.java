package info.juanmendez.learn.rx.info.juanmendez.learn.rx.edge;

import rx.Subscription;

/**
 * Created by Juan Mendez @juanmendezinfo on 10/3/2015.
 */
public class ConnectionSubscription implements Subscription {
    DBStorage db;

    public ConnectionSubscription(DBStorage db )
    {
        this.db = db;
    }

    public DBStorage getStorage(){
        return db;
    }

    public void unsubscribe() {
        db.close();
    }

    public boolean isUnsubscribed() {
        return false;
    }
}
