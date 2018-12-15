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
public class MyModuleB {

    public MyModuleB(){

    }

    @Provides
    @ElementsIntoSet
    static Set<String> provideSomeStrings(){
        return new HashSet<String>(Arrays.asList("DEF", "GHI"));
    }

    @Provides
    public Bar providesBar(Set<String> strings){
        return new Bar(strings);
    }

    @Provides
    @MyQualifier
    public Bar providesQualifiedBar(@MyQualifier Set<String> strings ){
        return  new Bar( strings );
    }
}
