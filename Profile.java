package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private DatabaseReference firebaseDatabase= FirebaseDatabase.getInstance().getReference();

    private FirebaseUser user;
    private FirebaseAuth mauth;
String sid;


    String current, docid,id;
    FirebaseFirestore db;
    private TextView name,code,group,phone,email,rate;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        rate=(TextView)findViewById(R.id.rr);
        sid = "FMROfZpN1bZtzcWmXrmR";
        name=(TextView)findViewById(R.id.name);
        code=(TextView)findViewById(R.id.code);
        group=(TextView)findViewById(R.id.group);
        phone=(TextView)findViewById(R.id.phone);
        email=(TextView)findViewById(R.id.email);


        mauth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        email.setText(user.getEmail().toString());

        id = mauth.getInstance().getCurrentUser().getUid();
        email.setText(user.getEmail().toString());


        databaseReference=FirebaseDatabase.getInstance().getReference().child("student").child(id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String n=dataSnapshot.child("name").getValue()+" "+dataSnapshot.child("sirname").getValue();
                    name.setText(n);
                    String c=dataSnapshot.child("code").getValue().toString();
                    code.setText(c);
                    String g=dataSnapshot.child("group").getValue().toString();
                    group.setText(g);
                    String p=dataSnapshot.child("phone").getValue().toString();
                    phone.setText(p);
                rate.setText(dataSnapshot.child("rate").getValue().toString());}
                else {
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("teacher").child(id);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String n = dataSnapshot.child("name").getValue() + " " + dataSnapshot.child("sirname").getValue();
                            name.setText(n);
                            String c = dataSnapshot.child("code").getValue().toString();
                            code.setText(c);
                            String g = dataSnapshot.child("group").getValue().toString();
                            group.setText(g);
                            String p = dataSnapshot.child("phone").getValue().toString();
                            phone.setText(p);
                            rate.setText(dataSnapshot.child("rate").getValue().toString());




                    }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });





                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }


    public void logout(View view) {

        DatabaseReference data= FirebaseDatabase.getInstance().getReference().child("teacher").child(mauth.getCurrentUser().getUid());
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {mauth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(),login.class));
                }
                else{

                    current = mauth.getCurrentUser().getEmail();


                    if (current.equals("example@gmail.com")) {


                        Map<String,Object> tokenmapremove=new HashMap<>();
                        tokenmapremove.put("tokenid", FieldValue.delete());
                        db.collection("student").document(sid).update(tokenmapremove).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                mauth.signOut();
                                db.collection("student").document();
                                finish();
                                Intent in=new Intent(Profile.this,login.class);
                                startActivity(in);
                                return;
                            }
                        });

                        //  Toast.makeText(login.this,""+user,Toast.LENGTH_LONG).show();
                    }

                    else {
                        docid = mauth.getCurrentUser().getUid();


                        Map<String, Object> tokenmapremove = new HashMap<>();
                        tokenmapremove.put("tokenid", FieldValue.delete());

                        db.collection("student").document(docid).update(tokenmapremove).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mauth.signOut();
                                db.collection("student").document();
                                finish();
                                Intent in = new Intent(Profile.this, login.class);
                                startActivity(in);
                                return;

                            }
                        });

                    }

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}