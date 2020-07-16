package com.aistle.MusicWorld.controller;

import com.aistle.MusicWorld.exception.ResourceNotFoundException;
import com.aistle.MusicWorld.model.Artist;
import com.aistle.MusicWorld.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mohammed Sibgathulla
 */
@RestController
@RequestMapping("/")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;

    /**
     * To save new artist we need to pass the Artist object through json to invoke the api.
     * @param artist
     * @return
     */
    @PostMapping("artists")
    public ResponseEntity<?> createArtist(@Valid @RequestBody Artist artist) {

        Artist result = artistRepository.save(artist);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/artists/{username}")
                .buildAndExpand(result.getArtistName()).toUri();

        return ResponseEntity.created(location).body("Artist saved successfully");
    }

    /**
     * To update artist we need to path artistId as path variable and Artist json object to be updated.
     * @param artistId
     * @param artistReq
     * @return
     */
    @PutMapping("/artists/{artistId}")
    public Artist updateArtist(@PathVariable (value = "artistId") Long artistId, @Valid @RequestBody Artist artistReq) {
        return artistRepository.findById(artistId).map(artist -> {
            artist.setArtistName(artistReq.getArtistName());
           return  artistRepository.save(artist);
        }).orElseThrow(() -> new ResourceNotFoundException("ArtistId " + artistId + " not found"));
    }

    /**
     * Once this api is invoked all the artist in the database are returned.
     * @return
     */
    @GetMapping("artists")
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    /**
     * Pass the string i.e part of the artist name as path variable in the url then the artists
     * are filtered and returned.
     * @param filter
     * @return
     */
    @GetMapping("artists/{filter}")
    public  List<Artist> getFilteredArtistsByName(@PathVariable (value = "filter") String filter) {
        return artistRepository.findAll().stream().filter(artist -> artist.getArtistName()
                .contains(filter))
                .collect(Collectors.toList());
    }


    /**
     * To sort artists by name in asc or desc, pass the string "asc" or "desc" as path variable in the url.
     * The sorted list is returned in Ascending or Descending order depending on the input.
     * @param sort
     * @return
     */
    @GetMapping("artists/sort={sort}")
    public  List<Artist> sortArtistsByName(@PathVariable (value = "sort") String sort) {
        List<Artist> artists = artistRepository.findAll();
        if(sort.equalsIgnoreCase("asc")){
            artists.sort((Artist a1, Artist a2)->a1.getArtistName().compareTo(a2.getArtistName()));
        } else if(sort.equalsIgnoreCase("desc")){
            artists.sort((Artist a1, Artist a2)->a2.getArtistName().compareTo(a1.getArtistName()));
        }
        return artists;

    }

    /**
     * To list all the artists with paging.
     * @param pageable
     * @return
     */
    @GetMapping("artists/paging")
    public Page<Artist> getAllCommentsByPostId(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }
}
