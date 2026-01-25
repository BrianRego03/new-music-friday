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

    @Query("""
            SELECT DISTINCT a
            FROM Album a 
            JOIN a.artistSet artist
            WHERE artist.spotifyID IN :artistIDs
                AND a.releaseDate = (
                            SELECT MAX(a2.releaseDate)
                            FROM Album a2
                            JOIN a2.artistSet artist2
                            WHERE artist2.spotifyID=artist.spotifyID
                            )             
            """)
    List<Album> bulkFindLatestAlbums(@Param("artistIDs") List<String> artistIDs);

    @Query("""
            SELECT DISTINCT a
            FROM Album a
            JOIN a.artistSet artist
            JOIN artist.userSet u
            WHERE u.id = :userID
            AND artist.spotifyID IN :artistIDs
                AND (:startDate IS NULL OR a.releaseDate >= :startDate)
                AND (:endDate   IS NULL OR a.releaseDate <= :endDate)
            ORDER BY a.releaseDate DESC
            """)
    List<Album> bulkFindFavouriteAlbums(@Param("artistIDs") List<String> artistIDs, @Param("userID") Integer userID,
                                              @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
