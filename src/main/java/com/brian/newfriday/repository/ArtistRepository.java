package com.brian.newfriday.repository;

import com.brian.newfriday.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist,Integer> {
    Artist findBySpotifyID(String spotifyId);
}
