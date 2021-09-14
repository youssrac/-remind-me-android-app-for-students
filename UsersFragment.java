package com.example.pavilion.remind2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 18/04/2018.
 */

public class UsersFragment extends Fragment {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private List<Users> usersList;
    private UserRecyclerAdapter userRecyclerAdapter;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.muserlistview);
        usersList=new ArrayList<>();
        userRecyclerAdapter=new UserRecyclerAdapter(usersList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(userRecyclerAdapter);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("student");
        // Inflate the layout for this fragment
        return view;
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

