package com.euphorbia.musicquiz;

public class Mp3Data {

    String id;
    String Title;
    String Artist;
    int Duration; // 재생시간
    String DataPath; // 실제 데이터 위치
    boolean checkBoxState;
    Long albumId;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getDataPath() {
        return DataPath;
    }

    public void setDataPath(String dataPath) {
        DataPath = dataPath;
    }

    public boolean isCheckBoxState() {
        return checkBoxState;
    }

    public void setCheckBoxState(boolean checkBoxState) {
        this.checkBoxState = checkBoxState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }
}
