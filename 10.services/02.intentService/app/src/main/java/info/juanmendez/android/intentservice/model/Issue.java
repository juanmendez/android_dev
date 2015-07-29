package info.juanmendez.android.intentservice.model;

public class Issue {

    private String volume;
    private String location;

    /**
     *
     * @return
     * The volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     *
     * @param volume
     * The volume
     */
    public void setVolume(String volume) {
        this.volume = volume;
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