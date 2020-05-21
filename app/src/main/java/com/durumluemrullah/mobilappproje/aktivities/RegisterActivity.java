package com.durumluemrullah.mobilappproje.aktivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.durumluemrullah.mobilappproje.Classes.User;
import com.durumluemrullah.mobilappproje.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth ;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    EditText name,surname,userName,eMail,password,confirfPassword;
    ImageView profilePhoto;
    Uri imageData;
    String profilePhotoUri;
    Bitmap selectedImage;
    Vibrator vibrator;
   // String profilePhotoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();
        storageReference =firebaseStorage.getReference();
        name =findViewById(R.id.etName);
        surname=findViewById(R.id.etSurname);
        userName=findViewById(R.id.etUsername);
        eMail=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassword);
        confirfPassword=findViewById(R.id.etConfirmPassword);
        profilePhoto = findViewById(R.id.profilePhoto);
    }
    public void login(View view){
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void selectImage(View view){

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


        }
        else{

            Intent intentToGalery= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGalery,2);
        }


    }

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
                    profilePhoto.setImageBitmap(selectedImage);
                }else {

                    selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageData);
                    profilePhoto.setImageBitmap(selectedImage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void register(View view){




        if (imageData !=  null ){

            UUID uuid= UUID.randomUUID();
            final String imageName ="POST/images"+ uuid+".jpg";


            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference newReferance =FirebaseStorage.getInstance().getReference(imageName);
                    newReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            profilePhotoUri = uri.toString();

                            User user =new User();


                            final String name1 = name.getText().toString().trim();
                            final String lastName = surname.getText().toString().trim();
                            final String userName1 =userName.getText().toString().trim();
                            String eMail1 = eMail.getText().toString().trim();
                            String password1 =password.getText().toString().trim();
                            String confirmPassword1 = confirfPassword.getText().toString().trim();



                            if(!name1.equals("")&&!lastName.equals("")&&!userName1.equals("")&&!eMail1.equals("")&&!password1.equals("")&&!confirmPassword1.equals("")){
                                if(password1.equals(confirmPassword1)){
                                    firebaseAuth.createUserWithEmailAndPassword(eMail1,password1).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                                                String userEmail = firebaseUser.getEmail();
                                                String biography ="";

                                                User user = new User(userName1,name1,lastName,biography,profilePhotoUri,userEmail);
                                                firebaseFirestore.collection("Users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                                                        startActivity(intent);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    });


                                }else
                                {
                                    Toast.makeText(getApplicationContext(), "passwords did not match",Toast.LENGTH_SHORT).show();
                                }



                            }
                            else{

                                Toast.makeText(getApplicationContext(),"fill in all fields !!!",Toast.LENGTH_SHORT).show();

                                vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(50);


                            }



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

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




        // prototip

    }
}
