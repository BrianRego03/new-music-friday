package com.brian.newfriday.service;

import com.brian.newfriday.client.SpotifyClient;
import com.brian.newfriday.dtos.ArtistLatestDto;
import com.brian.newfriday.repository.ArtistRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ArtistRefreshService {
    private final ArtistRepository artistRepository;
    private final SpotifyClient spotifyClient;
    private final ArtistService artistService;

    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean running = new AtomicBoolean(false);

    public ArtistRefreshService(ArtistRepository artistRepository, SpotifyClient spotifyClient, ArtistService artistService) {
        this.artistRepository = artistRepository;
        this.spotifyClient = spotifyClient;
        this.artistService = artistService;
    }

    @Async("refreshExecutor")
    public CompletableFuture<Void> refreshArtistData(){
        if(!running.compareAndSet(false, true)){
            return CompletableFuture.completedFuture(null);
        }

        try{
            List<ArtistLatestDto> latestArtists = artistRepository.getLatestArtistsWithAlbums();
            System.out.println("Listed successfully");
            for(ArtistLatestDto artistLatestDto : latestArtists){
                while(paused.get()){
                    Thread.sleep(1000);
                }
                String artistID = artistLatestDto.getArtistId();
                System.out.println("refreshing Artist ID: " + artistID);
                String latestAlbumID = artistLatestDto.getLatestAlbumId();
                String apiLatestAlbumID = spotifyClient.getLatestAlbumByArtistSpotifyId(artistID);
                System.out.println("Not api rated yet, taking a break");
                Thread.sleep(1*3*1000);//3 sec break
                if(apiLatestAlbumID==null || apiLatestAlbumID.equals(latestAlbumID)){
                    continue;
                }
                artistService.getCompleteArtist(artistID);
                System.out.println("next api success");

            }
            return CompletableFuture.completedFuture(null);
        }catch (Exception e){
            //log.error("Refresh failed", e);
            System.out.println("Error in refreshArtistData");
            return CompletableFuture.completedFuture(null);
        }finally {
            System.out.println("Artist refresh complete");
            running.set(false);
        }
    }

    public void pause(){
        paused.set(true);
    }

    public void resume(){
        paused.set(false);
    }

    public Boolean isPaused(){
        return paused.get();
    }

    public Boolean isRunning(){
        return running.get();
    }
}
