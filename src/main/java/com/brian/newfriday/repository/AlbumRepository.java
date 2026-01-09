package com.brian.newfriday.repository;

import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    Album findBySpotifyID(String spotifyId);
    List<Album> findByAlbumType(Record albumType);

    List<Album> findByArtistSet_Id(int artistId);
    List<Album> findByArtistSet_IdAndAlbumType(int artistId, Record albumType);
    List<Album> findByArtistSet_SpotifyID(String spotifyId);
    List<Album> findByArtistSet_SpotifyIDAndAlbumType(String spotifyId, Record albumType);
}
