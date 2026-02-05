package com.brian.newfriday.service;

import com.brian.newfriday.client.SpotifyClient;
import com.brian.newfriday.dtos.ArtistLatestDto;
import com.brian.newfriday.repository.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final Logger log = LoggerFactory.getLogger(ArtistRefreshService.class);

    public ArtistRefreshService(ArtistRepository artistRepository, SpotifyClient spotifyClient, ArtistService artistService) {
        this.artistRepository = artistRepository;
        this.spotifyClient = spotifyClient;
        this.artistService = artistService;
    }

    @Async("refreshExecutor")
    public CompletableFuture<Void> refreshArtistData(){
        if(!running.compareAndSet(false, true)){
            log.info("Artist data refresh is already running. Exiting this request.");
            return CompletableFuture.completedFuture(null);
        }

        try{
            log.info("Starting artist data refresh");
            List<ArtistLatestDto> latestArtists = artistRepository.getLatestArtistsWithAlbums();
            for(ArtistLatestDto artistLatestDto : latestArtists){
                while(paused.get()){
                    Thread.sleep(1000);
                }
                String artistID = artistLatestDto.getArtistId();
                String latestAlbumID = artistLatestDto.getLatestAlbumId();
                String apiLatestAlbumID = spotifyClient.getLatestAlbumByArtistSpotifyId(artistID);
                Thread.sleep(1*3*1000);//3 sec break
                if(apiLatestAlbumID==null || apiLatestAlbumID.equals(latestAlbumID)){
                    continue;
                }
                artistService.getCompleteArtist(artistID);
                log.info("Refreshed artist data for artist ID: {}",artistID);

            }
            return CompletableFuture.completedFuture(null);
        }catch (Exception e){
            log.error("Refresh failed", e);
            return CompletableFuture.completedFuture(null);
        }finally {
            log.info("Artist data refresh completed");
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
