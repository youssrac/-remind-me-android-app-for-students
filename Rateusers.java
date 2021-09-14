package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Rateusers extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView listViewStudents;
    private List<Users> list;
    private FirebaseAuth auth;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateusers);

        listViewStudents=(ListView)findViewById(R.id.usrl);
        databaseReference= FirebaseDatabase.getInstance().getReference("student");
        list=new ArrayList<>();
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(Rateusers.this,login.class));
        }




    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot usersnapshot :dataSnapshot.getChildren())
                {Users user1=usersnapshot.getValue(Users.class);
                if(user1.getRate()<=5){
                    list.add(user1);
                    UsersAdapter adapter=new UsersAdapter(Rateusers.this,list);

                    listViewStudents.setAdapter(adapter);}


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
