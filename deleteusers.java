package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class deleteusers extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private List<Users> usersList;
    private FirebaseFirestore mFirestore;
    private UseradminRecyclerAdapter userRecyclerAdapter;
    String docid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteusers);
        Intent i=getIntent();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
       recyclerView = (RecyclerView) findViewById(R.id.users_list1);
        userRecyclerAdapter = new UseradminRecyclerAdapter(this, usersList);





        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userRecyclerAdapter);



    }


    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot usersnapshot : dataSnapshot.getChildren()) {
                    Users user = usersnapshot.getValue(Users.class);
                    if (user.getRate() <= 5) {
                        usersList.add(user);
                        userRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
