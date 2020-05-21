package com.durumluemrullah.mobilappproje.aktivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.durumluemrullah.mobilappproje.Classes.Comment;
import com.durumluemrullah.mobilappproje.Classes.Message;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MessagePageActivity extends AppCompatActivity {
    String lobiId;
    ImageButton back;
    TextView senderName;
    EditText newMessage;
    ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore ;
    ArrayList<String> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);
        Intent intent = getIntent();
        lobiId =intent.getStringExtra("lobiId");
        messages=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        senderName=findViewById(R.id.senderName);
        newMessage=findViewById(R.id.newMessage);
        listView = findViewById(R.id.lvMessages);

        getMessage();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,messages);
        listView.setAdapter(arrayAdapter);

        listView.getDescendantFocusability();



    }
    public void backMessage(View view){
        Intent intent= new Intent(getApplicationContext(),MessageActivity.class);
        startActivity(intent);
        finish();

    }

    public void send (View view){

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String sender = firebaseUser.getEmail();
        String message = newMessage.getText().toString().trim();
        FieldValue date = FieldValue.serverTimestamp();
        if(!message.trim().equals("")){
            Message message1 = new Message(lobiId,message,sender,date);

            firebaseFirestore.collection("Message").add(message1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    newMessage.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{

            Toast.makeText(getApplicationContext(),"You must write somethink!! ", Toast.LENGTH_SHORT).show();
        }

    }

    public void getMessage(){


        CollectionReference collectionReference = firebaseFirestore.collection("Message");

        collectionReference.whereEqualTo("lobiId",lobiId).orderBy("fieldValue", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!= null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                    System.out.println(e.getLocalizedMessage().toString());

                }
                if(queryDocumentSnapshots != null){


                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String sender=(String)data.get("sender");
                        String message=(String)data.get("message");

                        String allMessage= sender+ ": \n   " +message;


                        messages.add(allMessage);
                        System.out.println(messages.size());




                    }
                }
            }
        });




    }
}
