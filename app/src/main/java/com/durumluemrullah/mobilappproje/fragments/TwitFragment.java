package com.durumluemrullah.mobilappproje.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.Classes.FireBase;
import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.Classes.Twit;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedPost;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedTwit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class TwitFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Twit> twits ;
    RecyclerViewFeedTwit recyclerViewFeedTwit;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_twit,container,false);
        recyclerView = view.findViewById(R.id.rvAccountTwit);
        firebaseFirestore = FirebaseFirestore.getInstance();
        twits = new ArrayList<>();



        getTwit();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewFeedTwit = new RecyclerViewFeedTwit (twits,getContext());
        recyclerView.setAdapter(recyclerViewFeedTwit);



        return view;
    }

    public void getTwit(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userEmail = firebaseUser.getEmail();


        CollectionReference collectionReference =firebaseFirestore.collection("Twit");

        collectionReference.whereEqualTo("userEmail",userEmail).orderBy("shareTime", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map <String,Object> data = snapshot.getData();

                        String userName =(String) data.get("userName");
                        String profilePhototoUrl =(String)data.get("profilePhototo");
                        String twit =(String)data.get("twit");
                        String twitId = (String) data.get("twitId");
                        String userId =(String) data.get("userId");
                        FieldValue shareTime = FieldValue.serverTimestamp();

                        System.out.println(userName);
                        Twit objTwit=new Twit(userId,userName,userEmail,twit,twitId,profilePhototoUrl,shareTime);
                        twits.add(objTwit);

                        recyclerViewFeedTwit.notifyDataSetChanged();





                    }
                }
            }
        });

    }


   /* public void getDataFromDataBase() {

        CollectionReference collectionReference = firebaseFirestore.collection("POSTSDETAIL");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getActivity().getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                if (queryDocumentSnapshots != null) {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();

                        String userEmail = (String) data.get("userEmail");
                        String profilePhotoUri = (String) data.get("profilePhotoUri");
                        String postUri = (String) data.get("postUri");
                        String explanation = (String) data.get("explanation");


                        Post post = new Post(userEmail, profilePhotoUri, postUri, explanation);

                        posts.add(post);

                        recyclerViewFeedPost.notifyDataSetChanged();
                    }
                }
            }
        });

    }*/
}
