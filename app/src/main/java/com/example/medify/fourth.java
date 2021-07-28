package com.example.medify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class fourth extends AppCompatActivity {
    private Button button;
    private Button button2;

    ViewPager viewPager;
    LinearLayout dots;
    Animation sideAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        button= (Button) findViewById(R.id.skip_btn);

        viewPager=findViewById(R.id.slider);
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        button= (Button) findViewById(R.id.skip_btn);
        button2= (Button) findViewById(R.id.next_btn);

        //set animation on elements
        viewPager.setAnimation(sideAnim);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendash();
            }

            public void opendash(){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opennext();
            }

            public void opennext(){
                Intent intent = new Intent(getApplicationContext(),fifth.class);
                startActivity(intent);
                finish();
            }
        });

    }
}