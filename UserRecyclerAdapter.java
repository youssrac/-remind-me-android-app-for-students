package com.example.pavilion.remind2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 18/04/2018.
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {
    private List<Users> usersList;
    public UserRecyclerAdapter(List<Users> usersList){
        this.usersList=usersList;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nametext.setText(usersList.get(position).getName());
        holder.sirnametext.setText(usersList.get(position).getSirname());
        holder.rateb.setRating((float) usersList.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mview;
        private TextView nametext,sirnametext;
        RatingBar rateb;

        public ViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
            nametext=(TextView)mview.findViewById(R.id.usernametext);
            sirnametext=(TextView)mview.findViewById(R.id.usersirnametext);
            rateb=(RatingBar)mview.findViewById(R.id.rateb);
        }
    }
}
