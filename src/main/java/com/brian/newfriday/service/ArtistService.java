package com.brian.newfriday.service;

import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository){
        this.artistRepository=artistRepository;
    }

    @Transactional
    public Artist registerArtist(String name, String spotifyID, String imgSmall, String imgMedium, String imgLarge){
        Artist newArtist = new Artist(name, spotifyID, imgSmall, imgMedium, imgLarge);
        return artistRepository.save(newArtist);
    }


}
