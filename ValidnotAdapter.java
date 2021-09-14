package com.example.pavilion.remind2;

/**
 * Created by asus on 18/04/2018.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ValidnotAdapter extends RecyclerView.Adapter<ValidnotAdapter.mViewHolder> {
    private List<notification> validList;
    public ValidnotAdapter(List<notification>validList){
        this.validList=validList;
    }
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_element,parent,false);
        return new mViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        holder.titre.setText(validList.get(position).getTitre());
        holder.module.setText(validList.get(position).getModule());
       // holder.description.setText(validList.get(position).getDescription());
        holder.numero.setText(validList.get(position).getNum()+"");
        final String d=validList.get(position).getDescription();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.linearLayout.getContext(),displaymainnotif.class);
                final String n=validList.get(position).getNum()+"";
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
        return validList.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder{

        View Vview;
        private TextView titre,module,description,numero;
        private RelativeLayout linearLayout;
        public mViewHolder(View itemView) {
            super(itemView);
            Vview=itemView;
            titre=(TextView)Vview.findViewById(R.id.nottitle);
            module=(TextView)Vview.findViewById(R.id.nmodule);
            //description=(TextView)Vview.findViewById(R.id.ndescription);
            numero=(TextView)Vview.findViewById(R.id.notnum);
            linearLayout=(RelativeLayout) Vview.findViewById(R.id.nitem);


        }
    }
}

