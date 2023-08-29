package com.example.entertainmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private ImageButton playPauseButton;
    private SeekBar seekBar;
    private TextView videoTimingTextView;
    private final Handler handler = new Handler();
    private boolean isVideoPlaying = false;
    private int currentVideoTag = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        playPauseButton = findViewById(R.id.playPauseButtonVideo);
        seekBar = findViewById(R.id.seekBarVideo);
        videoTimingTextView = findViewById(R.id.timingTextViewVideo);
        ImageButton previousButton = findViewById(R.id.previousButtonVideo);
        ImageButton nextButton = findViewById(R.id.nextButtonVideo);

        ImageButton previousVideoButton = findViewById(R.id.previousVideoButton);
        ImageButton nextVideoButton = findViewById(R.id.nextVideoButton);

        int clickedTag = getIntent().getIntExtra("clickedTag", 1);
        currentVideoTag = clickedTag;
        int videoResource = getVideoResource(clickedTag);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + videoResource);

        String videoTitle = getVideoTitle(clickedTag);
        TextView videoTitleTextView = findViewById(R.id.videoTitleTextView);
        videoTitleTextView.setText(videoTitle);

        previousVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPreviousVideo();
            }
        });

        nextVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextVideo();
            }
        });

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVideoPlaying) {
                    startVideo();
                } else {
                    pauseVideo();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipBackward(10000); // Skip 10 seconds backward
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipForward(10000); // Skip 10 seconds forward
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                    videoTimingTextView.setText(formatTime(progress / 1000) + " / " + formatTime(videoView.getDuration() / 1000));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for now
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for now
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(videoView.getDuration());
                updateSeekBar();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextVideo();
            }
        });
    }

    private void playPreviousVideo() {
        currentVideoTag--;
        if (currentVideoTag < 1) {
            currentVideoTag = 11;
        }
        playSelectedVideo();
    }

    private void playNextVideo() {
        currentVideoTag++;
        if (currentVideoTag > 11) {
            currentVideoTag = 1;
        }
        playSelectedVideo();
    }

    private void playSelectedVideo() {
        int videoResource = getVideoResource(currentVideoTag);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + videoResource);
        String videoTitle = getVideoTitle(currentVideoTag);
        TextView videoTitleTextView = findViewById(R.id.videoTitleTextView);
        videoTitleTextView.setText(videoTitle);
        startVideo();
    }

    private void startVideo() {
        videoView.start();
        playPauseButton.setImageResource(R.drawable.pause);
        isVideoPlaying = true;
        updateSeekBar();
    }

    private void pauseVideo() {
        videoView.pause();
        playPauseButton.setImageResource(R.drawable.play);
        isVideoPlaying = false;
    }

    private void skipBackward(int milliseconds) {
        int newPosition = videoView.getCurrentPosition() - milliseconds;
        if (newPosition < 0) {
            newPosition = 0;
        }
        videoView.seekTo(newPosition);
        updateSeekBar();
    }

    private void skipForward(int milliseconds) {
        int newPosition = videoView.getCurrentPosition() + milliseconds;
        if (newPosition > videoView.getDuration()) {
            newPosition = videoView.getDuration();
        }
        videoView.seekTo(newPosition);
        updateSeekBar();
    }

    private void updateSeekBar() {
        if (videoView.isPlaying()) {
            seekBar.setProgress(videoView.getCurrentPosition());
            int currentTimeInSeconds = videoView.getCurrentPosition() / 1000;
            int totalTimeInSeconds = videoView.getDuration() / 1000;
            videoTimingTextView.setText(formatTime(currentTimeInSeconds) + " / " + formatTime(totalTimeInSeconds));
            handler.postDelayed(updateSeekBarTask, 500);
        }
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    private final Runnable updateSeekBarTask = new Runnable() {
        public void run() {
            updateSeekBar();
        }
    };

    private String getVideoTitle(int clickedTag) {
        switch (clickedTag) {
            case 1:
                return "Bas Tu Dikhti H";
            case 2:
                return "Befikar";
            case 3:
                return "Dil Beparvah";
            case 4:
                return "Dilli ki Ladki";
            case 5:
                return "Inayat";
            case 6:
                return "Muskurao";
            case 7:
                return "Rabba Janda";
            case 8:
                return "What Jhumka";
            case 9:
                return "Tum kya mile";
            case 10:
                return "Scoopwhoop Mashup";
            case 11:
                return "Maa Poetry";
            default:
                return "Video Title";
        }
    }

    private int getVideoResource(int clickedTag) {
        switch (clickedTag) {
            case 1:
                return R.raw.bas_tu_dikhti_h;
            case 2:
                return R.raw.befikar;
            case 3:
                return R.raw.dil_beparvah;
            case 4:
                return R.raw.dilli_ki_ladki;
            case 5:
                return R.raw.inayat;
            case 6:
                return R.raw.muskurao;
            case 7:
                return R.raw.rabba_janda;
            case 8:
                return R.raw.what_jhumka;
            case 9:
                return R.raw.tum_kya_mile;
            case 10:
                return R.raw.scoopwhoop_mashup;
            case 11:
                return R.raw.maa;
        }
        return R.raw.maa;
    }
}
