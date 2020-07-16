package com.aistle.MusicWorld.model;

import com.aistle.MusicWorld.util.StringListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Mohammed Sibgathulla
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue
    Long id;
    @NotBlank
    @Size(max = 40)
    private String title;
    @NotBlank
    @Size(max = 5)
    private int yearOfRelease;
    @Convert(converter = StringListConverter.class)
    private List<String> genres;
    @ManyToOne
    @JoinColumn(name="artist_id", nullable=false)
    private Artist artist;

    public Album(String title, int yearOfRelease, List<String> genres, Artist artist) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.genres = genres;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", yearOfRelease='" + yearOfRelease + '\'' +
                ", genres=" + genres +
                ", artist=" + artist +
                '}';
    }
}
