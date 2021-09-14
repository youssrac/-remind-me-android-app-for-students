package com.example.pavilion.remind2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Validate extends AppCompatActivity {
    private TextView num,subject,module,description;
    private Button validate;
    private DatabaseReference reference,nstat,urate,urate1,reference1;
    private FirebaseUser user;
    private String id;
    String current;
    FirebaseAuth auth;
    private double stat,rate;
    private String title1, subject1,description1,t;
    private FirebaseFirestore db;
double n;
    String docid;
    boolean exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        reference1=FirebaseDatabase.getInstance().getReference().child("teacher");
        num=(TextView)findViewById(R.id.num);
        subject=(TextView)findViewById(R.id.subject);
        module=(TextView)findViewById(R.id.module);
        description=(TextView)findViewById(R.id.description);
        num.setText(getIntent().getStringExtra("notification_number"));
        subject.setText(getIntent().getStringExtra("notification_subject"));
        module.setText(getIntent().getStringExtra("notification_module"));
        description.setText(getIntent().getStringExtra("notification_description"));
        nstat= FirebaseDatabase.getInstance().getReference().child("notification").child(num.getText().toString()).child("stats");
        user= FirebaseAuth.getInstance().getCurrentUser();
        id=user.getUid();
        auth=FirebaseAuth.getInstance();
        urate= FirebaseDatabase.getInstance().getReference().child("student").child(id).child("rate");
        urate1= FirebaseDatabase.getInstance().getReference().child("teacher").child(id).child("rate");

        reference= FirebaseDatabase.getInstance().getReference().child("Validations").child(num.getText().toString()).child(id);
        validate=(Button)findViewById(R.id.validatebutton);
        db = FirebaseFirestore.getInstance();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()==true){
                    exist=true;
                }
                else {exist=false;}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        nstat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stat=dataSnapshot.getValue(double.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        urate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                rate=dataSnapshot.getValue(double.class);}
                else {urate1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        rate=dataSnapshot.getValue(double.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });








        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(exist==true){

                    Toast.makeText(Validate.this,"Already validated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Validate.this, MainActivity.class));
                }
                else {
                    reference.setValue("done");
                    Toast.makeText(Validate.this,"Validation Successfull !",Toast.LENGTH_SHORT).show();
                    n=stat+rate;
                    nstat.setValue(stat+rate);


                    if(n>=15){

                        title1=subject.getText().toString();
                        description1=description.getText().toString();
                        subject1=module.getText().toString();
                      current = auth.getCurrentUser().getEmail();
                        db.collection("student").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                for(DocumentChange doc: documentSnapshots.getDocumentChanges()) {
                                    if (!current.equals(doc.getDocument().get("email"))) {


                                        docid = doc.getDocument().getId();


                                        Map<String, Object> notificationmessage = new HashMap<>();


                                        notificationmessage.put("title", title1);
                                        notificationmessage.put("subject", subject1);
                                        notificationmessage.put("description", description1);
                                        db.collection("student/" + docid + "/validations").add(notificationmessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {

                                                //pg.setVisibility(View.INVISIBLE);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("", "not sent");
                                            }
                                        });
                                    }
                                }


                            }
                        });






                    }


                    startActivity(new Intent(Validate.this, MainActivity.class));

                }


            }



        });






    }
}
