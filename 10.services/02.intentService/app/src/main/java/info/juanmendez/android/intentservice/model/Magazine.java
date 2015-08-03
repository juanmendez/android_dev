package info.juanmendez.android.intentservice.model;

import java.util.Date;

public class Magazine {

    private int id;
    private float issue;
    private String location = "";
    private String fileLocation = "";
    private String title;

    private String status = MagazineStatus.AVAILABLE;
    private Date dateTime = new Date();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The issue
     */
    public float getIssue() {
        return issue;
    }

    /**
     *
     * @param issue
     * The issue
     */
    public void setIssue(float issue) {
        this.issue = issue;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format( "Magazine( issue: %s, location: %s, title: %s, file_location: %s )", issue, location, title, fileLocation );
    }
}