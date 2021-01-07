package com.example.socialmediaaggregatorapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.socialmediaaggregatorapp.Activities.CreatePostActivity;
import com.example.socialmediaaggregatorapp.Activities.CreateStoryActivity;
import com.example.socialmediaaggregatorapp.Activities.MainActivity;
import com.example.socialmediaaggregatorapp.R;
public class BottomNavigationFragment extends Fragment {

    public BottomNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

        Button MainPageButton = rootView.findViewById(R.id.MainPageBtn);
        MainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button createPostButton = rootView.findViewById(R.id.CreatePostActivityBtn);
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(intent);
            }
        });

        Button createStoryButton = rootView.findViewById(R.id.CreateStoryBtn);
        createStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateStoryActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}