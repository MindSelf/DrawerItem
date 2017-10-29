package com.example.zhaolexi.draweritem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

/**
 * Created by ZHAOLEXI on 2017/10/29.
 */

public class DrawerItem extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener {

    private static final String TAG = "DrawerItem";

    private View mView;
    private View mDrawerView;
    private int mDrawerViewHeight;
    private boolean mHasDrawn;
    private boolean mIsAnimating;
    private long mDuration=500;
    private ValueAnimator mAnimatorDrawing;
    private ValueAnimator mAnimatorFolding;


    public DrawerItem(Context context,View view, View drawerView){
        super(context);
        init();
    }

    public DrawerItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawerItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onGlobalLayout() {
        mView=getChildAt(0);
        mDrawerView = getChildAt(1);
        mDrawerViewHeight=mDrawerView.getHeight();
        mDrawerView.getLayoutParams().height=0;
        mDrawerView.requestLayout();
        mView.setOnClickListener(this);
        initAnimator();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onClick(View v) {
        if(mIsAnimating)
            return;
        if(mHasDrawn){
            mAnimatorFolding.start();
            Log.d(TAG, "onClick: folding");
            mHasDrawn=false;
        }else{
            Log.d(TAG, "onClick: drawing");
            mAnimatorDrawing.start();
            mHasDrawn=true;
        }
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private void initAnimator() {
        mAnimatorDrawing=ValueAnimator.ofInt(mDrawerViewHeight).setDuration(mDuration);
        mAnimatorDrawing.setTarget(mDrawerView);
        mAnimatorDrawing.setInterpolator(new LinearInterpolator());
        mAnimatorDrawing.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height=(int) animation.getAnimatedValue();
                mDrawerView.getLayoutParams().height=height;
                mDrawerView.requestLayout();
                Log.d(TAG, "onAnimationUpdate: "+height);
            }
        });
        mAnimatorDrawing.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                mIsAnimating=false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimating=false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimating=true;
            }
        });
        mAnimatorFolding = ValueAnimator.ofInt(mDrawerViewHeight, 0).setDuration(mDuration);
        mAnimatorFolding.setTarget(mDrawerView);
        mAnimatorFolding.setInterpolator(new LinearInterpolator());
        mAnimatorFolding.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height=(int) animation.getAnimatedValue();
                mDrawerView.getLayoutParams().height=height;
                mDrawerView.requestLayout();
                Log.d(TAG, "onAnimationUpdate: "+height);
            }
        });
        mAnimatorFolding.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                mIsAnimating=false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimating=false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimating=true;
            }
        });
    }
}
