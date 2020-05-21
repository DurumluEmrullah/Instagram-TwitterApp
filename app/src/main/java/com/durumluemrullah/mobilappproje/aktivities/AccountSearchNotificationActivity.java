package com.durumluemrullah.mobilappproje.aktivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.fragments.AccountFragment;
import com.durumluemrullah.mobilappproje.fragments.NotificationsFragment;
import com.durumluemrullah.mobilappproje.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountSearchNotificationActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_search_notification);


        navigationView=findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(navlistener);

        Intent intent = getIntent();
        int item =intent.getIntExtra("menu",0);
        Fragment selectedFragment=null;
        if(item == R.id.nav_account){
            selectedFragment = new AccountFragment();
        }
        else if(item == R.id.nav_search){
            selectedFragment = new SearchFragment();
        }

            getSupportFragmentManager().beginTransaction().replace(R.id.container_account_search_notification,selectedFragment).commit();



    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Intent intent;
                    Fragment selectedFragment1=null;
                    switch(menuItem.getItemId()){
                        case R.id.nav_feed:
                            intent = new Intent(getApplicationContext(),FeedActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_search:
                            selectedFragment1 = new SearchFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container_account_search_notification,selectedFragment1)
                                    .commit();
                            break;
                        case R.id.nav_add_photo:
                            intent = new Intent(getApplicationContext(),AddPhotoActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_account:
                            selectedFragment1 = new AccountFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container_account_search_notification,selectedFragment1)
                                    .commit();
                            break;
                    }

                    return true;
                }
            };
}
