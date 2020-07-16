package com.aistle.MusicWorld.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Mohammed Sibgathulla
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Artist {
    @Id
    @GeneratedValue
    Long id;
    @NotBlank
    @Size(max = 40)
    String artistName;

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}
