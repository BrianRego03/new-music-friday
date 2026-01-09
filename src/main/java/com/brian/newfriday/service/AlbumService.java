package com.brian.newfriday.service;

import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Record;
import com.brian.newfriday.repository.AlbumRepository;
import com.brian.newfriday.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository){
        this.albumRepository=albumRepository;
    }

    public Album registerAlbum(String title, String spotifyID, String imgSmall, String imgMedium, String imgLarge,
                               int trackLength, LocalDate releaseDate, Record albumType){
        Album newAlbum = new Album(title, spotifyID, imgSmall, imgMedium, imgLarge,
                trackLength, releaseDate, albumType);
        return albumRepository.save(newAlbum);
    }
}
