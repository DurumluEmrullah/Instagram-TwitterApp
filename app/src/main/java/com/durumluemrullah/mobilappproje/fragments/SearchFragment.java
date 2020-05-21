package com.durumluemrullah.mobilappproje.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedTwit;
import com.durumluemrullah.mobilappproje.adapters.SearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class SearchFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    EditText searchText;
    ImageButton searchButton;
    ArrayList<String> userEmail;
    ArrayList<String> profilePhotos;
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        searchText = view.findViewById(R.id.searchText);
        searchButton = view.findViewById(R.id.searchButton);
        firebaseFirestore = FirebaseFirestore.getInstance();
        userEmail = new ArrayList<>();
        profilePhotos= new ArrayList<>();
        recyclerView = view.findViewById(R.id.rvSearch);



        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        searchAdapter = new SearchAdapter(profilePhotos,userEmail,getContext());
        recyclerView.setAdapter(searchAdapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searching= searchText.getText().toString().trim();

                CollectionReference collectionReference = firebaseFirestore.collection("Users");

                collectionReference.whereEqualTo("name",searching).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e!= null){

                            Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                        }

                        if(queryDocumentSnapshots != null){
                            for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){

                                Map<String,Object> data = snapshot.getData();

                                String name= (String) data.get("name");
                                String lastname=(String)data.get("lastName");
                                String eMail=(String) data.get("userEmail");
                                String profilePhoto =(String)data.get("profilePhoto");

                                userEmail.add(eMail);
                                profilePhotos.add(profilePhoto);

                                System.out.println("MERHAbaaAAC");

                                searchAdapter.notifyDataSetChanged();;


                            }



                        }
                    }
                });


            }
        });




        return view;
    }
}
