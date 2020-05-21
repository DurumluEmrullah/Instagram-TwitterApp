package com.durumluemrullah.mobilappproje.aktivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.fragments.EditProfileFragment;
import com.durumluemrullah.mobilappproje.fragments.FollowersAndFollowingFragment;
import com.durumluemrullah.mobilappproje.fragments.NewUserFragment;

public class GeneralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        Intent intent = getIntent();
        String purpose =intent.getStringExtra("purpose");

        if(purpose.equals("following")) {
            FollowersAndFollowingFragment followersAndFollowingFragment = new FollowersAndFollowingFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.general_container,followersAndFollowingFragment).commit();

        }
        else if(purpose.equals("follower")){
            FollowersAndFollowingFragment followersAndFollowingFragment = new FollowersAndFollowingFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.general_container,followersAndFollowingFragment).commit();
        }
        else if(purpose.equals("editProfile")){
            EditProfileFragment editProfileFragment= new EditProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.general_container,editProfileFragment).commit();
        }

        else if(purpose.equals("newAccount")){

            NewUserFragment newUserFragment =new NewUserFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.general_container,newUserFragment).commit();

        }
    }
}
