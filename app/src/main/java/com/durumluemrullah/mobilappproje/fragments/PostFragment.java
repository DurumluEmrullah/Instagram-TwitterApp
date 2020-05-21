package com.durumluemrullah.mobilappproje.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.Classes.User;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedPost;
import com.durumluemrullah.mobilappproje.adapters.RecylerViewMyPostHolder;
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

public class PostFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    RecyclerViewFeedPost recyclerViewFeedPost;
    ArrayList<Post> posts ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post,container,false);
        firebaseFirestore =FirebaseFirestore.getInstance();
        posts = new ArrayList<>();


        getPost();
        RecyclerView recyclerView = view.findViewById(R.id.rvMyPost);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewFeedPost = new RecyclerViewFeedPost(posts,getContext());
        recyclerView.setAdapter(recyclerViewFeedPost);

        return view;
    }

    public void  getPost(){
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();



        String userName =  firebaseUser.getEmail();



        CollectionReference collectionReference = firebaseFirestore.collection("POSTSDETAIL");

        collectionReference.whereEqualTo("userEmail",userName).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        collectionReference.whereEqualTo("userEmail", userName).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map<String,Object> data = snapshot.getData();

                        String postUrl =(String) data.get("postUri");

                        posts.add(postUrl);


                        recylerViewMyPostHolder.notifyDataSetChanged();

                        System.out.println(posts.size());




                    }
                }
            }
        });
*/
    }
}
