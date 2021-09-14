package com.example.pavilion.remind2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class addteacher extends AppCompatActivity {
 private FirebaseAuth mAuth;
    private TextView phone, email, fname, password,sirname,code;
    String phonet,mail,fullnamet,passt,sname,codet;
String madmin;
    DatabaseReference databaseteacher;
String   adpass="000000";

    double rating = 15;
    String d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);
        Intent intent = getIntent();
        //passadmin=getIntent().getStringExtra("adminpass");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        phone = (TextView) findViewById(R.id.phonet);
        email = (TextView) findViewById(R.id.emailt);
        sirname = (TextView) findViewById(R.id.sname);
        fname = (TextView) findViewById(R.id.fullnamet);
        code = (TextView) findViewById(R.id.teacherid);
        password = (TextView) findViewById(R.id.passt);

        mAuth = FirebaseAuth.getInstance();

        databaseteacher = FirebaseDatabase.getInstance().getReference("teacher");

    }

    public void addt(View view) {
      phonet = phone.getText().toString().trim();
        mail = email.getText().toString().trim();
        fullnamet = fname.getText().toString().trim();
        passt = password.getText().toString().trim();
        sname = sirname.getText().toString().trim();
        codet = code.getText().toString().trim();

        if (mail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }


        if (fullnamet.isEmpty()) {
            fname.setError("Name is required");
            fname.requestFocus();
            return;
        }
        if (sname.isEmpty()) {
           sirname.setError("Surname is required");
            sirname.requestFocus();
            return;
        }
        if (codet.isEmpty()) {
           code.setError("Teacher id is required");
           code.requestFocus();
            return;
        }
        if (phonet.isEmpty()) {
            phone.setError(" phone number is required");
            phone.requestFocus();
            return;
        }

        if (passt.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (passt.length() < 6) {
            password.setError("Password is required to have at least 6 characters");
            password.requestFocus();
            return;
        }





        madmin=mAuth.getCurrentUser().getEmail();

        mAuth.signOut();
        mAuth.createUserWithEmailAndPassword(mail, passt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    String id=mAuth.getCurrentUser().getUid();
                   teacherinfo teacher=new teacherinfo(fullnamet,sname,phonet,codet,id,rating);
                    databaseteacher.child(id).setValue(teacher);

                    mAuth.signInWithEmailAndPassword(madmin, adpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "teacher added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(addteacher.this, adminconsole.class));

                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });


                            } else {
                    mAuth.signInWithEmailAndPassword(madmin,adpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                        }
                    });


                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "this teacher is already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

}

