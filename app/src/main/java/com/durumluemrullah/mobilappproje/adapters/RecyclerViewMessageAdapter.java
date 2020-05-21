package com.durumluemrullah.mobilappproje.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.Classes.MessageLobi;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.aktivities.MessagePageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerViewMessageAdapter extends RecyclerView.Adapter<RecyclerViewMessageAdapter.PostHolder> {

    private ArrayList<MessageLobi> messages;
    private Context context;

    public RecyclerViewMessageAdapter(ArrayList<MessageLobi> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.receylerview_message,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        holder.tvMail.setText(messages.get(position).getSenderEmail());
        Picasso.get().load(messages.get(position).getSenderProfilePhoto()).into(holder.profilePhoto);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PostHolder extends RecyclerView.ViewHolder{
        TextView tvMail;
        ImageView profilePhoto;

         public PostHolder(@NonNull View itemView) {
            super(itemView);
            tvMail=itemView.findViewById(R.id.tvMessageMail);
            profilePhoto=itemView.findViewById(R.id.tvMessagepp);
        }
    }





}