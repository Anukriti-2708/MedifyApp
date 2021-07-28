package com.example.medify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register1;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressdialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register1 = (TextView)findViewById(R.id.register1);
        register1.setOnClickListener(this);
        signIn = (Button)findViewById(R.id.signin);
        signIn.setOnClickListener(this);
        editTextEmail= (EditText)findViewById(R.id.email1);
        editTextPassword= (EditText)findViewById(R.id.password1);


        mAuth= FirebaseAuth.getInstance();
        progressdialog= new ProgressDialog(this);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            finish();
            startActivity(new Intent(MainActivity.this,Dashboard.class));

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register1:
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.signin:
                userlogin();
                 break;

        }
    }

    private void userlogin() {
        progressdialog.setMessage("Take a Break,Have a Kitkat");
        progressdialog.show();

        String email1 = editTextEmail.getText().toString().trim();
        String password1 = editTextPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    if (email1.equalsIgnoreCase("admin@govt.com")&& password1.equalsIgnoreCase("admin1")){
                        progressdialog.dismiss();
                        startActivity(new Intent(MainActivity.this,GovernmentActivity.class));
                    } else {
                        progressdialog.dismiss();
                        startActivity(new Intent(MainActivity.this, Dashboard.class));
                    }
                 }

                  else {
                     progressdialog.dismiss();
                     Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                 }
            }
        });


    }
}