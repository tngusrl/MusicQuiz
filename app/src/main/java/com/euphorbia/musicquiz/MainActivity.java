package com.euphorbia.musicquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    private ImageButton setting;
    private ImageView iv;
    private TextView TextView;
    private TextView TextView1;
    private SeekBar SeekBar;
    private ImageButton btn_rewind, btn_play_pause, btn_forward;
    private Button answer;

    List<Mp3Data> mp3ListArray = new ArrayList<>();

    int position = 0;
    int pausePosition = 0;

    int musicSize = 0;

    float speed = 1.0f;
    boolean play_pause = false;

    public MediaPlayer mediaPlayer = new MediaPlayer();

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // OS가 Marshmallow 이상일 경우 권한체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {
                getAudioListFromMediaDatabase();
            }
        }
        // OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
        else {
            getAudioListFromMediaDatabase();
        }

        setting = findViewById(R.id.setting);
        TextView = findViewById(R.id.TextView);
        TextView1 = findViewById(R.id.TextView1);
        iv = findViewById(R.id.iv);
        SeekBar = findViewById(R.id.SeekBar);
        btn_rewind = findViewById(R.id.btn_rewind);
        btn_play_pause = findViewById(R.id.btn_play_pause);
        btn_forward = findViewById(R.id.btn_forward);
        answer = findViewById(R.id.answer);

        position = randomRange(0, mp3ListArray.size() - 1);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_play_pause.setImageResource(R.drawable.play);
                play_pause = false;
                mediaPlayer.pause();
                pausePosition = mediaPlayer.getCurrentPosition();

                SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser)  // 사용자가 시크바를 움직이면
                            pausePosition = progress;   // 재생위치를 바꿔준다(움직인 곳에서의 음악재생)
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });


                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(0);
                mediaPlayer.stop();
                position = randomRange(0, mp3ListArray.size() - 1);
                TextView.setText("노래 제목을 맞춰보세요!");
                TextView1.setText("");
                answer.setVisibility(View.GONE);
                btn_play_pause.setImageResource(R.drawable.play);
                play_pause = false;
                pausePosition = 0;

                SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser)  // 사용자가 시크바를 움직이면
                            pausePosition = progress;   // 재생위치를 바꿔준다(움직인 곳에서의 음악재생)
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
                SeekBar.setProgress(pausePosition);

            }
        });

        btn_rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(0);
                mediaPlayer.stop();
                position = randomRange(0, mp3ListArray.size() - 1);
                TextView.setText("노래 제목을 맞춰보세요!");
                TextView1.setText("");
                answer.setVisibility(View.GONE);
                btn_play_pause.setImageResource(R.drawable.play);
                play_pause = false;
                pausePosition = 0;

                SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser)  // 사용자가 시크바를 움직이면
                            pausePosition = progress;   // 재생위치를 바꿔준다(움직인 곳에서의 음악재생)
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
                SeekBar.setProgress(pausePosition);

            }
        });

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView.setText(mp3ListArray.get(position).getTitle());
                TextView1.setText(mp3ListArray.get(position).getArtist());

                Uri sArtworkUri = Uri
                        .parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, mp3ListArray.get(position).getAlbumId());

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                            getApplicationContext().getContentResolver(), albumArtUri);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

                    iv.setImageBitmap(bitmap);

                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                            R.drawable.ic_launcher_background);
                } catch (IOException e) {

                    e.printStackTrace();
                }


            }
        });

        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (play_pause) { // 일시정지 버튼 클릭
                        btn_play_pause.setImageResource(R.drawable.play);
                        play_pause = false;
                        mediaPlayer.pause();
                        pausePosition = mediaPlayer.getCurrentPosition();

                        SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser)  // 사용자가 시크바를 움직이면
                                    pausePosition = progress;   // 재생위치를 바꿔준다(움직인 곳에서의 음악재생)
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });

                    } else { // 재생 버튼 클릭
                        answer.setVisibility(View.VISIBLE);
                        btn_play_pause.setImageResource(R.drawable.pause);

                        play_pause = true;
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(mp3ListArray.get(position).getDataPath());
                        mediaPlayer.prepare();
                        SeekBar.setMax(mediaPlayer.getDuration());
                        mediaPlayer.seekTo(pausePosition);
                        SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser)  // 사용자가 시크바를 움직이면
                                    mediaPlayer.seekTo(progress);   // 재생위치를 바꿔준다(움직인 곳에서의 음악재생)
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });

                        PlaybackParams pp = mediaPlayer.getPlaybackParams();
                        pp.setSpeed(speed);
                        mediaPlayer.setPlaybackParams(pp);

                        new Thread(new Runnable() {  // 쓰레드 생성
                            @Override
                            public void run() {
                                while (mediaPlayer.isPlaying()) {  // 음악이 실행중일때 계속 돌아가게 함
                                    try {
                                        Thread.sleep(1000); // 1초마다 시크바 움직이게 함
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // 현재 재생중인 위치를 가져와 시크바에 적용
                                    if (mediaPlayer.isPlaying())
                                        SeekBar.setProgress(mediaPlayer.getCurrentPosition());
                                }
                            }
                        }).start();


                    }

                } catch (IOException e) {

                }

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAudioListFromMediaDatabase();
        }
    }

    @Override
    public void onBackPressed() {
        // 기존의 뒤로가기 버튼의 기능 제거
        // super.onBackPressed();

        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            mediaPlayer.release();
        }
    }

    // 홈버튼 클릭시
    @Override
    protected void onStop() {
        super.onStop();

        btn_play_pause.setImageResource(R.drawable.play);
        play_pause = false;
        mediaPlayer.pause();
        pausePosition = mediaPlayer.getCurrentPosition();
    }

    private void getAudioListFromMediaDatabase() {

        ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "휴대폰의 음악을 불러오는 중입니다!");

        ContentResolver mResolver = getContentResolver();

        String[] projection = new String[]{
                MediaStore.Audio.Media.IS_MUSIC,
                MediaStore.Audio.Media.IS_ALARM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA
        };

        Cursor mCursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.Media.TITLE + " ASC");


        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                if (mCursor.getInt(0) != 0 || mCursor.getInt(1) != 0) {
                    Mp3Data mp3Data = new Mp3Data();
                    mp3Data.setTitle(mCursor.getString(3));
                    mp3Data.setArtist(mCursor.getString(4));
                    mp3Data.setId(mCursor.getString(5));
                    mp3Data.setDuration(mCursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
                    mp3Data.setDataPath(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
                    mp3Data.setAlbumId(mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
                    mp3ListArray.add(mp3Data);
                }
            }
        }

        pd.dismiss();

        musicSize = mp3ListArray.size();
        Toast.makeText(getApplicationContext(), musicSize + "개의 음악 불러오기 성공!", Toast.LENGTH_LONG).show();
    }

    public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {


            case 111:

                speed = 0.5f;
                break;

            case 112:
                speed = 0.75f;
                break;

            case 113:
                speed = 1.0f;
                break;

            case 114:
                speed = 1.25f;
                break;

            case 115:
                speed = 1.5f;
                break;

            default:

                break;

        }

    }
}