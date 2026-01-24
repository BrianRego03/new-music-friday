package com.brian.newfriday.mappers;

import com.brian.newfriday.dtos.SpotifyAlbumDto;
import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlbumMapper {

    @Mapping(source = "albumType", target = "albumType", qualifiedByName = "mapAlbumType")
    @Mapping(source = "spotifyID", target = "spotifyID")
    Album toAlbumEntity(SpotifyAlbumDto albumDto);

    @Mapping(source = "albumType", target = "albumType", qualifiedByName = "mapAlbumString")
    @Mapping(source = "spotifyID", target = "spotifyID")
    SpotifyAlbumDto toDto(Album album);

    @Named("mapAlbumType")
    default Record mapAlbumType(String albumType) {
        if (albumType == null) {
            return null;
        }
        switch (albumType.toLowerCase()) {
            case "album":
                return Record.ALBUM;
            case "single":
                return Record.SINGLE;
            default:
                return null;
        }
    }

    @Named("mapAlbumString")
    default String mapAlbumString(Record albumType) {
        if (albumType == null) {
            return null;
        }
        switch (albumType) {
            case ALBUM:
                return "album";
            case SINGLE:
                return "single";
            default:
                return null;
        }
    }


}
