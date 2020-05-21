package com.durumluemrullah.mobilappproje.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.Classes.User;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.aktivities.GeneralActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class AccountFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    TextView tvPost,tvTwit,tvFollower,tvFollowing,nameAndSurname,biography;
    ImageView profilePhoto;
    PostFragment postFragment;
    TwitFragment twitFragment;
    Button editProfile;
    User user;
    ArrayList<String> posts ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        firebaseFirestore =FirebaseFirestore.getInstance();
        tvPost = view.findViewById(R.id.tvGoPost);
        tvTwit=view.findViewById(R.id.tvGoTwit);
        editProfile=view.findViewById(R.id.btEditProfile);
        nameAndSurname = view.findViewById(R.id.nameAndSurname);
        biography=view.findViewById(R.id.biography);
        profilePhoto = view.findViewById(R.id.profilePhoto);
        postFragment=new PostFragment();
        twitFragment= new TwitFragment();
        posts =new ArrayList<>();



        tvTwit.setTextSize(15);
        tvPost.setTextSize(30);
        tvPost.setTextColor(Color.parseColor("#ffffff"));
        tvPost.setBackgroundColor(Color.parseColor("#4CAF50"));
        tvTwit.setTextColor(Color.parseColor("#000000"));
        tvTwit.setBackgroundColor(Color.parseColor("#ffffff"));

        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String id = firebaseUser.getEmail();






        CollectionReference collectionReference =firebaseFirestore.collection("Users");

        collectionReference.whereEqualTo("userEmail", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map<String,Object> data = snapshot.getData();

                        String userName =(String) data.get("userName");
                        String name =(String) data.get("name");
                        String profilePhototoUrl =(String) data.get("profilePhoto");
                        String biography1 =(String) data.get("biography");
                        String lastName =(String) data.get("lastName");


                        String email = firebaseUser.getEmail();
                        System.out.println(profilePhototoUrl);


                        nameAndSurname.setText( name +" "+ lastName);
                        biography.setText(biography1);
                        Picasso.get().load(profilePhototoUrl).into(profilePhoto);

                    }
                }
            }
        });





        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,postFragment).commit();

        tvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvTwit.setTextSize(15);
                tvPost.setTextSize(30);
                tvPost.setTextColor(Color.parseColor("#ffffff"));
                tvPost.setBackgroundColor(Color.parseColor("#4CAF50"));
                tvTwit.setTextColor(Color.parseColor("#000000"));
                tvTwit.setBackgroundColor(Color.parseColor("#ffffff"));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,postFragment).commit();
            }
        });
        tvTwit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvPost.setTextSize(15);
                tvTwit.setTextSize(30);
                tvTwit.setTextColor(Color.parseColor("#ffffff"));
                tvTwit.setBackgroundColor(Color.parseColor("#4CAF50"));
                tvPost.setTextColor(Color.parseColor("#000000"));
                tvPost.setBackgroundColor(Color.parseColor("#ffffff"));


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,twitFragment).commit();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), GeneralActivity.class);
                intent.putExtra("purpose","editProfile");
                startActivity(intent);
            }
        });
        return view;
    }



}
