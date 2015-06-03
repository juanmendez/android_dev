package com.commonsware.android.frw.filesdemo;

import java.io.File;
import java.util.Date;

/**
 * Created by Juan on 5/23/2015.
 */
public class DeleteEvent
{
    private File file;
    private Date date;

    DeleteEvent( File f, Date d ){
        file = f;
        date = d;
    }

    public File getFile()
    {
        return file;
    }

    public Date getDate()
    {
        return date;
    }
}
