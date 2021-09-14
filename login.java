package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity  {
FirebaseAuth auth;
ProgressBar progressBar;
EditText username,pass,pass2;
    FirebaseUser user;
    FirebaseFirestore fire;
    String sid;
    String token;
    String current;
    String docid;
   String password;
    String passwordadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent=getIntent();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        auth=FirebaseAuth.getInstance();
        sid = "FMROfZpN1bZtzcWmXrmR";
        fire = FirebaseFirestore.getInstance();
        username = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        user = FirebaseAuth.getInstance().getCurrentUser();

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

    }


    public void login(View view) {
        String email = username.getText().toString().trim();
        password = pass.getText().toString().trim();

        if (email.isEmpty()) {
            username.setError("Email is required");
            username.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            username.setError("Please enter a valid email");
            username.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              //  progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("student").child(auth.getCurrentUser().getUid());
                    final DatabaseReference reference1= FirebaseDatabase.getInstance().getReference().child("teacher").child(auth.getCurrentUser().getUid());
                    token = FirebaseInstanceId.getInstance().getToken();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()==false)
                            {
                                ////////////////////
                             reference1.addValueEventListener(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                     if(dataSnapshot.exists()==false){

                                         auth.signOut();
                                         Intent intent = new Intent(login.this, register.class);

                                         finish();
                                         startActivity(intent);

                                     }
                                 }

                                 @Override
                                 public void onCancelled(DatabaseError databaseError) {

                                 }
                             })   ;


                               //////////////////////////////////////
                             }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                    current = auth.getCurrentUser().getEmail();

                    if (current.equals("example@gmail.com")) {


                        Map<String, Object> tokenMap = new HashMap<>();
                        tokenMap.put("tokenid", token);
                        fire.collection("student").document(sid).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Intent intent = new Intent(login.this, adminconsole.class);
                               // intent.putExtra("adminpass",password);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });

                      //  Toast.makeText(login.this,""+user,Toast.LENGTH_LONG).show();
                    } else {

    reference1.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()==false){
docid = auth.getCurrentUser().getUid();


                    /////////////////
                    Map<String, Object> tokenMap = new HashMap<>();
                    tokenMap.put("tokenid", token);
                    fire.collection("student").document(docid).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Intent intent = new Intent(login.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    });

                }


        else{

            Intent intent = new Intent(login.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});




/////////////////////////////////
                    }




                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public void resetpassword(View view) {
        Intent in=new Intent(login.this,resetpassword.class);

        startActivity(in);
    }
}
