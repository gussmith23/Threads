package us.justg.threads;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Gus on 11/24/2014.
 */
public class MessageView extends LinearLayout implements View.OnClickListener {

    private TextView titleTextView;
    private TextView messageTextView;
    private LinearLayout mLinearLayout;

    private GestureDetector gestureDetector;
    OnTouchListener gestureListener;
    private GestureDetectorCompat mDetector;

    private boolean mHighlighted = false;

    private Message m;

    public MessageView(Context context, Message m) {
        super(context);

        this.m = m;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.message, this, true);

        mLinearLayout = (LinearLayout) getChildAt(0);


        titleTextView = (TextView) mLinearLayout.getChildAt(0);
        messageTextView = (TextView) mLinearLayout.getChildAt(1);

        titleTextView.setText("Message from " + m.getSender());
        messageTextView.setText(m.getMessage());

        setWillNotDraw(false);

        MessageTapListener messageTapListener = new MessageTapListener();
        mDetector = new GestureDetectorCompat(getContext(),messageTapListener);
        OnTouchListener onTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        };
        messageTextView.setOnTouchListener(onTouchListener);
        titleTextView.setOnTouchListener(onTouchListener);



    }


    /**
     *
     * @author Devunwired on Stackoverflow
     * @author Gus Smith
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        titleTextView.measure(0,0);

        int desiredWidth = titleTextView.getMeasuredWidth();

        getLayoutParams().width = desiredWidth;

        messageTextView.measure(0,0);

        int desiredHeight = titleTextView.getMeasuredHeight() + messageTextView.getMeasuredHeight();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS - for some reason setMeasured... doesn't work here, but this does.
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width,widthMode),
                MeasureSpec.makeMeasureSpec(height,heightMode));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mHighlighted) {
            setBackgroundColor(Color.BLACK);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        mDetector.onTouchEvent(ev);

        return super.onInterceptTouchEvent(ev);
    }

    // This changes the message's text, not the view's.
    public void setMessageText(String newMessageText) {
        m.setMessage(newMessageText);
    }

    public boolean toggleHighlight() {
        m.setmHighlighted(!m.ismHighlighted());

        return mHighlighted;
    }


    @Override
    public void onClick(View v) {

        mHighlighted = !mHighlighted;

        //((MessageView) v).toggleHighlight();

        invalidate();
    }

    private class MessageTapListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            toggleHighlight();
            //invalidate();
            requestLayout();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public void setBackground(int resource) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable( getResources().getDrawable(resource) );
        } else {
            mLinearLayout.setBackground( getResources().getDrawable(resource));
        }
    }
}


