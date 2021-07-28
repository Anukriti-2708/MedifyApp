package com.example.medify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Dashboard extends AppCompatActivity {
    int PICK_IMAGE_REQUEST=111;
    ImageView imageView;
    Button upload;
    Uri filepath;
    String downloadUrl;
    private TextView Logouttext;
    //firebase
    StorageReference ref,strg;
    DatabaseReference dbref;
    ProgressDialog progressDialog;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseStorage firebaseStorage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth= FirebaseAuth.getInstance();
        Logouttext= (TextView)findViewById(R.id.textView4);
        Logouttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(Dashboard.this,MainActivity.class));

            }
        });
        progressDialog=new ProgressDialog(this);
        //firebase
        strg= FirebaseStorage.getInstance().getReference();
        dbref= FirebaseDatabase.getInstance().getReference(" IMAGE");

        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"),PICK_IMAGE_REQUEST);

            }
        });
        upload=findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

    }
    private void uploadImage(){

        if(filepath!=null){
            progressDialog.show();
            ref=FirebaseStorage.getInstance().getReference().child("Pic");
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override

                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Image Uploaded
                    //GetDownloadUrl
                    strg.child("Pic").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            //get downloadUrl
                            downloadUrl=task.getResult().toString();
                            dbref.push().child("imageurl").setValue(downloadUrl);
                            progressDialog.dismiss();
                            Toast.makeText(Dashboard.this,"Image uploaded",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //on upload fail
                    progressDialog.dismiss();
                    Toast.makeText(Dashboard.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    //On upload progress
                    double pr=(100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading "+(int)pr + "%");
                }
            });
        }else
            Toast.makeText(Dashboard.this,"Image not found",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filepath=data.getData();
            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(Dashboard.this.getContentResolver(),filepath);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
