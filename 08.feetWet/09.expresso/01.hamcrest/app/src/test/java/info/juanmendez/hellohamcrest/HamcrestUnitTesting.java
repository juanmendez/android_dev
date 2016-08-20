package info.juanmendez.hellohamcrest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class HamcrestUnitTesting {
    @Test
    public void addition_isCorrect() throws Exception {
        List<String> names = Arrays.asList( "Peter", "Ricardo", "Tad", "Adam");

        //is the length of the list 4?
        assertThat( names, hasSize(4) );

        //do we have an element after Tad Renstrom?
        assertThat( names, hasItem("Tad"));

        //does the list have Juan in it?
        assertThat( names, not(hasItem("Juan")) );

        //is first element Peter?
        assertThat( names.get(0), is( "Peter") );

        //does the list have the following items?
        assertThat( names, hasItems("Peter", "Tad"));

        //do names length are all equal or less than 8 characters?
        assertThat( names, everyItem( NameLengthMatcher.equalOrLessThan(8)));
    }

    @Test
    public void regexTest(){
        assertThat( "http://www.country-to-visit.com/country?id=123456", RegexMatcher.finder( "id=\\d+$" ) );
    }

    @Test
    public void beanTest(){

        Song song1 = new Song(0, "Paradise City", "https://www.youtube.com/watch?v=Rbm6GXllBiw", 1987 );
        Song song2 = new Song(1, "Welcome to the Jungle", "https://www.youtube.com/watch?v=o1tj2zJ2Wvg", 1987 );

        //do we have a property named video_url?
        assertThat( song1, hasProperty("video_url"));

        //is video value coming from youtube?
        assertThat( song1.getVideo_url(), RegexMatcher.finder("^(https?:\\/\\/)?(www)?\\.youtube\\.com") );

        //are song1 and song2 different?
        assertThat( song1, not(samePropertyValuesAs(song2)));
    }
}