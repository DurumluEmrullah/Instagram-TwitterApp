package com.durumluemrullah.mobilappproje.aktivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.durumluemrullah.mobilappproje.Classes.MessageLobi;
import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedPost;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewMessageAdapter;
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

public class MessageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView received,sent;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<MessageLobi> receiveds;
    ArrayList<MessageLobi> sents;
    RecyclerViewMessageAdapter rvReceived,rvSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.rvMessages);
        received= findViewById(R.id.tvReceived);
        sent = findViewById(R.id.tvSent);
        receiveds=new ArrayList<>();
        sents = new ArrayList<>();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();


        sent.setTextSize(15);
        received.setTextSize(30);
        received.setTextColor(Color.parseColor("#ffffff"));
        received.setBackgroundColor(Color.parseColor("#4CAF50"));

        getReceived();

        System.out.println(receiveds.size());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvReceived = new RecyclerViewMessageAdapter(receiveds,getApplicationContext());
        recyclerView.setAdapter(rvReceived);

    }

    public void sent(View view){
        System.out.println("sent size"+ sents.size());
        sents.clear();
        received.setTextSize(15);
        sent.setTextSize(30);
        sent.setTextColor(Color.parseColor("#ffffff"));
        sent.setBackgroundColor(Color.parseColor("#4CAF50"));
        received.setTextColor(Color.parseColor("#000000"));
        received.setBackgroundColor(Color.parseColor("#ffffff"));

        receiveds.clear();

        getSent();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvSent = new RecyclerViewMessageAdapter(sents,getApplicationContext());
        recyclerView.setAdapter(rvSent);


    }
    public void received(View view){
        receiveds.clear();
        sent.setTextSize(15);
        received.setTextSize(30);
        received.setTextColor(Color.parseColor("#ffffff"));
        received.setBackgroundColor(Color.parseColor("#4CAF50"));
        sent.setTextColor(Color.parseColor("#000000"));
        sent.setBackgroundColor(Color.parseColor("#ffffff"));

        sents.clear();

        getReceived();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvReceived = new RecyclerViewMessageAdapter(receiveds,getApplicationContext());
        recyclerView.setAdapter(rvReceived);

    }


    public void getReceived(){


        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = firebaseUser.getEmail();

        CollectionReference collectionReference =firebaseFirestore.collection("MESAJ");

        collectionReference.whereEqualTo("receiverEmail", userEmail).orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                    System.out.println(e.getLocalizedMessage().toString());

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map<String,Object> data = snapshot.getData();

                        String senderPp= (String) data.get("senderProfilePhoto");
                        String senderEmail = (String) data.get("senderEmail");
                        String lobId=(String) data.get("lobiId");


                        MessageLobi messageLobi = new MessageLobi(senderEmail,senderPp,lobId);

                        receiveds.add(messageLobi);


                        rvReceived.notifyDataSetChanged();

                        System.out.println("merhbaaaakkka");



                    }
                }
            }
        });

    }
    public void getSent(){





        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = firebaseUser.getEmail();

        System.out.println("user mail . "+userEmail);

        CollectionReference collectionReference =firebaseFirestore.collection("MESAJ");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                    System.out.println(e.getLocalizedMessage().toString());

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map<String,Object> data = snapshot.getData();

                        String senderPp= (String) data.get("receiverProfilePhoto");
                        String senderEmail = (String) data.get("receiverEmail");
                        String lobId=(String) data.get("lobiId");


                        MessageLobi messageLobi = new MessageLobi(senderEmail,senderPp,lobId);

                        sents.add(messageLobi);


                        rvSent.notifyDataSetChanged();



                    }
                }
            }
        });
        System.out.println("sentsss ç: "+ sents.size());

    }



}
