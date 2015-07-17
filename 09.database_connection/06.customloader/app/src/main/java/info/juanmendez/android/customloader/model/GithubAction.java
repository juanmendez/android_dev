package info.juanmendez.android.customloader.model;

/**
 * Created by Juan on 7/13/2015.
 */
public class GithubAction
{
    public static final String GITHUB_RELOAD = "github_reload";
    public static final String GITHUB_CANCEL = "github_canceled";
    public static final String GITHUB_NONE = "github_none";

    public GithubAction( String _action ){

        if( _action.equals( GITHUB_RELOAD ) || _action.equals(GITHUB_CANCEL) )
            action = _action;
        else
            action = GITHUB_NONE;
    }

    private String action;
    public String getAction() {
        return action;
    }
}
