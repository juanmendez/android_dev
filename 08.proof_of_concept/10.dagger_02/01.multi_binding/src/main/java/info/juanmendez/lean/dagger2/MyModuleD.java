package info.juanmendez.lean.dagger2;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by musta on 2/4/2017.
 */
@Module
public class MyModuleD {

    @Provides
    @ElementsIntoSet
    @MyQualifier
    static Set<String> provideSomeStrings(){
        return new HashSet<String>(Arrays.asList("MNO", "PQR"));
    }
}
