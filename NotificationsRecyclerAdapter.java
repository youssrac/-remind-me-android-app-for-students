package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 18/04/2018.
 */

public class NotificationsRecyclerAdapter extends RecyclerView.Adapter<NotificationsRecyclerAdapter.Oviewholder> {

    private List<notification> notificationsList;
    public  NotificationsRecyclerAdapter(List<notification> notificationsList){

        this.notificationsList=notificationsList;

    }
    @Override
    public Oviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.othernot,parent,false);
        return new Oviewholder(v);
    }

    @Override
    public void onBindViewHolder(final Oviewholder holder, final int position) {
       // holder.number.setText(notificationsList.get(position).getNum()+"");
        holder.titre.setText(notificationsList.get(position).getTitre());
        holder.module.setText(notificationsList.get(position).getModule());
       // holder.description.setText(notificationsList.get(position).getDescription());
        final String d=notificationsList.get(position).getDescription();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.linearLayout.getContext(),Validate.class);
                final String n=notificationsList.get(position).getNum()+"";
                intent.putExtra("notification_number",n);
                intent.putExtra("notification_subject",holder.titre.getText().toString());
                intent.putExtra("notification_module",holder.module.getText().toString());
                intent.putExtra("notification_description",d);

                holder.linearLayout.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class Oviewholder extends RecyclerView.ViewHolder{
        View oview;
        private TextView titre,number,module,description;
        private RelativeLayout linearLayout;

        public Oviewholder(View itemView) {
            super(itemView);
            oview=itemView;
            titre=(TextView)oview.findViewById(R.id.nottitle);
          //  number=(TextView)oview.findViewById(R.id.notnum);
            module=(TextView)oview.findViewById(R.id.nmodule);
           // description=(TextView)oview.findViewById(R.id.ndescription);
            linearLayout=(RelativeLayout) oview.findViewById(R.id.nitem);


        }
        //initialize the variables (Textviews)
    }}

