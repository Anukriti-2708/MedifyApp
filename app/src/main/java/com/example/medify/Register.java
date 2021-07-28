 package com.example.medify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Register extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText UserName;
    EditText Phone;
    EditText District;
    EditText Email;
    EditText adhar;
    EditText password;
    Button b1;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Phone = (EditText) findViewById(R.id.Phone);
        UserName = (EditText) findViewById(R.id.userName);
        District = (EditText) findViewById(R.id.District);
        Email = (EditText) findViewById(R.id.email);
        adhar = (EditText) findViewById(R.id.adhar);
        password = (EditText)findViewById(R.id.password);
        b1 = (Button) findViewById(R.id.register);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(Phone);

        mAuth = FirebaseAuth.getInstance();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String user_email = Email.getText().toString().trim();
                    String user_password = password.getText().toString().trim();
                    String codepicker = ccp.getDefaultCountryCode().toString().trim();
                    mAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                  go();

                            }
                            else {
                                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();

                            }




                        }
                    });
                }

            }

            private void go() {
                Intent intent = new Intent(Register.this, manageotp.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ", ""));
                startActivity(intent);
            }
        });
    }
    private Boolean validate() {
        Boolean result= false;
        String name = UserName.getText().toString();
        String pass = password.getText().toString();
        String email = Email.getText().toString();


        if (name.isEmpty() || pass.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Enter all the details", Toast.LENGTH_SHORT).show();

        }
        else {
            result = true;
        }
        return result;
    }

    }
