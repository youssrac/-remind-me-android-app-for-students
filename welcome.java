package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class welcome extends AppCompatActivity {
    FirebaseAuth auth;
    Button b1;
    Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.button3);
        auth=FirebaseAuth.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logpage=new Intent(welcome.this,login.class);
                startActivity(logpage);
            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regpage=new Intent(welcome.this,register.class);
                startActivity(regpage);
            }

        });
        if(auth.getCurrentUser()!=null){finish();startActivity(new Intent(welcome.this,MainActivity.class));}
    }


}

