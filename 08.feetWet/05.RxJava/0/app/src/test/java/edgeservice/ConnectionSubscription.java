package edgeservice;

import rx.Subscription;

/**
 * Created by Juan on 10/3/2015.
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

    @Override
    public void unsubscribe() {
        db.close();
    }

    @Override
    public boolean isUnsubscribed() {
        return false;
    }
}
