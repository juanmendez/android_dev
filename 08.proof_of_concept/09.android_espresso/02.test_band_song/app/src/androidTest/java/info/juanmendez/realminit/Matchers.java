package info.juanmendez.realminit;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Juan Mendez on 9/5/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
public class Matchers {

    public static Matcher<View> withTextLabeled(final String expected) {
        return new TypeSafeMatcher<View>() {


            @Override
            protected boolean matchesSafely(View item) {
                if(item != null && item.findViewById( android.R.id.text1 ) != null) {
                    TextView taskName = (TextView) item.findViewById( android.R.id.text1 );
                    if(TextUtils.isEmpty(taskName.getText())) {
                        return false;
                    } else {
                        return taskName.getText().equals(expected);
                    }
                } else {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Looked for " + expected + " in the android.R.id.text1.xml file");
            }
        };
    }


    public static Matcher<View> first(Matcher<View> expected ){

        return new TypeSafeMatcher<View>() {
            private boolean first = false;

            @Override
            protected boolean matchesSafely(View item) {

                if( !first && expected.matches(item) ){
                    return first = true;
                }

                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Matcher.first( " + expected.toString() + " )" );
            }
        };
    }
}
