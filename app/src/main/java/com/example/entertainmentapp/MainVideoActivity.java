package com.example.entertainmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_video);
    }
    public void onVideoItemClick(View view) {
        int clickedTag = Integer.parseInt(view.getTag().toString());
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("clickedTag", clickedTag);
        startActivity(intent);
    }
}