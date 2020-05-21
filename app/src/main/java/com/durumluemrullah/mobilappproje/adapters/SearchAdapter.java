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

import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.aktivities.OtherPeopleProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.PostHolder> {

    private ArrayList<String> profile;
    private ArrayList<String> email;
    private Context context;


    public SearchAdapter(ArrayList<String> profile, ArrayList<String> email, Context context) {
        this.profile = profile;
        this.email = email;
        this.context = context;
    }

    class PostHolder extends RecyclerView.ViewHolder{
        ImageView profilePhoto;
        TextView userEmail;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            profilePhoto = itemView.findViewById(R.id.searchpp);
            userEmail =itemView.findViewById(R.id.searchEmail);


        }
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.search_adapter,parent,false);

        return new PostHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {

        holder.userEmail.setText(email.get(position));
        Picasso.get().load(profile.get(position)).into(holder.profilePhoto);


        holder.userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherPeopleProfile.class);
                intent.putExtra("user",email.get(position));
                context.startActivity(intent);
            }
        });


        holder.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherPeopleProfile.class);
                intent.putExtra("user",email.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profile.size();
    }
}
