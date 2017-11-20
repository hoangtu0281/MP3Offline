package com.example.hoangtu.mp3offline;

/**
 * Created by HoangTu on 27/09/2017.
 */

class ItemSong {
    private String data;
    private String title;
    private String duration;
    private String size;
    private String artist;
    private String album;

    public String getAlbum() {
        return album;
    }

    public String getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getSize() {
        return size;
    }

    public String getArtist() {
        return artist;
    }

    public ItemSong(String data, String title, String duration, String size, String artist) {
        this.data=data;
        this.artist=artist;
        this.duration=duration;
        this.size=size;
        this.title=title;

    }
}
