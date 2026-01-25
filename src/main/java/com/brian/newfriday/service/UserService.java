package com.brian.newfriday.service;

import com.brian.newfriday.dtos.SpotifyArtistDto;
import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.entity.User;
import com.brian.newfriday.mappers.ArtistMapper;
import com.brian.newfriday.repository.AlbumRepository;
import com.brian.newfriday.repository.ArtistRepository;
import com.brian.newfriday.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;
    private  final ArtistMapper artistMapper;
    private final AlbumRepository albumRepository;

    public UserService(UserRepository userRepository, ArtistRepository artistRepository,
                       ArtistService artistService, ArtistMapper artistMapper, AlbumRepository albumRepository) {
        this.userRepository=userRepository;
        this.artistRepository=artistRepository;
        this.artistService=artistService;
        this.artistMapper=artistMapper;
        this.albumRepository=albumRepository;
    }

    @Transactional
    public User registerUser(String name, String email, String password){

        User usernew = new User(name,email,password);
        return userRepository.save(usernew);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(String sort){
        if (sort == null || sort.isBlank()) {
            return userRepository.findAll();
        }
        return userRepository.findAll(Sort.by(sort));
    }

    @Transactional
    public String DeleteUser(int id){
        userRepository.deleteById(id);
        return "Success";
    }

    @Transactional
    public void addArtistToUser(int userId, String artistId){

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Artist artist = artistService.getCompleteArtist(artistId);
        user.AddArtist(artist);
        userRepository.save(user);

    }

    public List<SpotifyArtistDto> getUserArtistsDto(int userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Artist> artists = user.getArtistList();
        return artists.stream().map(artistMapper::toSpotifyDto).toList();
    }

    @Transactional
    public void removeArtistFromUser(int userId, String artistId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Artist artist = artistRepository.findBySpotifyID(artistId);
        if(artist == null){
            throw new IllegalArgumentException("Artist not found");
        }
        user.removeArtist(artist);
        userRepository.save(user);
    }
    

}
