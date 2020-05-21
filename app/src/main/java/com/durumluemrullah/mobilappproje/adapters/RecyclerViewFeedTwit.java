package com.durumluemrullah.mobilappproje.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.Classes.Twit;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.aktivities.CommentActivity;
import com.durumluemrullah.mobilappproje.aktivities.OtherPeopleProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewFeedTwit extends RecyclerView.Adapter<RecyclerViewFeedTwit.TwitHolder> {

    private ArrayList<Twit> newTwit;
    private Context context;

    public RecyclerViewFeedTwit(ArrayList<Twit> newTwit, Context context) {
        this.newTwit = newTwit;
        this.context=context;
    }

    @NonNull
    @Override
    public TwitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_view_feed_twit,parent,false);


        return new TwitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TwitHolder holder,final int position) {

        holder.tvUsername.setText(newTwit.get(position).getUserName());
        holder.likes.setText(" likes");
        holder.comments.setText("View all  comments");
        holder.twit.setText(newTwit.get(position).getTwit());
        Picasso.get().load(newTwit.get(position).getProfilePhoto()).into(holder.ivProfilePhoto);


        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("posdId",newTwit.get(position).getTwitId());
                context.startActivity(intent);
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("posdId",newTwit.get(position).getTwitId());
                context.startActivity(intent);
            }
        });
        holder.ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherPeopleProfile.class);
                intent.putExtra("user",newTwit.get(position).getUserEmail());
                context.startActivity(intent);
            }
        });


        holder.tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherPeopleProfile.class);
                intent.putExtra("user",newTwit.get(position).getUserEmail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newTwit.size();
    }

    class TwitHolder extends RecyclerView.ViewHolder{

        ImageView ivProfilePhoto;
        TextView tvUsername,twit,likes,comments;
        ImageButton like,comment;

        public TwitHolder(@NonNull View itemView) {
            super(itemView);

            ivProfilePhoto=itemView.findViewById(R.id.ivRvFeedTwitProfilePhoto);
            tvUsername=itemView.findViewById(R.id.tvRvFeedTwitUsername);
            twit=itemView.findViewById(R.id.tvRvFeedTwitTwit);
            likes=itemView.findViewById(R.id.tvRvFeedTwitLikes);
            comments=itemView.findViewById(R.id.tvRvFeedTwitComments);
            like=itemView.findViewById(R.id.ibRvFeedTwitLike);
            comment=itemView.findViewById(R.id.ibRvFeedTwitComment);
        }
    }
}
