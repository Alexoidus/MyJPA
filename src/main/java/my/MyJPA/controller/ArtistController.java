package my.MyJPA.controller;

import lombok.extern.slf4j.Slf4j;
import my.MyJPA.dto.ArtistDTO;
import my.MyJPA.service.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("artist/{id}")
    public String getArtist(@PathVariable Long id) {
        ArtistDTO artistDTO = artistService.getArtist(id);
        return String.format("Hello %s!", artistDTO.toString());
    }

}