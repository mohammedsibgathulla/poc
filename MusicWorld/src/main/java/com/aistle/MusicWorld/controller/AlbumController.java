package com.aistle.MusicWorld.controller;

import com.aistle.MusicWorld.exception.ResourceNotFoundException;
import com.aistle.MusicWorld.model.Album;
import com.aistle.MusicWorld.model.Artist;
import com.aistle.MusicWorld.repository.AlbumRepository;
import com.aistle.MusicWorld.repository.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mohammed Sibgathulla
 */


@RestController
@RequestMapping("/")
public class AlbumController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;

    /**
     * To save a new Album pass the artistId as path variable in the url and Album as json object.
     * @param artistId
     * @param album
     * @return
     */
    @PostMapping("artists/{artistId}/albums")
    public Album createAlbum(@PathVariable (value = "artistId") Long artistId, @Valid @RequestBody Album album) {
        return  artistRepository.findById(artistId).map(artist -> {
            album.setArtist(artist);
            return albumRepository.save(album);
        }).orElseThrow(() -> new ResourceNotFoundException("ArtistId " + artistId + " not found"));
    }

    /**
     * To update the existing album. Pass the artistId & albumId as path variables and Album json object.
     * @param artistId
     * @param album
     * @param albumId
     * @return
     */
    @PutMapping("artists/{artistId}/albums/{albumId}")
    public Album updateAlbum(@PathVariable (value = "artistId") Long artistId, @Valid @RequestBody Album album,
                             @PathVariable (value = "albumId") Long albumId) {
        return  albumRepository.findById(albumId).map(fetchedAlbum -> {
            fetchedAlbum.setTitle(album.getTitle());
            fetchedAlbum.setYearOfRelease(album.getYearOfRelease());
            fetchedAlbum.setGenres(album.getGenres());
            artistRepository.findById(artistId).map(artist -> {
                 fetchedAlbum.setArtist(artist);
                return null;
            });
            return albumRepository.save(fetchedAlbum);
        }).orElseThrow(() -> new ResourceNotFoundException("ArtistId " + artistId + " not found"));
    }

    /**
     * To list the Albums present in the database.
     * @param artistId
     * @return
     */
    @GetMapping("artists/{artistId}/albums")
    public List<Album> getAlbumsOfArtist(@PathVariable (value = "artistId") Long artistId) {
        return albumRepository.findAllByArtistId(artistId);
    }

    /**
     * To sort albums by title in asc or desc, pass the string "asc" or "desc" as path variable in the url.
     * The sorted list is returned in Ascending or Descending order depending on the input.
     * @param sort
     * @return
     */
    @GetMapping("artists/{artistId}/albums/sort={sort}")
    public  List<Album> sortAlbumsByName(@PathVariable (value = "sort") String sort) {
        List<Album> albums = albumRepository.findAll();
        if(sort.equalsIgnoreCase("asc")){
            albums.sort((Album a1, Album a2)->a1.getTitle().compareTo(a2.getTitle()));
        } else if(sort.equalsIgnoreCase("desc")){
            albums.sort((Album a1, Album a2)->a2.getTitle().compareTo(a1.getTitle()));
        }
        return albums;

    }

    /**
     * To sort albums by year in asc or desc, pass the string "asc" or "desc" as path variable in the url.
     * The sorted list is returned in Ascending or Descending order depending on the input.
     * @param sort
     * @return
     */
    @GetMapping("artists/{artistId}/albums/sortByYear={sort}")
    public  List<Album> sortAlbumsByReleaseYear(@PathVariable (value = "sort") String sort) {
        List<Album> albums = albumRepository.findAll();
        if(sort.equalsIgnoreCase("asc")){
            albums.sort((Album a1, Album a2)->a1.getYearOfRelease()-a2.getYearOfRelease());
        } else if(sort.equalsIgnoreCase("desc")){
            albums.sort((Album a1, Album a2)->a2.getYearOfRelease()-a1.getYearOfRelease());
        }
        return albums;

    }


    /**
     * To get the details of particular album.
     * @param artistId
     * @param albumId
     * @return
     */
    @GetMapping("artists/{artistId}/albums/{albumId}")
    public Optional<Album> getAlbumDetails(@PathVariable (value = "artistId") Long artistId,
                                        @PathVariable (value = "albumId") Long albumId) {
        return albumRepository.findById(albumId);
    }


    /**
     * To get the albums from the Discogs API.
     */
    @GetMapping("discog/")
    public void getAlbumsFromDiscogs() {
        final String uri = "https://api.discogs.com/database/search?artist=nirvana&per_page=3&page=1&key=WbJGjBnhTBgfvRozrJXL&secret=HwTyNVPFSuUENFRxtnBOeCSlgtFQPgVP";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        LOGGER.info("Details of Artist Nirvana {}", result);
    }

}
