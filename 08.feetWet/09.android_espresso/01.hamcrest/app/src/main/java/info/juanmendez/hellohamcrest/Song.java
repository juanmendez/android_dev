package info.juanmendez.hellohamcrest;

/**
 * Created by musta on 8/20/2016.
 */
public class Song {
    private final long id;
    private String title;
    private String video_url;
    private int year;

    public Song(long id, String title, String url, int year) {
        this.id = id;
        this.title = title;
        this.video_url = url;
        this.year = year;
    }

    public long getId() {
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

}
