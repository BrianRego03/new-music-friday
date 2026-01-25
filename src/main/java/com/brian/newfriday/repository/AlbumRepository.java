package com.brian.newfriday.repository;

import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    Album findBySpotifyID(String spotifyId);
    List<Album> findByAlbumType(Record albumType);

    List<Album> findByArtistSet_Id(int artistId);
    List<Album> findByArtistSet_IdAndAlbumType(int artistId, Record albumType);
    List<Album> findByArtistSet_SpotifyID(String spotifyId);
    List<Album> findByArtistSet_SpotifyIDAndAlbumType(String spotifyId, Record albumType);

    Boolean existsBySpotifyID(String spotifyId);

    Optional<Album> findTopByArtistSet_SpotifyIDOrderByReleaseDateDesc(String spotifyId);


}
