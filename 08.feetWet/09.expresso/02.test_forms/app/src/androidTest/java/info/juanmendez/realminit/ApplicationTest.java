package info.juanmendez.realminit;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest{
    @Rule
    public ActivityTestRule<MainActivity_> mActivityRule = new ActivityTestRule<>(MainActivity_.class);

    @Test
    public void songAddedTest(){
        onView( withId( R.id.addSongButton) ).perform(click());

        onView( withId( R.id.titleInput) ).perform(click());
        onView( withId( R.id.titleInput) ).perform( typeText("Welcome to the Jungle") );
        Espresso.closeSoftKeyboard();

        onView( withId( R.id.urlInput) ).perform(click() );
        onView( withId( R.id.urlInput) ).perform( typeText("https://www.youtube.com/watch?v=o1tj2zJ2Wvg") );
        Espresso.closeSoftKeyboard();

        onView( withId( R.id.yearInput) ).perform(click() );
        onView( withId( R.id.yearInput) ).perform( typeText("1987") );
        Espresso.closeSoftKeyboard();

        onView( withId( R.id.submit_button) ).perform(click() );
        onView( withId( R.id.addSongButton) ).check( matches( isDisplayed() ) );
    }

    @Test
    public void bandAddedTest(){

        onView( withId( R.id.addBandButton) ).perform(click());

        onView( withId( R.id.bandTitleInput) ).perform(click());
        onView( withId( R.id.bandTitleInput) ).perform( typeText("Def Leppard") );
        Espresso.closeSoftKeyboard();

        onView( withText( R.string.ok ) ).perform(click() );
        onView( withId( R.id.addSongButton) ).check( matches( isDisplayed() ) );

    }
}