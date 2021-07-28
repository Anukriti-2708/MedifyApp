 package com.example.medify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {

    private static int SPLASH_TIMER=3000;

        //var
        ImageView backgroundImage;
        //Animation
        Animation sideAnim,bottomAnim;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            
            //hooks
            backgroundImage = findViewById(R.id.background_image);

            //Animation
            sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
            bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

            //set animation on elements
            backgroundImage.setAnimation(sideAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(getApplicationContext(),second.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIMER);


        }
    }

