package us.justg.threads;

import android.provider.BaseColumns;

/**
 * Created by Gus on 12/17/2014.
 */
public final class ThreadsContract {

    public  ThreadsContract() {}

    public static abstract class Messages implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_MESSAGE_ID = "messageid";
        public static final String COLUMN_NAME_SENDER = "sender";
        public static final String COLUMN_NAME_MESSAGE = "message";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_DURATION = "duration";
    }

    public static abstract class ParentChildPairs implements BaseColumns {
        public static final String TABLE_NAME = "parentchildpairs";
        public static final String COLUMN_NAME_PARENT_ID = "parentid";
        public static final String COLUMN_NAME_CHILD_ID = "childid";
    }

}
