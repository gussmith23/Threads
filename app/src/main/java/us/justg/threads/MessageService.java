package us.justg.threads;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Gus on 12/16/2014.
 */
public class MessageService extends Service {

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        MessageService getService() {
            return MessageService.this;
        }
    }

    /**
     * Asks the service to collect and return the messages from the database.
     *
     * @return
     */
    public ArrayList<Message> getMessages() {

        // Testing.
        Message rootMessage = new Message("Gus", "hey yo whatdup", 1);
        Message message2 = new Message("Bubby", "Haha",2);
        Message message3 = new Message("Sumby", "Help lol",3);
        Message message4 = new Message("Alton Brown", "I'm on Netflix idiot", 4);
        Message message5 = new Message("A Dog","I've got $10k on Randy Orton tonight on WWE (on USA network)", 5);

        rootMessage.addReply(2,4);
        message2.addReply(3);
        message4.addReply(5);

        ArrayList<Message> testArray = new ArrayList<Message>();
        testArray.add(rootMessage);
        testArray.add(message2);
        testArray.add(message3);
        testArray.add(message4);
        testArray.add(message5);


        return null;
    }
}
