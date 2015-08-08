package info.juanmendez.android.intentservice.model;

/**
 * Created by Juan on 8/1/2015.
 */
public class Page
{
    private int id;
    private int magId;
    private int position;
    private String name;

    public int getMagId() {
        return magId;
    }

    public void setMagId(int magId) {
        this.magId = magId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format( "Magazine( issue: %s, location: %s, title: %s, file_location: %s )", id, magId, position, name );
    }
}
