package com.brian.newfriday.repository;

import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ArtistRepository extends JpaRepository<Artist,Integer> {
    Artist findBySpotifyID(String spotifyID);
    Boolean existsBySpotifyID(String spotifyID);

    @Query("""
            SELECT a FROM Album a
            JOIN a.artistSet artist
            WHERE artist.spotifyID = :spotifyID
                AND (:startDate IS NULL OR a.releaseDate >= :startDate)
                AND (:endDate   IS NULL OR a.releaseDate <= :endDate)
            ORDER BY a.releaseDate DESC
            """)
    Page<Album> getAlbumsBySpotifyIDAndReleaseDateBetween(
            @Param("spotifyID") String spotifyID,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("pageable") Pageable pageable);
}
