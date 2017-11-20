package com.example.hoangtu.mp3offline;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by HoangTu on 27/09/2017.
 */

public class MP3Player implements MediaPlayer.OnCompletionListener {

    private Context context;
    private MediaPlayer player;// dùng để nghe nhạc
    private ArrayList<ItemSong> arrSong;// danh sách hát
    private int currentIndex;// vị trí của bài hát hiện tại

    //private MP3Service mp3Service;

    public MP3Player(Context context,ArrayList<ItemSong> arrSong) {
        this.arrSong = arrSong;
        this.context=context;

    }

    public void create(int index){
        currentIndex=index;
        release();// trước khi play phải giái phóng bài hát hiện tại để hát bài mới
        player=MediaPlayer.create(context, Uri.parse(arrSong.get(currentIndex).getData()));// lấy ra đường dẫn
        if (player!=null){
            // bộ hành vi thực hiện hát xong chưa
            player.setOnCompletionListener(this);//lắng ghe hát xong bài hát hiện tại chưa
        }
    }

    public void start(){
        if (player!=null){
            player.start();// hát tại thời điểm 00; sau
        }
    }
    public void pause(){
        if (player!=null){
            player.pause();
        }
    }
    public void release(){
        if (player!=null){
            player.release();
        }
    }

    public int getDuration(){
        if (player!=null){
            return player.getDuration();
        }
        return 0;
    }

    public int getCurrentPosition(){
        if (player!=null){
            return player.getCurrentPosition();
        }
        return 0;
    }
    public void seek(int position){
        if (player!=null){
            player.seekTo(position);
        }
    }
    public void loop(boolean isLooping){
        if (player!=null){
            player.setLooping(isLooping);
        }
    }
    public boolean mp3IsPlaying(){
        if (player!=null){
            return  player.isPlaying();
        }
        return false;
    }
    public void changeSong(int i){
        currentIndex+=i;
        if (currentIndex>=arrSong.size()){
            currentIndex=0;
        }
        else if (currentIndex<0){
            currentIndex=arrSong.size()-1;
        }
        create(currentIndex);
        start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        changeSong(1);// khi hát xong tư đông cho hát bài tiếp theo
    }

}
