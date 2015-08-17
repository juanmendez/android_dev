package info.juanmendez.android.simplealarm.events;

import android.graphics.drawable.Drawable;

/**
 * Created by Juan on 8/16/2015.
 */
public class NotificationEvent
{
    private String title;
    private String message;
    private String content;
    private int smallIcon;

    public NotificationEvent(String content, String message, int smallIcon, String title) {
        this.content = content;
        this.message = message;
        this.smallIcon = smallIcon;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }

    public int getSmallIcon() {
        return smallIcon;
    }

    public String getTitle() {
        return title;
    }
}
