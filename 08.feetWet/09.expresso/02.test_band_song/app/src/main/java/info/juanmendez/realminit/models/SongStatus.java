package info.juanmendez.realminit.models;

/**
 * Created by Juan Mendez on 9/25/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
public class SongStatus {
    public static final String ADDED = "added";
    public static final String UPDATED = "updated";

    int songId;
    String type;

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus( String type, int songId ){
        setType( type );
        setSongId( songId );
    }
}
