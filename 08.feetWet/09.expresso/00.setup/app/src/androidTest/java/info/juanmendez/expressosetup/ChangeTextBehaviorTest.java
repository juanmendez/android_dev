package info.juanmendez.expressosetup;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith( AndroidJUnit4.class )
public class ChangeTextBehaviorTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Hello World!";
    }

    @Test
    public void changeText_sameActivity() {
        onView(withId(R.id.editTextUserInput))
                .check( matches(withText(mStringToBetyped)));
    }
}
