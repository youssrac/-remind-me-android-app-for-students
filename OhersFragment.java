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
 * A simple {@link Fragment} subclass.
 */
public class OhersFragment extends Fragment {
    private RecyclerView motherlist;
    private DatabaseReference databaseReference;
    private List<notification> notificationsList;
    private NotificationsRecyclerAdapter notificationsRecyclerAdapter;


    public OhersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_others, container, false);
        motherlist=(RecyclerView)view.findViewById(R.id.motherlist);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("notification");
        notificationsList=new ArrayList<>();
        notificationsRecyclerAdapter=new NotificationsRecyclerAdapter(notificationsList);
        motherlist.setHasFixedSize(true);
        motherlist.setLayoutManager(new LinearLayoutManager(container.getContext()));
        motherlist.setAdapter(notificationsRecyclerAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificationsList.clear();
                for (DataSnapshot othersnapshot :dataSnapshot.getChildren())
                {
      notification notifications=othersnapshot.getValue(notification.class);
//notificationsList.add(notifications);
                    if(notifications.getStats()<15){notificationsList.add(notifications);}
                    notificationsRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
