package com.example.pavilion.remind2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class validatestudent extends AppCompatActivity {
    private TextView phone, email, fname, password,group,surnam,coden;
private String adpass;


    DatabaseReference databasestudent;
    FirebaseFirestore mfire;
    String updateme = "example@gmail.com";
    String passmail = "example";
    double rating=0;
    String madmin;
    String passadmin;
    String fullname, mail, phon, pass,groupe,docid,surname,code;

    String d;
    Session session;
    String  sid="FMROfZpN1bZtzcWmXrmR";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validatestudent);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.user);
        fname = (TextView) findViewById(R.id.fname);
        coden = (TextView) findViewById(R.id.code2);
        group = (TextView) findViewById(R.id.group2);
       surnam = (TextView) findViewById(R.id.surname2);
        password = (TextView) findViewById(R.id.password);
        phon = getIntent().getStringExtra("phone");
        pass = getIntent().getStringExtra("password");
        mail = getIntent().getStringExtra("email");
        fullname = getIntent().getStringExtra("name");
        surname = getIntent().getStringExtra("surname");
        code = getIntent().getStringExtra("code");
        groupe = getIntent().getStringExtra("group");
     docid = getIntent().getStringExtra("docid");

        phone.setText(phon);
        email.setText(mail);
        adpass="0020219970";
        fname.setText(fullname);
        coden.setText(code);
        surnam.setText(surname);
        password.setText(pass);
        group.setText(groupe);
        mAuth = FirebaseAuth.getInstance();
        mfire=FirebaseFirestore.getInstance();
        databasestudent = FirebaseDatabase.getInstance().getReference("student");

      //  Toast.makeText(validatestudent.this,"docid  "+docid,Toast.LENGTH_SHORT).show();





    }


    public void add(View view) {
        madmin=mAuth.getCurrentUser().getEmail();

        mAuth.signOut();
        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    final String id=mAuth.getCurrentUser().getUid();

                    Users student=new Users(fullname,surname,phon,code,groupe,id,rating);
                    databasestudent.child(id).setValue(student);
                    mAuth.signOut();

                    mAuth.signInWithEmailAndPassword(madmin,adpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Map<String, Object> usermap = new HashMap<>();

                                usermap.put("name", fullname);
                                usermap.put("surname", surname);
                                usermap.put("email", mail);
                                usermap.put("id", id);

                                mfire.collection("student").document(id).set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(validatestudent.this, "student added", Toast.LENGTH_SHORT).show();


                                        String message = "Welcome To Update ME,Thank you for signing up " +
                                                "\n Your Username: " + mail +
                                                "\n Password: " + pass +
                                                "\n Best Regards" +
                                                "\n UpdateMe Team,";


                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                        Properties props = new Properties();
                                        props.put("mail.smtp.host", "smtp.gmail.com");
                                        props.put("mail.smtp.socketFactory.port", "465");
                                        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                                        props.put("mail.smtp.auth", "true");
                                        props.put("mail.smtp.port", "465");
                                        try {
                                            session = Session.getDefaultInstance(props, new Authenticator() {
                                                @Override
                                                protected PasswordAuthentication getPasswordAuthentication() {
                                                    return new PasswordAuthentication(updateme, passmail);

                                                }
                                            });
                                            if (session != null) {
                                                MimeMessage mm = new MimeMessage(session);

                                                //Setting sender address
                                                mm.setFrom(new InternetAddress(updateme));
                                                //Adding receiver
                                                mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
                                                //Adding subject
                                                mm.setSubject("Welcome to Update Me!");
                                                //Adding message
                                                mm.setText(message);
                                                Transport.send(mm);
                                            }
                                            Toast.makeText(validatestudent.this, "Student added and Email sent", Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(validatestudent.this, "id "+docid, Toast.LENGTH_SHORT).show();

                                            mfire.collection("student").document(sid).collection("notifications").document(docid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    startActivity(new Intent(validatestudent.this, displayrequests.class));
                                                    Log.i("", "request successfully deleted!");


                                                }
                                            })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.i("", "fail deleting request", e);
                                                        }
                                                    });


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            mfire.collection("student").document(sid).collection("notifications").document(docid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    startActivity(new Intent(validatestudent.this, displayrequests.class));
                                                    Log.i("", "request successfully deleted!");

                                                }
                                            })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                        //    Toast.makeText(validatestudent.this, "request fail ", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                          //  Toast.makeText(validatestudent.this, "id "+docid, Toast.LENGTH_SHORT).show();
                                            Toast.makeText(validatestudent.this, "Failed to send Email", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(validatestudent.this, "Failed to add student", Toast.LENGTH_LONG).show();

                                    }
                                });


                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }



                        }
                    });


                }else {
                    mAuth.signInWithEmailAndPassword(madmin,adpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                        }
                    });



                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "this student is already registered", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(validatestudent.this, "id "+docid, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }


        });
    }

}

