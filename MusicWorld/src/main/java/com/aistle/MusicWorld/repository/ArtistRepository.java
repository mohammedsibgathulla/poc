package com.aistle.MusicWorld.repository;

import com.aistle.MusicWorld.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Created by Mohammed Sibgathulla
 */
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findById(Long id);
    Page<Artist> findAll(Pageable pageable);
}
