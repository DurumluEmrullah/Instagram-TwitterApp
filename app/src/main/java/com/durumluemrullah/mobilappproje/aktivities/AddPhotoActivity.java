package com.durumluemrullah.mobilappproje.aktivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.durumluemrullah.mobilappproje.Classes.Post;
import com.durumluemrullah.mobilappproje.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.MoreObjects;
import com.google.firebase.Timestamp;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class AddPhotoActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView,galeri;
    Bitmap selectedImage;
    Uri imageData;
    EditText comment;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    String profilePhotoUri;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        imageView=findViewById(R.id.ivBack);
        galeri = findViewById(R.id.ivGaleri);
        comment =findViewById(R.id.etComment);
        firebaseStorage =FirebaseStorage.getInstance();
        storageReference =firebaseStorage.getReference();
        firebaseFirestore =FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){


                    ActivityCompat.requestPermissions(AddPhotoActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


                }
                else{

                    Intent intentToGalery= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentToGalery,2);
                }
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode ==1 ){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intentToGalery= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGalery,2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode ==2 && resultCode ==RESULT_OK && data != null){

            imageData =data.getData();
            try {
                if(Build.VERSION.SDK_INT>=28){

                    ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(),imageData);
                    selectedImage =ImageDecoder.decodeBitmap(source);
                    galeri.setImageBitmap(selectedImage);
                }else {

                    selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageData);
                    galeri.setImageBitmap(selectedImage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void sharePost(View view){

        if(imageData != null) {

            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            String email = firebaseUser.getEmail();

            CollectionReference collectionReference =firebaseFirestore.collection("Users");

            collectionReference.whereEqualTo("userEmail", email).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null){

                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                    }

                    if(queryDocumentSnapshots !=null){

                        for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                            Map<String,Object> data = snapshot.getData();

                            userName =(String) data.get("userName");
                            profilePhotoUri =(String) data.get("profilePhoto");


                        }
                    }
                }
            });


            UUID uuid =UUID.randomUUID();

            final String imageName ="POST/images"+ uuid+".jpg";



            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // url yi alma
                    StorageReference newReference =FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl =uri.toString();
                            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                            String userEmail =firebaseUser.getEmail();
                            String myComment = comment.getText().toString();
                            FieldValue date = FieldValue.serverTimestamp();

                            String postId=imageName ;
                            Post post =new Post(userEmail,profilePhotoUri,downloadUrl,myComment,date);


                            firebaseFirestore.collection("POSTSDETAIL").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                  Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                                  startActivity(intent);
                                  finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{

            Toast.makeText(getApplicationContext(),"You must select a Image !!!",Toast.LENGTH_SHORT).show();
        }
    }
    public void getDataUserInformation(){
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String id = firebaseUser.getUid();
        System.out.println(id);

        CollectionReference collectionReference =firebaseFirestore.collection("Users");

        collectionReference.whereEqualTo("userId", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){

                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

                }

                if(queryDocumentSnapshots !=null){

                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){


                        Map<String,Object> data = snapshot.getData();

                        userName =(String) data.get("userName");
                        String name =(String) data.get("name");
                        profilePhotoUri =(String) data.get("profilePhoto");
                        String biography1 =(String) data.get("biography");
                        String lastName =(String) data.get("lastName");
                        String userId = (String)data.get("userId");

                        System.out.println("Fonk içi "+userName);
                        System.out.println("f "+profilePhotoUri);



                    }
                }
            }
        });


    }

}
