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

import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.aktivities.AddPhotoActivity;
import com.durumluemrullah.mobilappproje.aktivities.CommentActivity;
import com.durumluemrullah.mobilappproje.aktivities.OtherPeopleProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewFeedPost extends RecyclerView.Adapter<RecyclerViewFeedPost.PostHolder> {

    private ArrayList<Post> newPost;
    private Context context;
    public RecyclerViewFeedPost(ArrayList<Post> newPost,Context context) {
        this.newPost = newPost;
        this.context=context;
    }



    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_view_feed_post,parent,false);
        return new PostHolder(view);
    }

    @Override
    public int getItemCount() {
        return newPost.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, final int position) {

        holder.myComment.setText(newPost.get(position).getExplanation());
        holder.comment.setText("View all  comments");
        holder.likes.setText(" likes");
        holder.username.setText(newPost.get(position).getUserEmail());
        Picasso.get().load(newPost.get(position).getPostUri()).into(holder.post);
        Picasso.get().load(newPost.get(position).getProfilePhotoUri()).resize(55,55).into(holder.profilePhoto);

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherPeopleProfile.class);
                intent.putExtra("user",newPost.get(position).getUserEmail());
                context.startActivity(intent);
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("posdId",newPost.get(position).getPostUri());
                context.startActivity(intent);
            }
        });

        holder.ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("posdId",newPost.get(position).getPostUri());
                context.startActivity(intent);
            }
        });

        holder.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherPeopleProfile.class);
                intent.putExtra("user",newPost.get(position).getUserEmail());
                context.startActivity(intent);

            }
        });


    }

    class PostHolder extends RecyclerView.ViewHolder{
        ImageView profilePhoto,post;
        TextView username,likes,myComment,comment;
        ImageButton like,ibComment;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            profilePhoto =itemView.findViewById(R.id.ivRvFeedPostProfilePhoto);
            post=itemView.findViewById(R.id.ivRvFeedPostPost);
            username=itemView.findViewById(R.id.tvRvFeedPostUsername);
            likes=itemView.findViewById(R.id.tvRvFeedPostLikes);
            myComment=itemView.findViewById(R.id.tvRvFeedPostMyComment);
            like=itemView.findViewById(R.id.ibRvFeedPostLike);
            ibComment=itemView.findViewById(R.id.ibRvFeedPostComment);
            comment = itemView.findViewById(R.id.tvRvFeedPostComments);


        }
    }

}
