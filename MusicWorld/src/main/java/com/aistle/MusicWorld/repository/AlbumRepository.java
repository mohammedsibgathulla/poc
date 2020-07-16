package com.aistle.MusicWorld.repository;

import com.aistle.MusicWorld.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Mohammed Sibgathulla
 */
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAllByArtistId(Long id);

}
