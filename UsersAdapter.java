package com.example.pavilion.remind2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by asus on 17/05/2018.
 */

public class UsersAdapter extends ArrayAdapter<Users> {
    int nbr =0;
    float sr =0;
    private Activity context;
    private List<Users> list;
    public UsersAdapter(Activity context, List<Users> list)
    {
        super(context,R.layout.listitem,list);
        this.context=context;
        this.list=list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.listitem,null,true);
        TextView tvn,tvid,tvr;
        RatingBar ratingBar;
        tvn=(TextView)listViewItem.findViewById(R.id.en);
       // tvid=(TextView)listViewItem.findViewById(R.id.idd);
        tvr=(TextView)listViewItem.findViewById(R.id.er);
        ratingBar=(RatingBar)listViewItem.findViewById(R.id.ratingBar);
        Users user1=list.get(position);
       // tvid.setText(user1.getIdd());
        tvn.setText(user1.getName()+"  "+user1.getSirname());
        tvr.setText(user1.getRate()+"");
        String cuid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("student").child(list.get(position).getIdd());
       final DatabaseReference reference1= FirebaseDatabase.getInstance().getReference().child("rates").child(list.get(position).getIdd());
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("rates").child(list.get(position).getIdd()).child(cuid);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            databaseReference.setValue(v);

                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot usnap : dataSnapshot.getChildren())
                        {
                            sr = sr +usnap.getValue(float.class);
                            nbr++;
                        }
                        if(nbr!=0){
                            reference.child("rate").setValue(sr/nbr);
                        }else{reference.child("rate").setValue(0);}
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });


return listViewItem;



    }
}
