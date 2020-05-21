package com.durumluemrullah.mobilappproje.aktivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.durumluemrullah.mobilappproje.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();

        if(firebaseUser != null){

            Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
            startActivity(intent);
            finish();


            System.out.println(firebaseUser.getEmail());
        }
    }

    public void login(View view){
        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void register(View view){
        Intent intent = new Intent(HomeActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
