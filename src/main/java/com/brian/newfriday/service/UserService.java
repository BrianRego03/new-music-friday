package com.brian.newfriday.service;

import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.entity.User;
import com.brian.newfriday.repository.ArtistRepository;
import com.brian.newfriday.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;

    public UserService(UserRepository userRepository, ArtistRepository artistRepository,
                       ArtistService artistService){
        this.userRepository=userRepository;
        this.artistRepository=artistRepository;
        this.artistService=artistService;
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
//        Artist artist = artistRepository.findBySpotifyID(artistId);
        Artist artist = artistService.getCompleteArtist(artistId);
//        if (artist == null) {
//
//        }
        user.AddArtist(artist);
        userRepository.save(user);
    }

}
