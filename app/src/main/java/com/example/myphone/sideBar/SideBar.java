package com.example.myphone.sideBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myphone.R;


public class SideBar extends View {

    private String[] letters = null;
    private int choose = -1;
    private int letterId = R.array.letter_list;
    private Paint paint;
    private TextView mTextView;

    private OnTouchingLetterListener onTouchingLetterListener;

    public SideBar(Context context) {
        super(context);
        this.letters = context.getResources().getStringArray(letterId);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.letters = context.getResources().getStringArray(letterId);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.letters = context.getResources().getStringArray(letterId);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.letters = context.getResources().getStringArray(letterId);
    }

    private void initPaint(){
        paint = new Paint();
        paint.setTextSize(20);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Log.d("dsafdsafdsa", String.valueOf(height));
        int singleHeight = height / letters.length;
        for (int i = 0; i < letters.length; i++){
            initPaint();
            if (choose == i){
                paint.setColor(Color.RED);
            }
            float x = (width - paint.measureText(letters[i])) / 2;
            float y = (i + 1) * singleHeight;
            canvas.drawText(letters[i], x, y ,paint);
            paint.reset();
        }
    }

    public interface OnTouchingLetterListener {
        void onTouchingLetterListener(String s);
    }

    public void setOnTouchingLetterListener(OnTouchingLetterListener onTouchingLetterListener) {
        this.onTouchingLetterListener = onTouchingLetterListener;
    }

    public boolean dispatchTouchEvent(MotionEvent event){
        final int action = event.getAction();
        final float y = event.getY();

        //计算选中的字母
        int index = (int) (y / getHeight() * letters.length);
        //为防止下标越界，对值进行判断
        if (index >= letters.length){
            index = letters.length - 1;
        }else if (index < 0){
            index =0;
        }
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(Color.GRAY);
                choose = index;
                mTextView.setVisibility(VISIBLE);
                mTextView.setText(letters[index]);
                invalidate();
            case MotionEvent.ACTION_UP:
        }
        return true;
    }
}
