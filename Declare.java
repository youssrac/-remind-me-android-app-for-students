package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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


public class Declare extends AppCompatActivity {


    private EditText titre,module,description;
    private Button submitt,logout;
    private DatabaseReference databaseReference,order,rat,val,reference,ref,rat2;
    private String idd,current;
    private double r;
    private FirebaseAuth firebaseAuth;


    private FirebaseFirestore db;
    int odd;
    String docid;
    double n;
String title,desc,mod;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        firebaseAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        reference=FirebaseDatabase.getInstance().getReference().child("student").child(firebaseAuth.getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    user=firebaseAuth.getCurrentUser();
                    if(user!=null)   {idd=user.getUid();

                        rat= FirebaseDatabase.getInstance().getReference().child("student").child(idd);

                        //add valueevent listener to rat to get the current userrating in the variable r and set it as default stats for the notificatoon
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("notification");
                        order=FirebaseDatabase.getInstance().getReference().child("order");
                        val=FirebaseDatabase.getInstance().getReference().child("Validations");

                        rat.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                r=dataSnapshot.child("rate").getValue(double.class);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        order.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                odd=dataSnapshot.getValue(int.class);


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        titre=(EditText)findViewById(R.id.titre);
                        module=(EditText)findViewById(R.id.module);
                        description=(EditText)findViewById(R.id.description);
                        submitt=(Button)findViewById(R.id.submitt);

                        submitt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                title=titre.getText().toString();
                                mod=module.getText().toString();
                                desc=description.getText().toString();
                                databaseReference.child(odd+"").child("stats").setValue(r);
                                databaseReference.child(odd+"").child("titre").setValue(titre.getText().toString());
                                databaseReference.child(odd+"").child("module").setValue(module.getText().toString());
                                databaseReference.child(odd+"").child("description").setValue(description.getText().toString());
                                databaseReference.child(odd+"").child("num").setValue(odd);
                                val.child(odd+"").child(user.getUid()).setValue("done");

                                order.setValue(odd+1);



                                n = r;

                                if (n >= 15) {
                                    current = firebaseAuth.getCurrentUser().getEmail();

                                    db.collection("student").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                //Toast.makeText(getApplicationContext(), "" + doc.getDocument().get("email"), Toast.LENGTH_SHORT).show();

                                                if (!current.equals(doc.getDocument().get("email"))) {

                                                    docid = doc.getDocument().getId();


                                                    Map<String, Object> notificationmessage = new HashMap<>();


                                                    notificationmessage.put("title", title);
                                                    notificationmessage.put("subject", mod);
                                                    notificationmessage.put("description", desc);
                                                    db.collection("student/" + docid + "/validations").add(notificationmessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(getApplicationContext(), "notification sent", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(Declare.this, MainActivity.class));
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
                            }
                        });









                }

                }
                ////////
                else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("teacher");

                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                idd = user.getUid();

                                rat = FirebaseDatabase.getInstance().getReference().child("teacher").child(idd);
                                //add valueevent listener to rat to get the current userrating in the variable r and set it as default stats for the notificatoon
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("notification");
                                order = FirebaseDatabase.getInstance().getReference().child("order");
                                val = FirebaseDatabase.getInstance().getReference().child("Validations");

                                rat.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        r = dataSnapshot.child("rate").getValue(double.class);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                order.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        odd = dataSnapshot.getValue(int.class);


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                titre = (EditText) findViewById(R.id.titre);
                                module = (EditText) findViewById(R.id.module);
                                description = (EditText) findViewById(R.id.description);
                                submitt = (Button) findViewById(R.id.submitt);
                                title=titre.getText().toString();
                                mod=module.getText().toString();
                                desc=description.getText().toString();

                                submitt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        databaseReference.child(odd + "").child("stats").setValue(r);
                                        databaseReference.child(odd + "").child("titre").setValue(titre.getText().toString());
                                        databaseReference.child(odd + "").child("module").setValue(module.getText().toString());
                                        databaseReference.child(odd + "").child("description").setValue(description.getText().toString());
                                        databaseReference.child(odd + "").child("num").setValue(odd);
                                        val.child(odd + "").child(user.getUid()).setValue("done");

                                        order.setValue(odd + 1);
                                        titre.setText("");
                                        module.setText("");
                                        description.setText("");



                                           // current = firebaseAuth.getCurrentUser().getEmail();

                                            db.collection("student").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        // Toast.makeText(getApplicationContext(), "" + doc.getDocument().get("email"), Toast.LENGTH_SHORT).show();



                                                            docid = doc.getDocument().getId();


                                                            Map<String, Object> notificationmessage = new HashMap<>();


                                                            notificationmessage.put("title", title);
                                                            notificationmessage.put("subject", mod);
                                                            notificationmessage.put("description", desc);
                                                            db.collection("student/" + docid + "/validations").add(notificationmessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    Toast.makeText(getApplicationContext(), "notification sent", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(Declare.this, MainActivity.class));
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
                                            });





                                    }
                                });

                            } else {
                                finish();
                                startActivity(new Intent(getApplicationContext(), login.class));
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
///////////////////////



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
