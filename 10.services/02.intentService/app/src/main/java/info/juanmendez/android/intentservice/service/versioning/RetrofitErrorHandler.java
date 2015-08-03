package info.juanmendez.android.intentservice.service.versioning;

import info.juanmendez.android.intentservice.helper.Logging;
import info.juanmendez.android.intentservice.model.RetrofitErrorResponse;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Converts the complex error structure into a single string you can get with error.getLocalizedMessage() in Retrofit error handlers.
 * Also deals with there being no network available
 *
 * Uses a few string IDs for user-visible error messages
 *
 * @see "https://gist.github.com/benvium/66bf24e0de80d609dac0"
 */
public class RetrofitErrorHandler implements ErrorHandler {

    public RetrofitErrorHandler() {
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        String errorDescription;

        if (cause.getKind() == RetrofitError.Kind.NETWORK ) {
            errorDescription = "network error";
        } else {
            if (cause.getResponse() == null) {
                errorDescription = "no response error";
            } else {

                // Error message handling - return a simple error to Retrofit handlers..
                try {
                    RetrofitErrorResponse errorResponse = (RetrofitErrorResponse) cause.getBodyAs(RetrofitErrorResponse.class);
                    errorDescription = errorResponse.error.data.message;
                } catch (Exception ex) {
                    try {
                        errorDescription = "error network http error";
                    } catch (Exception ex2) {
                        Logging.print("handleError: " + ex2.getLocalizedMessage());
                        errorDescription = "unknown error";
                    }
                }
            }
        }

        return new Exception(errorDescription);
    }
}