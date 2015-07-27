package info.juanmendez.android.intentservice.model;

import java.util.HashMap;
import java.util.Map;

public class Version {

    private String version;
    private String location;

    /**
     *
     * @return
     * The version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     * The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}