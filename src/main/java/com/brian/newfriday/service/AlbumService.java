package com.brian.newfriday.service;

import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.entity.Record;
import com.brian.newfriday.repository.AlbumRepository;
import com.brian.newfriday.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository){
        this.albumRepository=albumRepository;
    }

    @Transactional
    public Album registerAlbum(String title, String spotifyID, String imgSmall, String imgMedium, String imgLarge,
                               int trackLength, LocalDate releaseDate, Record albumType){
        Album newAlbum = new Album(title, spotifyID, imgSmall, imgMedium, imgLarge,
                trackLength, releaseDate, albumType);
        return albumRepository.save(newAlbum);
    }

    public Album getAlbumById(int id){
        return albumRepository.findById(id).orElse(null);
    }

    public String DeleteAlbum(int id){
        albumRepository.deleteById(id);
        return "Success";
    }

    public Album getAlbumBySpotifyID(String spotifyID){
        return albumRepository.findBySpotifyID(spotifyID);
    }

    public List<Album> getAllAlbums(){
        return albumRepository.findAll();
    }

    public List<Album> getAlbumsByType(Record albumType){
        return albumRepository.findByAlbumType(albumType);
    }

    public List<Artist> getArtistsByAlbumId(int albumId){
        Album album = albumRepository.findById(albumId).orElse(null);
        if (album != null) {
            return List.copyOf(album.getArtistSet());
        }
        return List.of();
    }
}
