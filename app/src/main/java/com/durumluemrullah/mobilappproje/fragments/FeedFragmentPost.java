package com.durumluemrullah.mobilappproje.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.Classes.FireBase;
import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedPost;
import com.google.android.gms.common.util.JsonUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import io.grpc.Context;

public class FeedFragmentPost extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> posts ;
    RecyclerViewFeedPost recyclerViewFeedPost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_post,container,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        posts = new ArrayList<>();
        getDataFromDataBase();

       // Context context = getActivity().getApplicationContext();
        RecyclerView recyclerView =view.findViewById(R.id.rvPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewFeedPost = new RecyclerViewFeedPost(posts,getContext());
        recyclerView.setAdapter(recyclerViewFeedPost);
        return view;
    }
    public void getDataFromDataBase(){

        CollectionReference collectionReference = firebaseFirestore.collection("POSTSDETAIL");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }

                if(queryDocumentSnapshots != null){

                    for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){

                        Map<String,Object> data = snapshot.getData();

                        String userEmail =(String) data.get("userEmail");
                        String profilePhotoUri =(String) data.get("profilePhotoUri");
                        String postUri =(String) data.get("postUri");
                        String explanation =(String) data.get("explanation");


                        Post post = new Post(userEmail,profilePhotoUri,postUri,explanation);

                        posts.add(post);

                        recyclerViewFeedPost.notifyDataSetChanged();
                    }
                }
            }
        });

        /*
        CollectionReference collectionReference =firebaseFirestore.collection("POSTSDETAIL");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map <String,Object> data = snapshot.getData();

                        String userEmail =(String) data.get("userEmail");
                        String profilePhotoUri =(String)data.get("profilePhotoUri");
                        String postUri =(String)data.get("postUri");
                        String explanation =(String) data.get("explanation");
                        String postId = (String) data.get("postId");

                        Post post = new Post(userEmail,profilePhotoUri,postUri,explanation);
                        posts.add(post);

                        recyclerViewFeedPost.notifyDataSetChanged();

                   }
                }
            }
        });
*/
    }


}
