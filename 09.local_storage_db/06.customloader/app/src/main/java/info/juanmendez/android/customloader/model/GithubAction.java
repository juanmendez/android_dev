package info.juanmendez.android.customloader.model;

/**
 * Created by Juan on 7/13/2015.
 */
public class GithubAction
{
    public static final String GITHUB_RELOAD = "github_reload";
    public static final String GITHUB_CANCEL = "github_canceled";
    public static final String GITHUB_NONE = "github_none";

    private String action = GITHUB_NONE;
    private String query = "";

    public GithubAction( String _action ){

        if( _action.equals( GITHUB_RELOAD ) || _action.equals(GITHUB_CANCEL) )
            action = _action;
        else
            action = GITHUB_NONE;
    }

    public String getAction() {
        return action;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
