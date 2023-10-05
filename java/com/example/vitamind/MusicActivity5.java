package com.example.vitamind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MusicActivity5 extends AppCompatActivity {

    MediaPlayer player;
    TextView click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music5);

        // creak hooks
        click = findViewById(R.id.textClick5);

        // go to YouTube opened in the browser
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/results?search_query=10+minute+meditation"));
                stopPlayer();
                startActivity(intent);
            }
        });
    }

    // play music
    public void play(View v){
        if(player == null){
            player = MediaPlayer.create(this, R.raw.med10);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }

        player.start();
    }

    // pause music
    public void pause(View v){
        if(player != null){
            player.pause();
        }

    }

    public void stop(View v){
        stopPlayer();
    }

    // stop music
    private void stopPlayer(){
        if(player != null){
            player.release();
            player = null;
            Toast.makeText(this, "Music Stopped", Toast.LENGTH_SHORT).show();
        }
    }

    // stop music if back is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}