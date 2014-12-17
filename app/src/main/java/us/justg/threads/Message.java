package us.justg.threads;

import java.util.ArrayList;

/**
 * Created by Gus on 11/24/2014.
 */
public class Message {

    private String sender;
    private String message;

    private final int mID;

    private boolean mHighlighted;

    public boolean ismHighlighted() {
        return mHighlighted;
    }

    public void setmHighlighted(boolean mHighlighted) {
        this.mHighlighted = mHighlighted;
    }

    private ArrayList<Message> replies;

    // IDs of all messages which are responses to this message.
    private ArrayList<Integer> mReplyIDs;

    /**
     *
     * @param sender
     * @param message
     * @deprecated
     */
    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
        replies = new ArrayList<Message>();
        this.mID = 0;
    }

    public Message(String sender, String message, int id) {
        this.sender = sender;
        this.message = message;
        //replies = new ArrayList<Message>();
        this.mID = id;
        this.mReplyIDs = new ArrayList<Integer>();
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

    /**
     *
     * @param m
     * @return
     * @deprecated
     */
    public Message addReply(Message m) {
        replies.add(m);
        return m;
    }

    public void addReply(int... replyID) {
        for (int reply : replyID) {
            mReplyIDs.add(reply);
        }
    }

    public ArrayList<Message> getReplies() {
        return replies;
    }

    //
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
