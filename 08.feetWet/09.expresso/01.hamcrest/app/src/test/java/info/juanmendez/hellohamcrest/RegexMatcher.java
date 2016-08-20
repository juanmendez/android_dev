package info.juanmendez.hellohamcrest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Pattern;

/**
 * Created by musta on 8/19/2016.
 */
public class RegexMatcher extends TypeSafeMatcher<String> {
    private final Pattern pattern;

    public RegexMatcher(String urlMatcher) {
        pattern = Pattern.compile( urlMatcher );
    }

    @Override
    protected boolean matchesSafely(String item) {

        return pattern.matcher( item ).find();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText( "must match " + this.pattern.toString() );
    }

    public static RegexMatcher finder(String reg ){
        return new RegexMatcher( reg );
    }
}
