package com.durumluemrullah.mobilappproje.Classes;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedPost;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FireBase {
    FirebaseFirestore firebaseFirestore;

    public ArrayList<Post> getPostfromFireBaseEmail(String Email, final Context context, final RecyclerViewFeedPost recyclerViewFeedPost){
        final ArrayList<Post> posts = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();


        CollectionReference collectionReference = firebaseFirestore.collection("POSTSDETAIL");

        collectionReference.whereEqualTo("userEmail",Email).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Toast.makeText(context,e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
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

        return posts;
    }
}
