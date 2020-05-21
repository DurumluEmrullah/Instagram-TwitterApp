package com.durumluemrullah.mobilappproje.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.durumluemrullah.mobilappproje.R;
import com.durumluemrullah.mobilappproje.aktivities.FeedActivity;

public class NewUserFragment extends Fragment {

    TextView next;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_user,container,false);
        next=view.findViewById(R.id.nextText);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity().getApplicationContext(), FeedActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
