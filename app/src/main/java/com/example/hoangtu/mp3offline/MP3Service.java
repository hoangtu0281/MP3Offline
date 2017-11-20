package com.example.hoangtu.mp3offline;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Created by HoangTu on 01/11/2017.
 */

public class MP3Service extends Service {
    private MP3Data mp3Data;
    private MP3Player mp3Player;
    private ArrayList<ItemSong> arrSong = new ArrayList<>();

    public static final String ACTION_PREV = "PREW";
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PAUSE = "PAUSE";
    public static final String ACTION_CLOSE = "CLOSE";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MP3Binder();// đổi lại
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(ACTION_PREV);
        intentFilter.addAction(ACTION_NEXT);
        intentFilter.addAction(ACTION_PAUSE);
        intentFilter.addAction(ACTION_CLOSE);

        registerReceiver(receiver,intentFilter);

        mp3Data = new MP3Data(this);
        arrSong.addAll(mp3Data.readData());
        mp3Player = new MP3Player(this,arrSong){
            @Override
            public void create(int index) {
                super.create(index);
               // NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);// là đối tượng quản lý notifind trên actionbar
                Notification.Builder builder = new Notification.Builder(MP3Service.this);
                builder.setSmallIcon(R.drawable.boy);
                builder.setOngoing(true);
                //builder.setTicker("Play Music");
                //builder.setOngoing(true);// mặc định của nó là flase, để là true thì sẽ ko mất đc nữa
                //builder.setContentTitle("MP3 Music");
                //builder.setContentText(arrSong.get(index).getTitle());// lấy tên bài hát
               // manager.notify(102948,builder.build());
                RemoteViews  remoteView = new RemoteViews(getPackageName(),R.layout.layout_thongbao);// khởi tạo thông báo mới, cần 2 thông số
                remoteView.setTextViewText(R.id.tvName,arrSong.get(index).getTitle());// lấy tên bài hát
                builder.setContent(remoteView);
                // thay thế câu lệnh  manager.notify(102948,builder.build());

                Intent itPrev = new Intent(ACTION_PREV);
                Intent itNext = new Intent(ACTION_NEXT);
                Intent itPause = new Intent(ACTION_PAUSE);
                Intent itClose = new Intent(ACTION_CLOSE);

                PendingIntent clickImPrev = PendingIntent.getBroadcast(MP3Service.this,0,itPrev,0);
                PendingIntent clickImNext = PendingIntent.getBroadcast(MP3Service.this,0,itNext,0);
                PendingIntent clickImPause = PendingIntent.getBroadcast(MP3Service.this,0,itPause,0);
                PendingIntent clickImClose = PendingIntent.getBroadcast(MP3Service.this,0,itClose,0);

                remoteView.setOnClickPendingIntent(R.id.imvPrevious,clickImPrev);
                remoteView.setOnClickPendingIntent(R.id.imvPlay,clickImPause);
                remoteView.setOnClickPendingIntent(R.id.imClose,clickImClose);
                remoteView.setOnClickPendingIntent(R.id.imvNext,clickImNext);

                Notification mNotification = new Notification.Builder(MP3Service.this).build();
                MP3Service.this.startForeground(12234234,builder.build());

                mNotification.bigContentView = remoteView;
                //MP3Service.this.startForeground(22331123,mNotification);// thay thế câu lệnh  manager.notify(102948,builder.build());

            }
        };
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        unregisterReceiver(receiver);
    }

    class MP3Binder extends Binder{
        public MP3Service getMP3Service(){
         return MP3Service.this;
        }
    }

    public MP3Player getMp3Player() {
        return mp3Player;
    }

    public ArrayList<ItemSong> getArrSong() {
        return arrSong;
    }
//    private void create(int index){
//        mp3Player.create(index);
//        // đặt id để ko bị trùng id nào trên notify
//    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case ACTION_PREV:
                mp3Player.changeSong(-1);
                break;
            case ACTION_NEXT:
                mp3Player.changeSong(1);
                break;
            case ACTION_PAUSE:
                if (mp3Player.mp3IsPlaying()){
                    mp3Player.pause();
                }
                break;
            case ACTION_CLOSE:
                stopSelf();
                break;
        }
    }
};
}
