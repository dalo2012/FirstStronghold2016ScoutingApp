package com.example.davidreetz.firststronghold2016scoutingapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Daryl on 6/15/2015.
 */
public class Counter extends RelativeLayout {

    private Button bPlus, bMinus;
    private TextView tvTitle, tvCount;
    private int count, max, min, color, plusColor, minusColor;
    private float textSize;
    private String title;
    private OnClickListener listen;

    public Counter(Context context) {
        super(context);
        initializeViews(context);
    }

    public Counter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initializeViews(context);
    }

    public Counter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        initializeViews(context);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray;
        typedArray = context
                .obtainStyledAttributes(attrs, R.styleable.Counter);
        title = typedArray.getString(0);
        max = typedArray.getInt(R.styleable.Counter_max, Integer.MAX_VALUE);
        min = typedArray.getInt(R.styleable.Counter_min, 0);
        color = typedArray.getColor(R.styleable.Counter_textColor, Color.BLACK);
        plusColor = typedArray.getColor(R.styleable.Counter_plusColor, Color.GREEN);
        minusColor = typedArray.getColor(R.styleable.Counter_minusColor, Color.RED);
        textSize = typedArray.getDimension(R.styleable.Counter_textSize, 15);
        typedArray.recycle();
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.counter, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Sets the images for the previous and next buttons. Uses
        // built-in images so you don't need to add images, but in
        // a real application your images should be in the
        // application package so they are always available.
        bPlus = (Button) findViewById(R.id.bPlus);
        bMinus = (Button) findViewById(R.id.bMinus);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvCount = (TextView) findViewById(R.id.tvCount);

        OnClickListener listen = new OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bPlus:
                        setCount(count + 1);
                        break;
                    case R.id.bMinus:
                        setCount(count - 1);
                        break;
                }
                call();
            }
        };
        bPlus.setOnClickListener(listen);
        bMinus.setOnClickListener(listen);
        init2();
    }

    public void call() {
        if (listen != null) {
            listen.onClick(this);
        }
    }

    public void init2() {
        if (title != null)
            tvTitle.setText(title);
        else
            tvTitle.setText("Counter");
        tvTitle.setTextColor(color);
        tvCount.setTextColor(color);
        tvCount.setText(Integer.toString(count));
        bPlus.setBackgroundColor(plusColor);
        bMinus.setBackgroundColor(minusColor);
        setSize(textSize);
    }

    public void setCount(int nCount) {
        if (nCount >= min && nCount <= max) {
            count = nCount;
            tvCount.setText(Integer.toString(count));
        }
    }

    public int getCount() {
        return count;
    }

    public void setSize(float size) {
        textSize = size;
        tvTitle.setTextSize(textSize + 5);
        tvCount.setTextSize(textSize);
        bPlus.setTextSize(textSize);
        bMinus.setTextSize(textSize);
    }

    public float getSize() {
        return textSize;
    }

    public void setMin(int nMin) {
        min = nMin;
        if (count < min) {
            setCount(min);
        }
    }

    public int getMin() {
        return min;
    }

    public void setMax(int nMax) {
        max = nMax;
        if (count > max) {
            setCount(max);
        }
    }

    public int getMax() {
        return max;
    }

    public void setTitle(String nTitle) {
        title = nTitle;
        tvTitle.setText(title);
    }

    public String getTitle() {
        return title;
    }

    public void setColor(int nColor) {
        color = nColor;
        tvTitle.setTextColor(color);
        tvCount.setTextColor(color);
    }

    public int getColor() {
        return color;
    }

    public void setPlusColor(int nColor) {
        plusColor = nColor;
        bPlus.setBackgroundColor(plusColor);
    }

    public int getPlusColor() {
        return plusColor;
    }

    public void setMinColor(int nColor) {
        minusColor = nColor;
        bMinus.setBackgroundColor(minusColor);
    }

    public void setOnClickListener(OnClickListener nListen) {
        listen = nListen;
    }
}