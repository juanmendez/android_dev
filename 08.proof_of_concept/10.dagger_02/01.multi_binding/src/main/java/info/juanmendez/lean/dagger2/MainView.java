package info.juanmendez.lean.dagger2;

import javax.inject.Inject;

/**
 * Created by musta on 2/4/2017.
 */
public class MainView {


    @Inject
    Bar bar;

    @Inject
    @MyQualifier
    Bar qualifiedBar;

    @Inject
    public MainView(){
        Application.myComponent.inject(this);
        System.out.println( "size " + bar.getStrings().size() + ", " + qualifiedBar.strings.size() );
    }
}
