/**
 * Created by Juan on 6/26/2015.
 */

import org.junit.runners.model.InitializationError;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

/**
 * This class helps fix the issue with Manifest, not found by simply using RobolectricTestRunner!
 * @see <a href='http://www.peterfriese.de/android-testing-with-robolectric/'>Android Testing with Robolectric</a>
 */
public class MyGradleTestRunner extends RobolectricTestRunner {

    public MyGradleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String manifestProperty = System.getProperty("android.manifest");
        if (config.manifest().equals(Config.DEFAULT) && manifestProperty != null) {
            String resProperty = System.getProperty("android.resources");
            String assetsProperty = System.getProperty("android.assets");
            return new AndroidManifest(Fs.fileFromPath(manifestProperty), Fs.fileFromPath(resProperty),
                    Fs.fileFromPath(assetsProperty));
        }
        AndroidManifest appManifest = super.getAppManifest(config);
        return appManifest;
    }
}
