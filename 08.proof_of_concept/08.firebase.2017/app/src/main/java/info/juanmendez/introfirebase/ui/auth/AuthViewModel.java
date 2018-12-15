package info.juanmendez.introfirebase.ui.auth;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

/**
 * Created by juan on 12/8/17.
 */

public class AuthViewModel extends BaseObservable {
    @Bindable public final ObservableBoolean loggedIn = new ObservableBoolean(false);
}
