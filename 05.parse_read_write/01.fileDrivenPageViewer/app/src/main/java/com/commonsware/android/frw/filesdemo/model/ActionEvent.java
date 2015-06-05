package com.commonsware.android.frw.filesdemo.model;

import java.io.File;

public class ActionEvent
{
    private ActionType _action;
    private File _file;
    private String _content = "";

    public ActionEvent( ActionType action )
    {
        _action = action;
    }

    public ActionEvent( ActionType action, File file )
    {
        _action = action;
        _file = file;
    }

    public ActionType getAction()
    {
        return _action;
    }

    public File getFile()
    {
        return _file;
    }

    public void setContent( String content )
    {
        _content = new String(content);
    }

    public String getContent() {
        return _content;
    }

    public enum ActionType
    {
        LOAD( "load"),
        SAVE("save"),
        DELETE("delete"),
        DELETE_CONFIRMED("deleteConfirmed"),
        UPDATE( "update");

        public String name;

        ActionType( String name )
        {
            this.name = name;
        }
    }
}
