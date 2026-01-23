package com.brian.newfriday.mappers;

import com.brian.newfriday.dtos.SpotifyArtistDto;
import com.brian.newfriday.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ArtistMapper {
    Artist toArtist(SpotifyArtistDto spotifyArtistDto);
    SpotifyArtistDto toSpotifyDto(Artist artist);
}
