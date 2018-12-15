package info.juanmendez.lean.dagger2;

import java.util.Set;

/**
 * Created by musta on 2/4/2017.
 */
public class Bar {

    Set<String> strings;

    Bar(Set<String> strings) {
        this.strings = strings;

    }

    public Set<String> getStrings() {
        return strings;
    }
}
