package com.brian.newfriday.controller;

import com.brian.newfriday.repository.ArtistRepository;
import com.brian.newfriday.service.ArtistService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController {
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;

    public ArtistController(ArtistRepository artistRepository, ArtistService artistService) {
        this.artistRepository = artistRepository;
        this.artistService = artistService;
    }
}
