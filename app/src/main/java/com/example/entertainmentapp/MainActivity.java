package com.example.entertainmentapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onSongItemClick(View view) {
        int songNumber = Integer.parseInt(view.getTag().toString());
        Intent intent = new Intent(this, SongActivity.class);
        intent.putExtra("songNumber", songNumber);
        startActivity(intent);
    }
}
