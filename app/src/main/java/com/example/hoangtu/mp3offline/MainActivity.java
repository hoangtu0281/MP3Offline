package com.example.hoangtu.mp3offline;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener {

    //private ArrayList<ItemSong> arrSong = new ArrayList<>();// dang sách bài hát
    private ListView lvSong;
    // private MP3Data mp3Data;
    private MP3AdapterSong adapterSong;
    private MP3Player mp3Player;
    private SeekBar sbTime;
    private  MP3Service mp3Service;



    private static final String[] PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final int GRANTED = PackageManager.PERMISSION_GRANTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            for (String p : PERMISSION){
                if (checkSelfPermission(p)!=GRANTED){
                    requestPermissions(PERMISSION,0);
                    return;
                }
            }
        }
        Intent intent = new Intent(this,MP3Service.class);
        startService(intent);// giúp cho duy trì khi bài hát chết
        bindService(intent,connection, Context.BIND_ABOVE_CLIENT);
        //lvSong.setOnItemClickListener(this);// click bvafo bài nào thì hát bài đó
//        initView();
        //khởi tạo seekber cuối cùng;
        //asyncTask.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resule : grantResults){
            if (resule!=GRANTED){
                finish();
                return;
            }
        }
       // initView();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MP3Service.MP3Binder binder = (MP3Service.MP3Binder) iBinder;
            mp3Service = binder.getMP3Service();
            initView();
            //khởi tạo seekber cuối cùng;
            asyncTask.execute();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private void initView() {
        adapterSong = new MP3AdapterSong(this,mp3Service.getArrSong());
        lvSong = (ListView) findViewById(R.id.lvSong);


        lvSong.setAdapter(adapterSong);
        sbTime = (SeekBar)findViewById(R.id.sbTime);
        lvSong.setOnItemClickListener(this);
        sbTime.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mp3Service.getMp3Player().create(i);
        mp3Service.getMp3Player().start();
    }
    private AsyncTask<Void,Void,Void> asyncTask = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            sbTime.setMax(mp3Service.getMp3Player().getDuration());// giới hạn là bài hát
            sbTime.setProgress(mp3Service.getMp3Player().getCurrentPosition());// tiến trình là time hiện tại
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b==true){
            mp3Service.getMp3Player().seek(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
