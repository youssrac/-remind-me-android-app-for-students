package com.example.pavilion.remind2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Created by PAVILION on 17/05/2018.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<Notifications> mNotifList;
    public ViewHolder holder;
    private FirebaseFirestore firebaseFirestore;
    private Context context;
String docid;
    public MyItemRecyclerViewAdapter(Context context, List<Notifications> mNotifList) {

        this.mNotifList = mNotifList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.singlenotification, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        firebaseFirestore = FirebaseFirestore.getInstance();

        String  sid="FMROfZpN1bZtzcWmXrmR";

        holder.id.setText(mNotifList.get(position).getId());
        holder.fullname.setText(mNotifList.get(position).getFullname());
        holder.email.setText(mNotifList.get(position).getEmail());
        holder.password.setText(mNotifList.get(position).getPassword());
        holder.group.setText(mNotifList.get(position).getGroup());
        holder.code.setText(mNotifList.get(position).getCode());
        holder.surname.setText(mNotifList.get(position).getSirname());
         docid=mNotifList.get(position).notifid;

        firebaseFirestore.collection("student").document(sid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.i("one","one");


            }
        });

        holder.f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.f.getContext(),validatestudent.class);
                intent.putExtra("phone",holder.id.getText().toString());
                intent.putExtra("email",holder.email.getText().toString());
                intent.putExtra("name",holder.fullname.getText().toString());
                intent.putExtra("password",holder.password.getText().toString());
                intent.putExtra("group",holder.group.getText().toString());
                intent.putExtra("surname",holder.surname.getText().toString());
                intent.putExtra("code",holder.code.getText().toString());
                intent.putExtra("docid", docid);
                holder.f.getContext().startActivity(intent);
            }
        });



    }


    public void removeItem(int position) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        mNotifList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return mNotifList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;


        public RelativeLayout foreground,background;

        public TextView id;
        public TextView passadmin;
        public TextView email;
        public TextView fullname,group,surname,code;
        public TextView password;
        private FrameLayout f;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            foreground=(RelativeLayout) mView.findViewById(R.id.foreground);
            background=(RelativeLayout) mView.findViewById(R.id.background);
            id = (TextView) mView.findViewById(R.id.notif_phone);
            email = (TextView) mView.findViewById(R.id.notif_email);
            group = (TextView) mView.findViewById(R.id.notif_group);
            fullname = (TextView) mView.findViewById(R.id.notif_fname);
            code = (TextView) mView.findViewById(R.id.notif_code);
            password = (TextView) mView.findViewById(R.id.notif_password);
           surname = (TextView) mView.findViewById(R.id.notif_surname);
           f = (FrameLayout) mView.findViewById(R.id.itemn);

        }
    }

}