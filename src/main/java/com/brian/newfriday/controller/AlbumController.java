package com.brian.newfriday.controller;

import com.brian.newfriday.mappers.AlbumMapper;
import com.brian.newfriday.repository.AlbumRepository;
import com.brian.newfriday.service.AlbumService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumController {
    private AlbumService albumService;
    private AlbumRepository albumRepository;
    private AlbumMapper albumMapper;

    public AlbumController(AlbumService albumService, AlbumRepository albumRepository,
                           AlbumMapper albumMapper){
        this.albumService = albumService;
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }
}
