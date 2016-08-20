package info.juanmendez.hellohamcrest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by musta on 8/19/2016.
 */
public class NameLengthMatcher extends TypeSafeMatcher<String> {
    private final int length;

    public static NameLengthMatcher equalOrLessThan(int length ){
        return new NameLengthMatcher(length);
    }

    public NameLengthMatcher(int length) {
        this.length = length;
    }

    @Override
    protected boolean matchesSafely(String item) {
        return item.length() <= this.length;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText( "gotta mach this length " + this.length );
    }
}
