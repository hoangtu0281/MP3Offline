package com.example.hoangtu.mp3offline;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by HoangTu on 27/09/2017.
 */

public class MP3Data {
    private ContentResolver resolver;

    public MP3Data(Context context) {
        resolver = context.getContentResolver();
    }

    public ArrayList<ItemSong> readData(){
        //ContactsContract.Contacts.CONTENT_URI: danh bạ
        //CallLog.CONTENT_URI: cuộc gọi nhớ
        //Telephony.Sms.CONTENT_URI: sms
        //MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        //MediaStore.Audio.Media.INTERNAL_CONTENT_URI
        ArrayList<ItemSong> arr = new ArrayList<>();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);// truyền vòa uri

        // đưa tất cả dữ leieju ra
        // in dữ liêu tất cả các cột ra
        int indexData = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
        int indexSize = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);

        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            //in tất cả ra, duyệt từng dòng một
//            for ( int i = 0; i <cursor.getColumnCount();i++){// láy ra từng cột{
//                Log.e(cursor.getColumnName(i),cursor.getString(i)+" ");
//            }

            String data = cursor.getString(indexData);
            int d = cursor.getInt(indexDuration);
            int s = (d/1000);
            int m  = s%60;
            s = s/60;

            String duration = m+ ":" +s;
            String size = cursor.getInt(indexSize)/1024/1024+" MB";
            String artist = cursor.getString(indexArtist);
            String album = cursor.getColumnName(indexAlbum);
            String title = cursor.getString(indexTitle);

            ItemSong itemSong = new ItemSong(data,title,duration,size,artist);
            arr.add(itemSong);

            cursor.moveToNext();
        }
        return arr;
    }
}
