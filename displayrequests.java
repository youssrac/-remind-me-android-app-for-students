package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class displayrequests extends AppCompatActivity implements RecyclerItemTouchHelperListener  {
    private RecyclerView mNotificationList;
    private MyItemRecyclerViewAdapter notificationsAdapter;
    String docid;
    public static String password;

    int deletedindex;
    private List<Notifications> mNotifList;
    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayrequests);

        Intent i=getIntent();
        //password = getIntent().getStringExtra("adminpass");

        mNotifList = new ArrayList<>();
        mNotificationList = (RecyclerView) findViewById(R.id.notification_list);
        notificationsAdapter = new MyItemRecyclerViewAdapter(this, mNotifList);



        mNotificationList.setHasFixedSize(true);
        mNotificationList.setLayoutManager(new LinearLayoutManager(this));
        mNotificationList.setAdapter(notificationsAdapter);
        mFirestore = FirebaseFirestore.getInstance();
        ItemTouchHelper.SimpleCallback item
                = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(item).attachToRecyclerView(mNotificationList);



        String  sid="FMROfZpN1bZtzcWmXrmR";
        //  Toast.makeText(container.getContext(), "User_ID : " + sid , Toast.LENGTH_LONG).show();
        mFirestore.collection("student").document(sid).collection("notifications").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for(DocumentChange doc: documentSnapshots.getDocumentChanges()) {
                    //docid=doc.getDocument().getId();
                    docid=doc.getDocument().getId();
                    Notifications notificatio = doc.getDocument().toObject(Notifications.class).withId(docid);
                    mNotifList.add(notificatio);


                    notificationsAdapter.notifyDataSetChanged();
                    Log.i("two","one");

                }

            }

        });


    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof MyItemRecyclerViewAdapter.ViewHolder){
            //String name=mNotifList.get(viewHolder.getAdapterPosition()).getRequest();
            Notifications deleteditem=mNotifList.get(viewHolder.getAdapterPosition());
            deletedindex=viewHolder.getAdapterPosition();
            //Toast.makeText(notificationdisplayactivity.this, "position :  "+position, Toast.LENGTH_LONG).show();
//notificationsAdapter.removeItem(deletedindex);
            docid=mNotifList.get(position).notifid;

            //Toast.makeText(notificationdisplayactivity.this, "ID :  "+docid, Toast.LENGTH_LONG).show();
            String  sid="FMROfZpN1bZtzcWmXrmR";

            mFirestore.collection("student").document(sid).collection("notifications").document(docid

            )
                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    notificationsAdapter.removeItem(deletedindex);
                    Log.i("", "request successfully deleted!");

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("", "fail deleting request", e);
                        }
                    });

        }


    }
}
