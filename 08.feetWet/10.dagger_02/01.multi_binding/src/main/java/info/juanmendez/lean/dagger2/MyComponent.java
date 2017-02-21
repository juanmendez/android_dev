package info.juanmendez.lean.dagger2;

import dagger.Component;

import javax.inject.Singleton;
import java.util.Set;

/**
 * Created by musta on 2/4/2017.
 */
@Singleton
@Component(modules = {MyModuleA.class, MyModuleB.class, MyModuleC.class, MyModuleD.class})
public interface MyComponent {

    Set<String> strings();

    @MyQualifier
    Set<String> stringsFromQualifier();

    void inject( MainView mainView );
}
