package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class adminconsole extends AppCompatActivity {

    FirebaseFirestore db;
    String current,docid;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminconsole);
        Intent intent=getIntent();
       // password = getIntent().getStringExtra("adminpass");
        mauth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void display(View view) {
        Intent in=new Intent(adminconsole.this,displayrequests.class);

        startActivity(in);
    }
    public void addteacher(View view) {
        Intent in=new Intent(adminconsole.this,addteacher.class);

        startActivity(in);
    }

    public void delete(View view) {
        Intent in=new Intent(adminconsole.this,deleteusers.class);

        startActivity(in);
    }

    public void home(View view) {
        Intent intent = new Intent(adminconsole.this, MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void logout(View view) {

        current = mauth.getCurrentUser().getEmail();

        db.collection("student").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for(DocumentChange doc: documentSnapshots.getDocumentChanges()) {


                    if (current.equals(doc.getDocument().get("email"))) {

                        docid = doc.getDocument().getId();


                        Map<String,Object> tokenmapremove=new HashMap<>();
                        tokenmapremove.put("tokenid", FieldValue.delete());

                        db.collection("student").document(docid).update(tokenmapremove).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mauth.signOut();
                                db.collection("student").document();
                                finish();
                                Intent in=new Intent(adminconsole.this,login.class);
                                startActivity(in);

                            }
                        });

                    }

                }

            }
        });


        // firebaseAuth1.signOut();
        //startActivity(new Intent(getApplicationContext(),login.class));
       // finish();
    }
}
