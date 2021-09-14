package com.example.pavilion.remind2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class displaymainnotif extends AppCompatActivity {
    private TextView num,subject,module,description;
    private Button validate;
    private DatabaseReference reference,nstat,urate;
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
        setContentView(R.layout.activity_displaymainnotif);
        num=(TextView)findViewById(R.id.num);
        subject=(TextView)findViewById(R.id.subject);
        module=(TextView)findViewById(R.id.module);
        description=(TextView)findViewById(R.id.description);
        num.setText(getIntent().getStringExtra("notification_number"));
        subject.setText(getIntent().getStringExtra("notification_subject"));
        module.setText(getIntent().getStringExtra("notification_module"));
        description.setText(getIntent().getStringExtra("notification_description"));


    }
}
