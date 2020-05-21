package com.durumluemrullah.mobilappproje.aktivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.durumluemrullah.mobilappproje.Classes.Comment;
import com.durumluemrullah.mobilappproje.R;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    EditText comment;
    String postId;
    ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore ;
    ArrayList<String> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        comment= findViewById(R.id.etComment);
        listView=findViewById(R.id.lvComment);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();
        comments = new ArrayList<>();
        Intent intent = getIntent();
        postId=intent.getStringExtra("posdId");



        getComments();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,comments);
        listView.setAdapter(arrayAdapter);

    }

    public void share(View view){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String commenter = firebaseUser.getEmail();
        String comment1 = comment.getText().toString().trim();
        FieldValue date = FieldValue.serverTimestamp();
        if(!comment1.equals("")){
            final Comment dbcomment = new Comment(postId,commenter,comment1,date);

            firebaseFirestore.collection("Comments").add(dbcomment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplicationContext(),"Shared Comment!",Toast.LENGTH_SHORT).show();
                    comment.setText("");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            });



        }
        else{

            Toast.makeText(getApplicationContext(),"Enter Comment !! ", Toast.LENGTH_SHORT).show();
        }


    }

    public void getComments(){

        CollectionReference collectionReference = firebaseFirestore.collection("Comments");

            collectionReference.whereEqualTo("postId",postId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!= null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                }
                if(queryDocumentSnapshots != null){


                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String commenter = (String)data.get("commenter");
                        String comment1 = (String) data.get("comment");

                        String comment = commenter +" ~ "+comment1;

                        comments.add(comment);
                        System.out.println(comment);



                    }
                }
            }
        });

    }
}
