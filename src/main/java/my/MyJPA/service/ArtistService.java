package my.MyJPA.service;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import my.MyJPA.dto.ArtistDTO;
import my.MyJPA.exception.ObjectNotFoundException;
import my.MyJPA.logging.Mdc;
import my.MyJPA.mapper.ArtistMapper;
import my.MyJPA.model.Artist;
import my.MyJPA.repository.ArtistRepository;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    public ArtistService(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    @Transactional(readOnly = true)
    public ArtistDTO getArtist(Long id) {
        @Cleanup final var mdc = Mdc.put("app.artist.id", String.valueOf(id));

        log.info("MDC map: {}", MDC.getCopyOfContextMap());

        Optional<Artist> artistOptional = artistRepository.findById(id);


        /*if (artistOptional.isPresent()) {
            Artist artist = artistOptional.get();
            log.info("Found artist: {}", artist);
            artist.setDescription(artist.getDescription());
            //artistRepository.save(artist);
        } else {
            log.info("Artist with id {} not found, creating new", id);

            Artist artist = new Artist();
            artist.setName("Depeche Mode");
            artist.setDescription("English electronic music band formed March 1980 in Basildon, Essex, and named after a popular French fashion magazine. The group's original line-up consisted of Dave Gahan (lead vocals, occasional songwriter since 2005) Martin Gore (keyboards, guitar, vocals, chief songwriter after 1981) Andy Fletcher (keyboards) and Vince Clarke (keyboards, and chief songwriter from 1980 until 1981).");

            artistRepository.save(artist);

            log.info("Created artist: {}", artist);
        }*/

        log.info("End of method");

        return artistMapper.entityToDto(artistOptional.orElseThrow(ObjectNotFoundException::new));
    }

    public void createArtist(ArtistDTO artistDTO) {
        artistRepository.save(null);
    }

    public void updateArtist(ArtistDTO artistDTO) {
        artistRepository.save(null);
    }

}
