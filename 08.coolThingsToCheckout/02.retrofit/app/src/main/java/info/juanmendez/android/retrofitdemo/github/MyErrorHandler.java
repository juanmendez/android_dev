package info.juanmendez.android.retrofitdemo.github;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Juan on 7/6/2015.
 */
public class MyErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null && r.getStatus() == 401) {
            return new Exception("Unathorized cause");
        }
        return cause;
    }
}
