package info.juanmendez.realminit.models;

/**
 * Created by Juan Mendez on 10/10/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class SongCom{

    public static final int READ = 0;
    public static final int DELETE = -1;
    public static final int ADD = 1;

    public static final int NONE = 0;
    public static final int NO = 1;
    public static final int YES = 2;
    public static final int COMPLETED = 3;


    private Song song;
    private int status;
    private int confirm = NONE;


    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public boolean isSameSong( Song song ){

        if( this.song == null )
            return false;

        return this.song.getId() == song.getId();
    }

    public static SongCom createEmpty(){
        Song emptySong = new Song();
        SongCom songCom = new SongCom();
        songCom.setSong( emptySong );
        songCom.setStatus( SongCom.ADD );

        return songCom;
    }
}
