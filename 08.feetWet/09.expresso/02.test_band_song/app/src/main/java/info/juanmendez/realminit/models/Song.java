package info.juanmendez.realminit.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Song extends RealmObject{

    @PrimaryKey
    private int id = -1;
    private String title;
    private String video_url;
    private int year;
    private Band band;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public int getYear() {
        return year;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }
}