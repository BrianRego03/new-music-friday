package com.brian.newfriday.service;

import com.brian.newfriday.repository.ArtistRepository;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    private final ArtistRepository artistRepository;

    public AlbumService(ArtistRepository artistRepository){
        this.artistRepository=artistRepository;
    }
}
