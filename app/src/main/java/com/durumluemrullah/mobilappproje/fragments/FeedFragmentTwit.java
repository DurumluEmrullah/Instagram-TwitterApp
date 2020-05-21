package com.durumluemrullah.mobilappproje.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.Classes.Twit;
import com.durumluemrullah.mobilappproje.Classes.User;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedPost;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedTwit;
import com.durumluemrullah.mobilappproje.aktivities.FeedActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class FeedFragmentTwit extends Fragment {
    EditText createTwit ;
    Button btShare;
    String userName;
    String profilePhototoUrl;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    RecyclerViewFeedTwit recyclerViewFeedTwit;
    ArrayList<Twit> twits;

    String userEmail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_twit,container,false);
        firebaseStorage =FirebaseStorage.getInstance();
        storageReference =firebaseStorage.getReference();
        firebaseFirestore =FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        createTwit = view.findViewById(R.id.newTwit);
        btShare = view.findViewById(R.id.btShare);
        twits = new ArrayList<>();


        getDataFromDataBase();

        recyclerView = view.findViewById(R.id.rvFeedTwit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewFeedTwit = new RecyclerViewFeedTwit(twits,getContext());
        recyclerView.setAdapter(recyclerViewFeedTwit);

        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String userId = firebaseUser.getUid();
                userEmail = firebaseUser.getEmail();

                CollectionReference collectionReference =firebaseFirestore.collection("Users");

                collectionReference.whereEqualTo("userEmail", userEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null){

                            Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                        }

                        if(queryDocumentSnapshots !=null){

                            for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                                Map<String,Object> data = snapshot.getData();

                                userName=(String) data.get("userName");
                                profilePhototoUrl =(String) data.get("profilePhoto");

                                String newTwit = createTwit.getText().toString().trim();
                                ArrayList<String> comment =new ArrayList<>();
                                ArrayList<String> likes = new ArrayList<>();
                                FieldValue date = FieldValue.serverTimestamp();
                                String twitId = ""+newTwit.hashCode()+date;

                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                String userId = firebaseUser.getUid();

                                final Twit twit = new Twit(userId,userName,userEmail,newTwit,twitId,profilePhototoUrl,date);
                                firebaseFirestore.collection("Twit").add(twit).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        createTwit.setText("");
                                        Toast.makeText(getActivity().getApplicationContext(),"Shared twit",Toast.LENGTH_SHORT).show();
                                        twits.clear();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    }
                });





            }
        });



        return view;
    }

    public void getDataFromDataBase(){

        CollectionReference collectionReference =firebaseFirestore.collection("Twit");

        collectionReference.orderBy("shareTime", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map <String,Object> data = snapshot.getData();

                        String userName =(String) data.get("userName");
                        String userEmail = (String) data.get("userEmail");
                        String profilePhototoUrl =(String)data.get("profilePhoto");
                        String twit =(String)data.get("twit");
                        String twitId = (String)data.get("twitId");
                        String userId =(String) data.get("userId");
                        System.out.println("Profile Photo : "+profilePhototoUrl);

                        System.out.println(userName);
                        Twit objTwit=new Twit(userId,userName,userEmail,twit,twitId,profilePhototoUrl);
                        twits.add(objTwit);

                        recyclerViewFeedTwit.notifyDataSetChanged();



                    }
                }
            }
        });

    }
}
