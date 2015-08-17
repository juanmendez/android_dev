package info.juanmendez.android.intentservice.model;

/**
 * @see "https://gist.github.com/benvium/66bf24e0de80d609dac0"
 */
public class RetrofitErrorResponse {
   public Error error;

    public class Error {
        public Data data;

        public class Data {
            public String message;
        }
    }
}

