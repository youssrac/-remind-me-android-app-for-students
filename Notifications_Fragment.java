package com.example.pavilion.remind2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 18/04/2018.
 */

public class Notifications_Fragment extends Fragment {
    private RecyclerView validnot;
    private List<notification> validList;
    private ValidnotAdapter validnotAdapter;
    private DatabaseReference reference;
    private List<notification> inv;

    private String title, subject,description;
    private FirebaseFirestore db;
    private displaymainnotif notificationsRecyclerAdapter;
    String docid;
    String current;

    public Notifications_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notifications_, container, false);
        validnot=(RecyclerView)view.findViewById(R.id.validnot);
        validList=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference().child("notification");
        validnotAdapter=new ValidnotAdapter(validList);
        validnot.setHasFixedSize(true);
        validnot.setLayoutManager(new LinearLayoutManager(container.getContext()));
        validnot.setAdapter(validnotAdapter);
        inv=new ArrayList<>();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {int n=-1;
                validList.clear();
                for (DataSnapshot validsnapshot :dataSnapshot.getChildren())
                {
                    notification notifications=validsnapshot.getValue(notification.class);
                    if(validsnapshot.child("stats").getValue(double.class)>=15){n++;inv.add(notifications);

///////////////////////////////////////////
                    }

                }for (int i=n;i>=0;i--){validList.add(inv.get(i)); validnotAdapter.notifyDataSetChanged();}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

