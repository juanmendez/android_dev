package info.juanmendez.android.hellorx.db;

import java.util.ArrayList;

import info.juanmendez.android.hellorx.model.DataGenerator;
import rx.Subscription;

public class ConnectionSubscription implements Subscription {

    private static ArrayList<String> letters;

    public ConnectionSubscription() {


        if( letters == null ){
            letters = (ArrayList<String>) DataGenerator.generateGreekAlphabet();
        }
    }

    @Override
    public void unsubscribe() {

        System.out.println( "Unsubscribe called!" );
    }

    @Override
    public boolean isUnsubscribed() {
        return false;
    }

    public ArrayList<String> getLetters() {

        return letters;
    }
}
