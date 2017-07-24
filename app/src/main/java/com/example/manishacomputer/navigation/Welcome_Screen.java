package com.example.manishacomputer.navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Welcome_Screen extends AppCompatActivity {
   private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome__screen);
        imageView=(ImageView)findViewById(R.id.imageView);
        Animation animation= AnimationUtils.loadAnimation(Welcome_Screen.this,R.anim.animation);
        imageView.setAnimation(animation);
        animation.setDuration(5000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               startActivity(new Intent(Welcome_Screen.this,loginActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
