package com.example.pavilion.remind2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PAVILION on 19/05/2018.
 */

public class UseradminRecyclerAdapter extends RecyclerView.Adapter<UseradminRecyclerAdapter.ViewHolder> {
    private List<Users> usersList;
    private Context context;
    String id;
    String docid,idd;
    private FirebaseFirestore mFirestore;
    String mail;
    public UseradminRecyclerAdapter(Context context, List<Users> usersList){
        this.usersList=usersList;
        this.context = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleuser,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.names.setText(usersList.get(position).getName());
        holder.phon.setText(usersList.get(position).getPhone());
        mFirestore = FirebaseFirestore.getInstance();
        holder.gr.setText(usersList.get(position).getGroup());
        holder.n.setText(usersList.get(position).getIdd());
        holder.surname.setText(usersList.get(position).getSirname());
        holder.rateb.setRating((float) usersList.get(position).getRate());
        id=holder.n.getText().toString();
        final DatabaseReference data= FirebaseDatabase.getInstance().getReference("student").child(id);
        holder.f1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i("n   ",id);


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:




                                data.removeValue();


                                /////////////////////////////////

                                mFirestore.collection("student").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                            //Toast.makeText(getApplicationContext(), "" + doc.getDocument().get("email"), Toast.LENGTH_SHORT).show();

                                            if (id.equals(doc.getDocument().get("id"))) {

                                                docid = doc.getDocument().getId();




                                                mFirestore.collection("student").document(docid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
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
                                });
                               //////////////////////////////////////////////
                                break;

                              case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to remove this student?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mview;
        private TextView names,phon,surname,gr,n;
        RatingBar rateb;
        RelativeLayout f1;

        public ViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
            names=(TextView)mview.findViewById(R.id.username);
            phon=(TextView)mview.findViewById(R.id.phone11);
            gr=(TextView)mview.findViewById(R.id.group11);
           surname=(TextView)mview.findViewById(R.id.username4);
           n=(TextView)mview.findViewById(R.id.n);

            f1 = (RelativeLayout) mview.findViewById(R.id.itemn1);



            rateb=(RatingBar)mview.findViewById(R.id.rating);
        }
    }
}

