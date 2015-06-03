package com.commonsware.android.frw.filesdemo.model;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONComposer;
import com.fasterxml.jackson.jr.ob.JSONObjectException;
import com.fasterxml.jackson.jr.ob.comp.ObjectComposer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model for each page
 */
public class Page
{
    /**
     * lets fill these to their defaults, so that we don't worry
     * for exceptions when creating PageFragment
     */
    private String fileName = "";
    private Date dateCreated = new Date();
    private String content = "";
    private String title = "";
    private Boolean visible = false;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateCreated(long mils) {
        this.dateCreated = new Date(mils);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    private static final SimpleDateFormat formatter = new SimpleDateFormat("EEEE, yyyy/MM/dd/hh:mm:ss");

    public Page()
    {
    }

    public Page( String fileName, Date dateCreated, String title, String content, Boolean visible )
    {
        this.fileName = fileName;
        this.dateCreated = dateCreated;
        this.content = content;
        this.title = title;
        this.visible = visible;
    }

    public static String getJSONPage( Page page ) throws IOException
    {
        JSON json = JSON.std;
        JSONComposer composer = json.with(JSON.Feature.PRETTY_PRINT_OUTPUT).composeString();
        ObjectComposer objectComposer = composer.startObject();

        objectComposer.put( "title", page.getTitle() ).
                put("dateCreated", page.getDateCreated().getTime()).
                put("fileName", page.getFileName()).
                put("content", page.getContent() ).
                put("visible", page.getVisible() ).end();

        return composer.finish().toString();
    }
}
