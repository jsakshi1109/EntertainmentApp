package com.example.entertainmentapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnMusic = findViewById(R.id.btnAudio);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnVideo = findViewById(R.id.btnVideo);

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, MainVideoActivity.class);
                startActivity(intent);
            }
        });
    }
}
