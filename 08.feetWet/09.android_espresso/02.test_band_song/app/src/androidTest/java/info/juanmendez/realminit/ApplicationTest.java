package info.juanmendez.realminit;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import com.donnfelker.realmespressocontrib.RealmRecyclerViewActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import info.juanmendez.realminit.models.Band;
import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.views.MainActivity_;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.containsString;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {
    @Rule
    public ActivityTestRule<MainActivity_> mActivityRule = new ActivityTestRule<>(MainActivity_.class);

    @Test
    public void addBandsTest() {

        onView(withId(R.id.action_band)).check(matches(isDisplayed()));
        onView(withId(R.id.action_band)).perform(click());

        String[] bandNames = new String[]{"Tesla", "Bon Jovi", "Guns n' Roses", "Aerosmith", "Def Leppard", "Motley Crue"};

        for (String bandName : bandNames) {
            addBand( bandName );
        }

        onView(withId(R.id.addSongButton)).check(matches(isDisplayed()));
    }

    @Test
    public void addSongTest() {
        onView(withId(R.id.action_song)).perform(click());
        onView(withId(R.id.action_band)).check(matches(isDisplayed()));
        onView(allOf(withText(containsString("List of songs")), withParent(withId(R.id.fragmentToolbar)))).check(matches(isDisplayed()));

        //addSong
        Song song = new Song();
        song.setTitle("Love Song");
        song.setYear(1989);
        song.setVideo_url("http://youtube.com/abc/lovesong");

        Band band = new Band();
        band.setName("Tesla");
        addSong(song, band);



        /*
        onView(withText("Number of songs 1")).check(matches(isDisplayed()));
        onView(withId(R.id.action_song)).perform(click());
        onView(withId(R.id.songListView)).perform(RealmRecyclerViewActions.scrollTo(Matchers.withTextLabeled(song.getTitle())));
        onView(withText(song.getTitle())).perform(click());

        onView(withId(R.id.titleInput)).check(matches(isDisplayed()));*/
    }

    /**
     * lets editSong a song
     */
    @Test
    public void editSongTest() {

        onView(withId(R.id.action_song)).perform(click());

        //lets check the recylerView and select one song! :)
        Song song = new Song();
        song.setTitle("Love Song");
        song.setYear(1989);
        song.setVideo_url("http://youtube.com/abc/lovesong");

        Band band = new Band();
        band.setName("Tesla");

        editSong( song, band );

        Espresso.pressBack();
        onView(withId(R.id.addSongButton)).perform( click() );

        //lets click again
        onView(withId(R.id.songListView)).perform(RealmRecyclerViewActions.scrollTo(Matchers.first(Matchers.withTextLabeled(song.getTitle()))));
        onView(Matchers.first(withText(song.getTitle()))).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit_button)).perform( click() );

        song = new Song();
        song.setTitle("Paradise City");
        song.setYear(1987);
        song.setVideo_url("http://youtube.com/abc/lovesong");

        band = new Band();
        band.setName("Guns n' Roses");
        //addSong( song, band );
        //click plus button
        Espresso.pressBack();
        addSong( song, band );

    }


    @Test
    public void deleteSongTest() {

        onView(withId(R.id.action_song)).perform(click());
        onView(withId(R.id.action_band)).check(matches(isDisplayed()));
        onView(allOf(withText(containsString("List of songs")), withParent(withId(R.id.fragmentToolbar)))).check(matches(isDisplayed()));

        //addSong
        Song song = new Song();
        song.setTitle("Love Song");
        song.setYear(1989);
        song.setVideo_url("http://youtube.com/abc/lovesong");

        Band band = new Band();
        band.setName("Tesla");
        addSong(song, band);

        Espresso.pressBack();
        Espresso.pressBack();

        onView(withId(R.id.action_song)).perform(click());
        onView(withId(R.id.songListView)).perform(RealmRecyclerViewActions.scrollTo(Matchers.first(Matchers.withTextLabeled(song.getTitle()))));
        onView(Matchers.first(withText(song.getTitle()))).perform(longClick());
        onView(allOf(withText(R.string.ok), withClassName(containsString(Button.class.getSimpleName())))).perform(click());
    }

    public void addSong(Song song, Band band) {

        //click plus button
        onView(withId(R.id.button_add_song)).perform(click());


        onView(withId(R.id.titleInput)).perform(typeText(song.getTitle()));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.urlInput)).perform(click());
        onView(withId(R.id.urlInput)).perform(typeText(song.getVideo_url()));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.yearInput)).perform(click());
        onView(withId(R.id.yearInput)).perform(typeText(Integer.toString(song.getYear())));
        Espresso.closeSoftKeyboard();

        if (band != null && !band.getName().isEmpty()) {
            onView(withId(R.id.bandListView)).perform(RealmRecyclerViewActions.scrollTo(Matchers.withTextLabeled(band.getName())));
            onView(withText(band.getName())).perform(click());
        }

        onView(withId(R.id.submit_button)).perform(click());
    }

    public void editSong(Song song, Band band ){
        onView(withId(R.id.songListView)).perform(RealmRecyclerViewActions.scrollTo(Matchers.first(Matchers.withTextLabeled(song.getTitle()))));
        onView(Matchers.first(withText(song.getTitle()))).perform(click());
        onView(withId(R.id.titleInput)).check(matches(isDisplayed()));

        onView(withId(R.id.yearInput)).perform(click()).perform(clearText()).perform(typeText(Integer.toString( song.getYear() )));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.bandListView)).perform(RealmRecyclerViewActions.scrollTo(Matchers.withTextLabeled(band.getName())));
        onView(withText(band.getName())).perform(click());

        onView( withId( R.id.submit_button ) ).perform( click() );
    }

    public void addBand( String bandTitle ){

        onView(withId(R.id.button_add_song)).check(matches(isDisplayed()));
        onView(withId(R.id.button_add_song)).perform(click());

        onView(withId(R.id.bandTitleInput)).perform(click());
        onView(withId(R.id.bandTitleInput)).perform(typeText(bandTitle));
        Espresso.closeSoftKeyboard();
        onView(withText(R.string.ok)).perform(click());
    }
}