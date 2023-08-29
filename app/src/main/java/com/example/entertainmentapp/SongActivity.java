package com.example.entertainmentapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private int currentSongNumber;
    private List<Integer> shuffledSongOrder;
    private int shuffledIndex;
    private boolean isHearted = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song1);

        currentSongNumber = getIntent().getIntExtra("songNumber", 11);
        playSong(currentSongNumber);

        currentSongNumber = getIntent().getIntExtra("songNumber", 11);
        shuffledSongOrder = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            shuffledSongOrder.add(i);
        }
        Collections.shuffle(shuffledSongOrder);
        shuffledIndex = shuffledSongOrder.indexOf(currentSongNumber);

        playSong(currentSongNumber);

        final ImageButton previousButton = findViewById(R.id.previousButton);
        final ImageButton nextButton = findViewById(R.id.nextButton);
        final ImageButton playPauseButton = findViewById(R.id.playPauseButton);
        final ImageButton shuffleButton = findViewById(R.id.shuffleButton); // Add this button
        final SeekBar seekBar = findViewById(R.id.seekBar);
        final TextView songTimingTextView = findViewById(R.id.timingTextView);
        final ImageButton heartButton = findViewById(R.id.heartButton);

        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart(heartButton);
            }
        });

        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playShuffledSong();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPreviousSong();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextSong();
            }
        });

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    mediaPlayer.start();
                    playPauseButton.setImageResource(R.drawable.pause);
                    isPlaying = true;
                    updateSeekBar(seekBar, songTimingTextView);
                } else if (isPaused) {
                    mediaPlayer.start();
                    playPauseButton.setImageResource(R.drawable.pause);
                    isPaused = false;
                } else {
                    mediaPlayer.pause();
                    playPauseButton.setImageResource(R.drawable.play);
                    isPaused = true;
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    songTimingTextView.setText(formatTime(progress / 1000) + " / " + formatTime(mediaPlayer.getDuration() / 1000));
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

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextSong();
            }
        });

    }

    private void updateSeekBar(final SeekBar seekBar, final TextView songTimingTextView) {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        int currentTimeInSeconds = mediaPlayer.getCurrentPosition() / 1000;
        int totalTimeInSeconds = mediaPlayer.getDuration() / 1000;
        songTimingTextView.setText(formatTime(currentTimeInSeconds) + " / " + formatTime(totalTimeInSeconds));

        if (mediaPlayer.isPlaying()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar(seekBar, songTimingTextView);
                }
            };
            handler.postDelayed(runnable, 500);
        }
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    private int getSongResourceId(int songNumber) {
        switch (songNumber) {
            case 1:
                return R.raw.baarishein;
            case 2:
                return R.raw.alag_aasman;
            case 3:
                return R.raw.gul;
            case 4:
                return R.raw.dooriyan;
            case 5:
                return R.raw.tere_jaane_se;
            case 6:
                return R.raw.ktmbk;
            case 7:
                return R.raw.tere_bina;
            case 8:
                return R.raw.na_sata;
            case 9:
                return R.raw.love_story;
            case 10:
                return R.raw.kasoor;
            case 11:
                return R.raw.kya_karoon;
            default:
                return 0;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setSongDetails(int songNumber, TextView songTitleTextView, ImageView songImage) {
        switch (songNumber) {
            case 1: songTitleTextView.setText("Baarishein");
                songImage.setImageResource(R.drawable.baarishein);
                break;

            case 2: songTitleTextView.setText("Alag Aasman");
                songImage.setImageResource(R.drawable.alag_aasman);
                break;

            case 3: songTitleTextView.setText("Gul");
                songImage.setImageResource(R.drawable.gul);
                break;

            case 4: songTitleTextView.setText("Dooriyaan");
                songImage.setImageResource(R.drawable.dooriyan);
                break;

            case 5: songTitleTextView.setText("Tere Jaane Se");
                songImage.setImageResource(R.drawable.tere_jaane_se);
                break;

            case 6: songTitleTextView.setText("KTMBK");
                songImage.setImageResource(R.drawable.ktmbk);
                break;

            case 7: songTitleTextView.setText("Tere Bina");
                songImage.setImageResource(R.drawable.tere_bina);
                break;

            case 8: songTitleTextView.setText("Na Sata");
                songImage.setImageResource(R.drawable.na_sata);
                break;

            case 9: songTitleTextView.setText("Love Story");
                songImage.setImageResource(R.drawable.love_story);
                break;

            case 10: songTitleTextView.setText("Kasoor");
                songImage.setImageResource(R.drawable.kasoor);
                break;

            case 11: songTitleTextView.setText("Kya Karoon");
                songImage.setImageResource(R.drawable.kya_karoon);
                break;
        }
    }

    private void playSong(int songNumber) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, getSongResourceId(songNumber));
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView songTimingTextView = findViewById(R.id.timingTextView);
        ImageView songImage = findViewById(R.id.songImage);
        TextView songTitleTextView = findViewById(R.id.songTitle);

        seekBar.setMax(mediaPlayer.getDuration());

        String songTitle = "Title Song " + songNumber;
        songTitleTextView.setText(songTitle);
        setSongDetails(songNumber, songTitleTextView, songImage);
        mediaPlayer.start();
        isPlaying = true;
        updateSeekBar(seekBar, songTimingTextView);
    }

    private void playPreviousSong() {
        currentSongNumber = (currentSongNumber - 2 + 12) % 12 + 1;
        playSong(currentSongNumber);
    }

    private void playNextSong() {
        currentSongNumber = currentSongNumber % 12 + 1;
        playSong(currentSongNumber);
    }

    private void playShuffledSong() {
        shuffledIndex = (shuffledIndex + 1) % shuffledSongOrder.size();
        int currentSongNumber1 = shuffledSongOrder.get(shuffledIndex);
        playSong(currentSongNumber1);
    }

    private void toggleHeart(ImageButton heartButton) {
        if (isHearted) {
            heartButton.setImageResource(R.drawable.heart);
        } else {
            heartButton.setImageResource(R.drawable.red_heart);
        }
        isHearted = !isHearted;
    }
}

