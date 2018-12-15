package info.juanmendez.lean.dagger2;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/**
 * Created by musta on 2/4/2017.
 */
@Module
public class MyModuleC {

    @Provides
    @IntoSet
    @MyQualifier
    static String provideOne(){
        return "JKL";
    }
}
