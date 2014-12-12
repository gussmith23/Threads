package us.justg.threads;

import us.justg.threads.util.SystemUiHider;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Map extends Activity {

    /**
     * Gus's stuff
     */
    MessagesRelativeLayout messagesRelativeLayout;
    static final String TAG = "Threads";
    float startingSpan;
    float endSpan;
    float startFocusX;
    float startFocusY;
    GestureDetectorCompat mGestureDetectorCompat;


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        messagesRelativeLayout = (MessagesRelativeLayout) findViewById(R.id.map);


        /*
        // Stuff Gus added.
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(this, new OnPinchListener());
        messageViewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });*/



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }



}
