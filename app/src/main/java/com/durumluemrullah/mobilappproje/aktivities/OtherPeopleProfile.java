package com.durumluemrullah.mobilappproje.aktivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.durumluemrullah.mobilappproje.Classes.MessageLobi;
import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.Classes.Twit;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedPost;
import com.durumluemrullah.mobilappproje.adapters.RecyclerViewFeedTwit;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class OtherPeopleProfile extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    ImageView back,profilePhoto;
    TextView countTwit,countPost,followers,following, name, biography ,userEmail,posts,twits;
    ArrayList<Post> postsList;
    ArrayList<Twit> twitList;
    RecyclerViewFeedPost recyclerViewFeedPost;
    RecyclerViewFeedTwit recyclerViewFeedTwit;
    String userEmail1;
    String profilePhotoUri;
    String lastSender;
    String lastReceiver;
    String lastMessageLobi;
    String lastSender2;
    String lastReceiver2;

    RecyclerView recyclerView ;
    String user;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        setContentView(R.layout.activity_other_people_profile);
        back = findViewById(R.id.ivBack);
        profilePhoto=findViewById(R.id.profilePhoto);
        countPost = findViewById(R.id.countPosts);
        countTwit=findViewById(R.id.countTwit);

        name = findViewById(R.id.nameAndSurname);
        biography=findViewById(R.id.biography);
        firebaseFirestore = FirebaseFirestore.getInstance();
        userEmail=findViewById(R.id.userEmail);
        postsList =new ArrayList<>();
        twitList=new ArrayList<>();
        posts =findViewById(R.id.tvGoPost);
        twits = findViewById(R.id.tvGoTwit);
        recyclerView = findViewById(R.id.rvPostviev);

        CollectionReference collectionReference = firebaseFirestore.collection("Users");

        collectionReference.whereEqualTo("userEmail",user).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e != null) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map<String,Object> data = snapshot.getData();

                        String userName1 =(String) data.get("userName");
                        String name1 =(String) data.get("name");
                        String profilePhototoUrl1 =(String) data.get("profilePhoto");
                        String biography1 =(String) data.get("biography");
                        String lastName1=(String) data.get("lastName");
                        String userId1 = (String)data.get("userId");

                        name.setText(name1 + " "+lastName1);
                        Picasso.get().load(profilePhototoUrl1).into(profilePhoto);
                        biography.setText(biography1);
                        userEmail.setText(userName1);


                    }
                }

            }
        });


        twits.setTextSize(15);
        posts.setTextSize(30);
        posts.setTextColor(Color.parseColor("#ffffff"));
        posts.setBackgroundColor(Color.parseColor("#4CAF50"));

        getPost();




        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewFeedPost = new RecyclerViewFeedPost(postsList,getApplicationContext());
        recyclerView.setAdapter(recyclerViewFeedPost);


    }

    public void back(View view){

        finish();
    }




    public void posts(View view){

        postsList.clear();
        twits.setTextSize(15);
        posts.setTextSize(30);
        posts.setTextColor(Color.parseColor("#ffffff"));
        posts.setBackgroundColor(Color.parseColor("#4CAF50"));
        twits.setTextColor(Color.parseColor("#000000"));
        twits.setBackgroundColor(Color.parseColor("#ffffff"));

        twitList.clear();

        getPost();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewFeedPost = new RecyclerViewFeedPost(postsList,getApplicationContext());
        recyclerView.setAdapter(recyclerViewFeedPost);


    }
    public void twits(View view){
        twitList.clear();
        posts.setTextSize(15);
        twits.setTextSize(30);
        twits.setTextColor(Color.parseColor("#ffffff"));
        twits.setBackgroundColor(Color.parseColor("#4CAF50"));
        posts.setTextColor(Color.parseColor("#000000"));
        posts.setBackgroundColor(Color.parseColor("#ffffff"));
        postsList.clear();


        getTwit();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewFeedTwit = new RecyclerViewFeedTwit (twitList,getApplicationContext());
        recyclerView.setAdapter(recyclerViewFeedTwit);


    }

    public void  getPost(){

        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();






        CollectionReference collectionReference =firebaseFirestore.collection("POSTSDETAIL");

        collectionReference.whereEqualTo("userEmail", user).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map<String,Object> data = snapshot.getData();

                        String postUrl =(String) data.get("postUri");


                        userEmail1 =(String) data.get("userEmail");
                        profilePhotoUri =(String) data.get("profilePhotoUri");
                        String postUri =(String) data.get("postUri");
                        String explanation =(String) data.get("explanation");

                        Post post = new Post(userEmail1,profilePhotoUri,postUri,explanation);

                        postsList.add(post);


                        recyclerViewFeedPost.notifyDataSetChanged();

                        System.out.println(postsList.size());

                        countPost.setText(" Post\n"+postsList.size());

                    }
                }
            }
        });


    }

    public void getTwit(){




            CollectionReference collectionReference =firebaseFirestore.collection("Twit");

            collectionReference.whereEqualTo("userEmail",user).orderBy("shareTime", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null){

                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

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
                            Twit objTwit=new Twit(userId,userName,user,twit,twitId,profilePhototoUrl,shareTime);
                            twitList.add(objTwit);

                            recyclerViewFeedTwit.notifyDataSetChanged();

                            countTwit.setText( "Twits\n"+twitList.size());





                        }
                    }
                }
            });

    }

    public void sendMessage(View view){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        final String senderEmail =firebaseUser.getEmail();






// Daha önceden mesaj oluşturuldu mu ?
        CollectionReference collectionReference1 = firebaseFirestore.collection("MESAJ");

        collectionReference1.whereEqualTo("senderEmail",senderEmail).whereEqualTo("receiverEmail",userEmail1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if ( e != null){
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                    }

                    if (queryDocumentSnapshots != null){
                        for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                            Map<String,Object> data =snapshot.getData();
                            lastSender =(String) data.get("senderEmail");
                            lastReceiver=(String) data.get("receiverEmail");
                            lastMessageLobi=(String)data.get("lobiId");


                        }

                    }
            }
        });

        if(!(lastSender== senderEmail && lastReceiver ==userEmail1)) {

            UUID uuid = UUID.randomUUID();
            final String lobiId = "" + uuid;
            CollectionReference collectionReference = firebaseFirestore.collection("Users");
            collectionReference.whereEqualTo("userEmail", senderEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                    if (queryDocumentSnapshots != null) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            Map<String, Object> data = snapshot.getData();
                            String senderProfilePhotoUri = (String) data.get("profilePhoto");
                            FieldValue date =FieldValue.serverTimestamp();

                            MessageLobi message = new MessageLobi(senderEmail, senderProfilePhotoUri, userEmail1, profilePhotoUri, lobiId,date);

                            firebaseFirestore.collection("MESAJ").add(message).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(getApplicationContext(), MessagePageActivity.class);
                                    intent.putExtra("lobiId", lobiId);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }
            });


        }else{
            Intent intent = new Intent(getApplicationContext(),MessagePageActivity.class);
            intent.putExtra("lobiId",lastMessageLobi);
            startActivity(intent);
        }




    }
}
