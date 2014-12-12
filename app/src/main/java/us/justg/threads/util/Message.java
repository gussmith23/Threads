package us.justg.threads.util;

import android.view.View;
import java.util.ArrayList;

/**
 * Created by Gus on 11/24/2014.
 */
public class Message {

    private String sender;
    private String message;

    private boolean mHighlighted;

    public boolean ismHighlighted() {
        return mHighlighted;
    }

    public void setmHighlighted(boolean mHighlighted) {
        this.mHighlighted = mHighlighted;
    }

    private ArrayList<Message> replies;

    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
        replies = new ArrayList<Message>();
    }






    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message addReply(Message m) {
        replies.add(m);
        return m;
    }

    public ArrayList<Message> getReplies() {
        return replies;
    }

    //
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
