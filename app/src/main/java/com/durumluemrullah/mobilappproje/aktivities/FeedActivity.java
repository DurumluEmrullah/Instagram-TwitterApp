package com.durumluemrullah.mobilappproje.aktivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.fragments.FeedFragmentPost;
import com.durumluemrullah.mobilappproje.fragments.FeedFragmentTwit;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FeedActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    ImageButton post,twit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        post = findViewById(R.id.postButton);
        twit = findViewById(R.id.twitButton);


        post.setBackgroundColor(Color.parseColor("#4CAF50"));


        FeedFragmentPost feedFragmentPost = new FeedFragmentPost();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_add_photo,feedFragmentPost).commit();

        navigationView=findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(navlistener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id  == R.id.action_name){
            Intent intent = new Intent(FeedActivity.this,MessageActivity.class);
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Intent intent;
                    switch(menuItem.getItemId()){
                        case R.id.nav_feed:

                            break;
                        case R.id.nav_search:
                            intent = new Intent(getApplicationContext(),AccountSearchNotificationActivity.class);
                            intent.putExtra("menu",R.id.nav_search);
                            startActivity(intent);
                            break;
                        case R.id.nav_add_photo:
                            intent = new Intent(getApplicationContext(),AddPhotoActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.nav_account:
                            intent = new Intent(getApplicationContext(),AccountSearchNotificationActivity.class);
                            intent.putExtra("menu",R.id.nav_account);
                            startActivity(intent);
                            break;
                    }
                        return true;
                }
            };

    public void feedPosts(View view){

        post.setBackgroundColor(Color.parseColor("#4CAF50"));
        twit.setBackgroundColor(Color.parseColor("#ffffff"));
        FeedFragmentPost feedFragmentPost = new FeedFragmentPost();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_add_photo,feedFragmentPost).commit();

    }
    public void feedTwits(View view){
        twit.setBackgroundColor(Color.parseColor("#4CAF50"));
        post.setBackgroundColor(Color.parseColor("#ffffff"));
        FeedFragmentTwit feedFragmentTwit = new FeedFragmentTwit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_add_photo,feedFragmentTwit).commit();

    }
}
