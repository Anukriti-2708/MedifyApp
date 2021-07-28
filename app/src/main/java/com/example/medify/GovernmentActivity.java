package com.example.medify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class GovernmentActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button logoutG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government);
        mAuth= FirebaseAuth.getInstance();

        logoutG = (Button) findViewById(R.id.buttonG);
        logoutG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(GovernmentActivity.this,MainActivity.class));
            }
        });
    }
}