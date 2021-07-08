package com.example.myphone.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myphone.R;

public class EditTextWithDel extends EditText {

    private Drawable imgInable;
    private Drawable imgAble;
    private Context context;
    public EditTextWithDel(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        imgAble = context.getResources().getDrawable(R.drawable.ic_shanchu_2);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (length() != 0) {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (length() == 0){
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
                }
            }
        });
    }

    /*
    删除事件
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP){
            int eventX = (int) event.getX();
            int eventY = (int) event.getY();
//            Rect rect = new Rect();
//            getGlobalVisibleRect(rect);
//            rect.left = rect.right - 50;
            if (getWidth() - eventX <= getCompoundPaddingRight()){
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
}
