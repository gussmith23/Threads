package us.justg.threads;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * @author Paresh Mayani (for pinch zoom stuff)
 * @author Gus Smith
 */
public class MessagesRelativeLayout extends RelativeLayout  {
    float mScaleFactor = 1;
    float mPivotX;
    float mPivotY;

    float mTranslateX = 0;
    float mTranslateY = 0;

    private static float MIN_ZOOM = 1f;
    private static float MAX_ZOOM = 5f;

    private boolean mScrolling = false;

    ScaleGestureDetector mScaleGestureDetector;
    GestureDetector mGestureDetector;

    ArrayList<Message> rootMessages;

    int debugcounter = 0;

    // X and Y contain the indices of the messages to draw lines between.
    ArrayList<Point> linesToDraw;


    public MessagesRelativeLayout(Context context) {
        this(context, null, 0);
    }

    public MessagesRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessagesRelativeLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);

        linesToDraw = new ArrayList<Point>();

        //addView(new MessageView(context));
        Log.d(Map.TAG, "added");

        Message rootMessage = new Message("Gus", "hey yo whatdup");
        rootMessage.addReply(new Message("Bubby", "Haha")).addReply(new Message("Sumby", "Help lol"));
        rootMessage.addReply(new Message("Alton Brown", "I'm on Netflix idiot")).addReply(new Message("A Dog","I've got $10k on Randy Orton tonight on WWE (on USA network)"));

        rootMessages = new ArrayList<Message>();
        rootMessages.add(rootMessage);

        mScaleGestureDetector = new ScaleGestureDetector(context, new OnPinchListener());
        mGestureDetector = new GestureDetector(context, new OnScrollListener());


        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mScaleGestureDetector.onTouchEvent(event);
                mGestureDetector.onTouchEvent(event);

                return true;
            }
        });


        // Debug
        addView(
                new MessageView(
                        context,
                        new Message("Gus", "bingo bango")
                )
        );

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {


        //TODO fix this horrible zooming!!!!


        //debug
        //Paint p = new Paint(); p.setColor(Color.BLACK);
        //canvas.drawCircle((float)mPivotX,(float)mPivotY,10.0f, p);

        canvas.save(Canvas.MATRIX_SAVE_FLAG);

        // Draw lines.
        drawLines(canvas);


        super.dispatchDraw(canvas);
        canvas.restore();
    }

    private void drawLines(Canvas canvas) {

        for (Point p : linesToDraw) {

            View parent = getChildAt(p.x);
            View child = getChildAt(p.y);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);

            canvas.drawLine(parent.getX(),parent.getY(),child.getX(),child.getY(), paint);


        }

    }

    public void scale(float scaleFactor, float pivotX, float pivotY) {
        mScaleFactor = scaleFactor;
        mPivotX = pivotX;
        mPivotY = pivotY;

        updateScaleAndTranslateOnChildren();

        this.invalidate();
    }

    public void restore() {
        mScaleFactor = 1;
        this.invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //Log.d(Map.TAG, "on Layout");

        // Clear views.
        removeAllViews();
        linesToDraw = new ArrayList<Point>();

        for (Message rootMessage : rootMessages) {

            generateMessageViewsRecursion(rootMessage, -1);

        }

        for (int i = 0; i < getChildCount(); i++) {

            MessageView child = (MessageView) getChildAt(i);

            child.measure(0,0);

            int childl = 500*i;
            int childr = childl + child.getMeasuredWidth();
            int childt = childl;
            int childb = childt + child.getMeasuredHeight();
            child.layout(childl,childt,childr,childb);
            //child.layout(0,0,500,500);
        }


        // Maybe call this here? The views are losing their translations when highlighted, but the
        //      the translations come back on drag.
        // update - nope
        //invalidate();
        // Maybe this?
        // nope
        //super.onLayout(changed,l,t,r,b);

    }

    private void generateMessageViewsRecursion(Message rootMessage, int parentIndex) {

        MessageView messageViewToAdd = generateMessageView(this.getContext(),rootMessage);

        debugcounter++;

        addView(messageViewToAdd);

        // Find the index of this message.
        int thisMessageIndex = getChildCount() - 1;


        // you can't do this here (value vs reference problem...)
        //int l = 100*debugcounter;
        //int t = l;
        //messageViewToAdd.layout(l,t,l+500, t+500);

        for (Message m : rootMessage.getReplies()) {

            int nextChildIndex = getChildCount();

            // Add a line between them.
            addConnectingLine(thisMessageIndex, nextChildIndex);

            // Run recursion
            generateMessageViewsRecursion(m, thisMessageIndex);
        }
    }

    private MessageView generateMessageView(Context context, Message rootMessage) {
        MessageView messageView = new MessageView(context, rootMessage);

        if (rootMessage.ismHighlighted()) {
            messageView.setBackground(R.drawable.message_view_border_highlighted);
        }

        return messageView;

    }

    private void addConnectingLine(int parentIndex, int nextChildIndex) {
        // Add to line array
        linesToDraw.add(new Point(parentIndex,nextChildIndex));
    }

    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int cellWidthSpec = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);
        int cellHeightSpec = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);

        int count = getChildCount();
        for (int index=0; index<count; index++) {
            final View child = getChildAt(index);
            child.measure(cellWidthSpec, cellHeightSpec);
        }

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));

    }*/


    private class OnPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        float startingSpan;
        float endSpan;
        float startFocusX;
        float startFocusY;

        // The old scale factor.
        float oldScale;


        public boolean onScaleBegin(ScaleGestureDetector detector) {

            oldScale = mScaleFactor;

            startingSpan = detector.getCurrentSpan();
            //startFocusX = detector.getFocusX();
            //startFocusY = detector.getFocusY();

            return true;
        }


        public boolean onScale(ScaleGestureDetector detector) {
            //scale(oldScale * detector.getCurrentSpan() / startingSpan, startFocusX, startFocusY);
            //scale(oldScale * detector.getCurrentSpan() / startingSpan, detector.getFocusX()-mTranslateX/mScaleFactor, detector.getFocusY()-mTranslateY/mScaleFactor);
            scale(oldScale * detector.getCurrentSpan() / startingSpan, detector.getFocusX(), detector.getFocusY());

            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            //restore();
        }
    }

    private class OnScrollListener implements GestureDetector.OnGestureListener {


        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            mTranslateX -= distanceX;
            mTranslateY -= distanceY;

            updateScaleAndTranslateOnChildren();

            //setTranslationX(mTranslateX);
            //setTranslationY(mTranslateY);

            invalidate();

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private void updateScaleAndTranslateOnChildren() {

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            //TODO implement proper scaling (including transltion correction)

            child.setTranslationX(mTranslateX);
            child.setTranslationY(mTranslateY);



            /*
            child.setScaleX(mScaleFactor);
            child.setScaleY(mScaleFactor);
            child.setPivotX(mPivotX);
            child.setPivotY(mPivotY);*/
        }

    }


}
