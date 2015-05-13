package info.juanmendez.android.asynctask00;

/**
 * Created by Juan on 5/12/2015.
 */
public enum ActionEvent
{
    GO( "go"),STOP("stop");

    private String action;
    ActionEvent( String action )
            {
                this.action = action;
            }

    String getAction()
    {
        return action;
    }

}
