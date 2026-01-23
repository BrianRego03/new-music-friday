package com.brian.newfriday.mappers;

import com.brian.newfriday.dtos.SpotifyArtistDto;
import com.brian.newfriday.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ArtistMapper {
    @Mapping(source = "spotifyID", target = "spotifyID")
    Artist toArtist(SpotifyArtistDto spotifyArtistDto);

    @Mapping(source = "spotifyID", target = "spotifyID")
    SpotifyArtistDto toSpotifyDto(Artist artist);
}
