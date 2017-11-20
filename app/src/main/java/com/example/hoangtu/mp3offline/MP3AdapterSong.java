package com.example.hoangtu.mp3offline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HoangTu on 27/09/2017.
 */

public class MP3AdapterSong extends ArrayAdapter<ItemSong>  {
    private ArrayList<ItemSong> arrSong;
    private LayoutInflater inflater;
    private MP3Player mp3Player;

    public static final String ACTION_PREV = "PREW";
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PAUSE = "PAUSE";
    public static final String ACTION_CLOSE = "CLOSE";

    public MP3AdapterSong(@NonNull Context context, @NonNull ArrayList<ItemSong> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        inflater=LayoutInflater.from(context);
        arrSong=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (v==null){
            viewHolder = new ViewHolder();
            v= inflater.inflate(R.layout.item,parent,false);
            viewHolder.tvActar=(TextView) v.findViewById(R.id.tvActar);
            viewHolder.tvSize=(TextView) v.findViewById(R.id.tvSize);
           viewHolder.tvTitle=(TextView) v.findViewById(R.id.tvTitle);
            viewHolder.tvGiay=(TextView) v.findViewById(R.id.tvGiay);
            viewHolder.tvAlbum=(TextView) v.findViewById(R.id.tvAlbum);

//            viewHolder.imvNext  = v.findViewById(R.id.imvNext);
//            viewHolder.imvPlay = v.findViewById(R.id.imvPlay);
//            viewHolder.imvPrew = v.findViewById(R.id.imvPrevious);

            v.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) v.getTag();
        }
        final ItemSong song = arrSong.get(position);
        viewHolder.tvTitle.setText(song.getTitle());
        viewHolder.tvSize.setText(song.getSize());
        viewHolder.tvGiay.setText(song.getDuration());
        viewHolder.tvAlbum.setText(song.getData());
        viewHolder.tvActar.setText(song.getArtist());
        return v;
    }
    class ViewHolder{
        TextView tvTitle;
        TextView tvSize;
        TextView tvGiay;
        TextView tvActar;
        TextView tvAlbum;

//        ImageView imvNext;
//        ImageView imvPlay;
//        ImageView imvPrew;
    }
}
