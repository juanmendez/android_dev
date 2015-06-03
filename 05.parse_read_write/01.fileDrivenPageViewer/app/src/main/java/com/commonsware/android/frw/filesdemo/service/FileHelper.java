package com.commonsware.android.frw.filesdemo.service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * This is a helper class which does saving and loading
 * I can't remember all the details how to work with files in java, but
 * commons.io simplifies things.
 */
public class FileHelper
{
    public static synchronized void save(String text, File target) throws IOException
    {
        FileUtils.writeStringToFile(target, text);
    }

    public static synchronized String load(File target) throws IOException
    {
        String result = "";

        try
        {
            result = FileUtils.readFileToString( target );
        }
        catch (java.io.FileNotFoundException e)
        {
            // that's OK, we probably haven't created it yet
        }

        return (result);
    }

    public static synchronized void remove( File target ) throws IOException
    {
        FileUtils.forceDelete( target );
    }
}