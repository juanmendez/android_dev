package info.juanmendez.lean.dagger2;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/**
 * Created by musta on 2/4/2017.
 */
@Module
public class MyModuleA {

    public MyModuleA(){

    }

    @Provides @IntoSet
    static String provideOneString(){
        return "ABC";
    }
}
