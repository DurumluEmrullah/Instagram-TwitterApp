package com.durumluemrullah.mobilappproje.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Stack;

public class RecylerViewMyPostHolder extends RecyclerView.Adapter<RecylerViewMyPostHolder.MyPostHolder> {

    private ArrayList<String> posts;


    public RecylerViewMyPostHolder(ArrayList<String> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.recycler_view_my_share_post,parent,false);
        return new MyPostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostHolder holder, int position) {


            Picasso.get().load(posts.get(position)).resize(100,100).into(holder.post1);

            Picasso.get().load(posts.get(++position)).resize(100,100).into(holder.post2);

            Picasso.get().load(posts.get(++position)).resize(100,100).into(holder.post3);

        System.out.println("sayi :"+((posts.size()+(posts.size()%3))/3));
    }

    @Override
    public int getItemCount() {
        int postSize = posts.size();
        return ((int)Math.ceil(postSize/3));
    }

    class MyPostHolder extends RecyclerView.ViewHolder{
        ImageView post1,post2,post3;
        public MyPostHolder(@NonNull View itemView) {
            super(itemView);

            post1=itemView.findViewById(R.id.ivPost1);
            post2=itemView.findViewById(R.id.ivPost2);
            post3= itemView.findViewById(R.id.ivPost3);

        }
    }
}
