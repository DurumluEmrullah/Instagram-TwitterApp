package com.durumluemrullah.mobilappproje.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.durumluemrullah.mobilappproje.Classes.User;
import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.aktivities.AddPhotoActivity;
import com.durumluemrullah.mobilappproje.aktivities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public  class EditProfileFragment extends Fragment {
    ImageView editProfileBack, editProfileCheck;
    Button logOut;
    ImageView profilePhoto;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    Bitmap selectedImage;
    EditText etName, etSurname, etUserName, etBio;
    TextView tvEmail;
    Uri imageData;
    static String downloadUrl;


    private String userName, userEmail, name, lastName, biography, profilePhotofromdb, updateId;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        editProfileBack = view.findViewById(R.id.ivEditProfileBack);
        editProfileCheck = view.findViewById(R.id.ivEditProfileCheck);
        logOut = view.findViewById(R.id.btLog_out);
        profilePhoto = view.findViewById(R.id.profilePhoto);
        etName = view.findViewById(R.id.etName);
        etUserName = view.findViewById(R.id.etUserNameEdit);
        etBio = view.findViewById(R.id.etBio);
        etSurname = view.findViewById(R.id.etSurname);
        tvEmail = view.findViewById(R.id.tvEmail);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final String email = firebaseUser.getEmail();
        CollectionReference collectionReference = firebaseFirestore.collection("Users");

        collectionReference.whereEqualTo("userEmail", email).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(getActivity().getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                if (queryDocumentSnapshots != null) {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();

                        userName = (String) data.get("userName");
                        userEmail = (String) data.get(("userEmail"));
                        name = (String) data.get("name");
                        lastName = (String) data.get("lastName");
                        biography = (String) data.get("biography");
                        profilePhotofromdb = (String) data.get("profilePhoto");
                        updateId = snapshot.getId();


                        etSurname.setText(lastName);
                        etBio.setText(biography);
                        etUserName.setText(userName);
                        tvEmail.setText(userEmail);
                        Picasso.get().load(profilePhotofromdb).into(profilePhoto);
                        etName.setText(name);


                    }
                }
            }
        });


        editProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        editProfileCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println(userName);
                System.out.println(userEmail);
                System.out.println(name);
                System.out.println(lastName);
                System.out.println(biography);
                System.out.println(profilePhotofromdb);
                System.out.println(updateId);


                userName = etUserName.getText().toString().trim();
                userEmail = tvEmail.getText().toString().trim();
                name = etName.getText().toString();
                lastName = etSurname.getText().toString().trim();
                biography = etBio.getText().toString().trim();


                User newUser = new User(userName, name, lastName, biography, profilePhotofromdb, userEmail);


                CollectionReference collectionReference = firebaseFirestore.collection("Users");

                collectionReference.document(updateId)
                        .set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //   Toast.makeText(getActivity().getApplicationContext(),"Update Succsessful",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });


                getActivity().finish();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();

                Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });

        return view;
    }
}