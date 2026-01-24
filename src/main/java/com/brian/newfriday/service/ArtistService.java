package com.brian.newfriday.service;

import com.brian.newfriday.client.SpotifyClient;
import com.brian.newfriday.dtos.CompleteAlbumDto;
import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.repository.AlbumRepository;
import com.brian.newfriday.repository.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SpotifyClient spotifyClient;

    public ArtistService(ArtistRepository artistRepository, AlbumRepository albumRepository,
                         SpotifyClient spotifyClient){
        this.artistRepository=artistRepository;
        this.albumRepository=albumRepository;
        this.spotifyClient=spotifyClient;
    }

    @Transactional
    public Artist registerArtist(String name, String spotifyID, String imgSmall, String imgMedium, String imgLarge){
        Artist newArtist = new Artist(name, spotifyID, imgSmall, imgMedium, imgLarge);
        return artistRepository.save(newArtist);
    }

    public Artist getArtistById(int id){
        return artistRepository.findById(id).orElse(null);
    }

    public List<Artist> getAllArtists(String sort){
        return artistRepository.findAll(Sort.by(sort));
    }

    @Transactional
    public String DeleteArtist(int id){
        artistRepository.deleteById(id);
        return "Success";
    }

    @Transactional
    public Artist getArtistBySpotifyID(String spotifyID){
        Artist artist = artistRepository.findBySpotifyID(spotifyID);
        if(artist != null){
            return artist;
        }
        artist = spotifyClient.getArtistFromSpotify(spotifyID);
        if(artist == null){
            throw new EntityNotFoundException("Artist not found with Spotify ID: " + spotifyID);
        }
        artistRepository.save(artist);
        return artist;
    }

    public boolean existsBySpotifyID(String spotifyID){
        return artistRepository.existsBySpotifyID(spotifyID);
    }

    @Transactional
    public Artist getCompleteArtist(String spotifyID){
        Artist currentArtist = getArtistBySpotifyID(spotifyID);
        if(!(currentArtist.getAlbumList().size()<5)){
            return currentArtist;
        }
        CompleteAlbumDto completeAlbumDto = spotifyClient.getAlbumsByArtistSpotifyId(spotifyID);
        List<Album> finalAlbumList = new ArrayList<>();

        for(int i=0;i<completeAlbumDto.getAlbumList().size();i++){
            Album currentAlbum = completeAlbumDto.getAlbumList().get(i);
            if(albumRepository.existsBySpotifyID(currentAlbum.getSpotifyID())){
                continue;
            }
            currentAlbum = albumRepository.save(completeAlbumDto.getAlbumList().get(i));
            currentAlbum.addArtist(currentArtist);
            if(!completeAlbumDto.getArtistIds().get(i).isEmpty()){
                List<String> extraArtistList = completeAlbumDto.getArtistIds().get(i);
                for(int j=0;j<extraArtistList.size();j++){
                    Artist extraArtist = getArtistBySpotifyID(extraArtistList.get(j));
                    currentAlbum.addArtist(extraArtist);
                }
            }
            finalAlbumList.add(currentAlbum);
        }
        return currentArtist;
    }



    @Transactional
    public void addAlbumBySpotifyID(String artistId, String albumId){
        Artist artist = artistRepository.findBySpotifyID(artistId);
        Album album = albumRepository.findBySpotifyID(albumId);
        if(artist == null){
            throw new EntityNotFoundException("Artist not found with Spotify ID: " + artistId);
        }
        if(album == null){
            throw new EntityNotFoundException("Album not found with Spotify ID: " + albumId);
        }

        if(artist.getAlbumSet().contains(album)){
            return;
        }
        artist.addAlbum(album);
    }

    @Transactional
    public void AddAlbumById(int artistId, int albumId){
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with ID: " + artistId));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found with ID: " + albumId));

        if(artist.getAlbumSet().contains(album)){
            return;
        }
        artist.addAlbum(album);
    }

    @Transactional
    public void removeAlbumById(int artistId, int albumId){
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with ID: " + artistId));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found with ID: " + albumId));

        if(!artist.getAlbumSet().contains(album)){
            return;
        }
        artist.removeAlbum(album);
    }

    @Transactional
    public void removeAlbumBySpotifyID(String artistId, String albumId){
        Artist artist = artistRepository.findBySpotifyID(artistId);
        Album album = albumRepository.findBySpotifyID(albumId);
        if(artist == null){
            throw new EntityNotFoundException("Artist not found with Spotify ID: " + artistId);
        }
        if(album == null){
            throw new EntityNotFoundException("Album not found with Spotify ID: " + albumId);
        }

        if(!artist.getAlbumSet().contains(album)){
            return;
        }
        artist.removeAlbum(album);
    }

    @Transactional
    public void removeAllAlbums(int artistId){
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with ID: " + artistId));
        artist.getAlbumSet().clear();
    }

    @Transactional
    public void removeAllAlbumsBySpotifyID(String artistId){
        Artist artist = artistRepository.findBySpotifyID(artistId);
        if(artist == null){
            throw new EntityNotFoundException("Artist not found with Spotify ID: " + artistId);
        }
        artist.getAlbumSet().clear();
    }

    public List<Album> getAlbumsByArtistId(int artistId){
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with ID: " + artistId));
        return List.copyOf(artist.getAlbumSet());
    }

    public List<Album> getAlbumsByArtistSpotifyID(String artistId){
        Artist artist = artistRepository.findBySpotifyID(artistId);
        if(artist == null){
            throw new EntityNotFoundException("Artist not found with Spotify ID: " + artistId);
        }
        return artist.getAlbumList();
    }

    @Transactional
    public Page<Album> getAlbumsByArtistSpotifyID(String artistId, LocalDate fromDate,
                                                  LocalDate toDate, Pageable pageable)
    {

        if(!artistRepository.existsBySpotifyID(artistId)){
            throw new EntityNotFoundException("Artist not found with Spotify ID: " + artistId);
        }
        return artistRepository.getAlbumsBySpotifyIDAndReleaseDateBetween(artistId, fromDate,
                toDate, pageable);
    }


}
