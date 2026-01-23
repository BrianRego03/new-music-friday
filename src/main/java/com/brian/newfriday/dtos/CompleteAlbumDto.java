package com.brian.newfriday.dtos;

import com.brian.newfriday.entity.Album;

import java.util.List;

public class CompleteAlbumDto {
    private List<Album> AlbumList;
    private List<List<String>> artistIds;
    private String currentArtistId;

    public CompleteAlbumDto(List<Album> AlbumList, List<List<String>> artistIds, String currentArtistId) {
        this.AlbumList = AlbumList;
        this.artistIds = artistIds;
        this.currentArtistId = currentArtistId;
    }

    public List<Album> getAlbumList() {
        return AlbumList;
    }
    public void setAlbumList(List<Album> AlbumList) {
        this.AlbumList = AlbumList;
    }
    public List<List<String>> getArtistIds() {
        return artistIds;
    }
    public void setArtistIds(List<List<String>> artistIds) {
        this.artistIds = artistIds;
    }
    public String getCurrentArtistId() {
        return currentArtistId;
    }
    public void setCurrentArtistId(String currentArtistId) {
        this.currentArtistId = currentArtistId;
    }

}
