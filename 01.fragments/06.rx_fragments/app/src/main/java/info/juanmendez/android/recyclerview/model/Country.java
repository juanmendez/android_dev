package info.juanmendez.android.recyclerview.model;

/**
 * Created by Juan on 10/19/2015.
 */
public class Country {
    String name;
    String link;
    String flag;

    public Country( String _name, String _link, String _flag ){
        name = _name;
        link = _link;
        flag = _flag;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getFlag() {
        return flag;
    }
}
