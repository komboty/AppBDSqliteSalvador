package com.example.appbdsqlitesalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class AudioVideo extends AppCompatActivity implements View.OnClickListener {
    public Button btnReproducir, btnDetener;
    public MediaPlayer reproductor;
    public VideoView videoPrueba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_video);

        btnReproducir = findViewById(R.id.btnReproducir);
        btnDetener = findViewById(R.id.btnDetener);
        videoPrueba = (VideoView) findViewById(R.id.vvPrueba);

        btnReproducir.setOnClickListener(this);
        btnDetener.setOnClickListener(this);

        Uri path = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
        videoPrueba.setVideoURI(path);
        videoPrueba.setMediaController(new MediaController(this));
        videoPrueba.start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnReproducir){
            play_mpa1();
        } else if (view.getId() == R.id.btnDetener){
            stop();
        }
    }

    private void play_mpa1() {
        reproductor = MediaPlayer.create(this, R.raw.sonido);
        reproductor.start();
    }

    private void stop() {
        reproductor.stop();
    }


}