package info.juanmendez.dynamicfragments.modules;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.dynamicfragments.controllers.MainActivity;
import info.juanmendez.dynamicfragments.controllers.MainActivity_;
import info.juanmendez.dynamicfragments.controllers.fragments.LeftFragment_;
import info.juanmendez.dynamicfragments.controllers.fragments.RightFragment;
import info.juanmendez.dynamicfragments.controllers.fragments.RightFragment_;
import info.juanmendez.dynamicfragments.controllers.fragments.TheFragment;
import info.juanmendez.dynamicfragments.helpers.SideHelper;
import info.juanmendez.dynamicfragments.helpers.SideHelper_;

/**
 * Unable to start activity
 * ComponentInfo{info.juanmendez.dynamicfragments/info.juanmendez.dynamicfragments.controllers.MainActivity_}:
 * java.lang.IllegalArgumentException: No inject registered for members/info.juanmendez.dynamicfragments.controllers.MainActivity_.
 * You must explicitly add it to the 'injects' option in one of your modules.
**/
 @Module(
        injects = {
                MainActivity_.class,
                LeftFragment_.class,
                RightFragment_.class,
                SideHelper_.class
        },
        addsTo = AppModule.class,
        library = true
)
public class ActivityModule
{
    private final MainActivity activity;

    public ActivityModule( MainActivity activity )
    {
        this.activity = activity;
    }

    @Provides
    @Singleton
    public MainActivity mainActivityProvider()
    {
        return this.activity;
    }

    @Provides
    public InputMethodManager inputMethodManagerProvider()
    {
        return (InputMethodManager)this.activity.getSystemService(Context.INPUT_METHOD_SERVICE );
    }

    /**
     * This provider is very dried when it comes to Dagger injections. Just by using a breakpoint, I could
     * tell none of those dagger injections are available.
     * @return
     */
    @Provides
    public SideHelper sideHelperProvider()
    {
        return SideHelper_.getInstance_( (Context) this.activity );
    }
}
