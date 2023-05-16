package my.MyJPA.mapper;

import my.MyJPA.dto.ArtistDTO;
import my.MyJPA.model.Artist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    Artist dtoToEntity(ArtistDTO artistDTO);

    ArtistDTO entityToDto(Artist artist);

}
