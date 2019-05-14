package com.example.sampleaudioplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String AUDIO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";
    private MediaPlayer mediaPlayer;
    private int playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    playAudio(AUDIO_URL);
                    Toast.makeText(MainActivity.this, "음악 파일 재생 시작", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button pauseButton = (Button) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    playbackPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                    Toast.makeText(MainActivity.this, "음악 파일 재생 중지", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button restartButton = (Button) findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    mediaPlayer.seekTo(playbackPosition);  // 중지한 지점에서부터 재생
                    Toast.makeText(MainActivity.this, "음악 파일 재생 재시작", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void playAudio(String url) throws Exception {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);  // 파일 위치 지정
        mediaPlayer.prepare();  // 재생 준비
        mediaPlayer.start();  // 파일 재생
    }

    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    // MediaPlayer객체가 이미 리소스를 사용하고있을 경우 리소스 해제
    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();  // 리소스 해제 (MediaPlayer 재사용하기 위해)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
