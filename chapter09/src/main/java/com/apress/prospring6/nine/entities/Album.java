package com.apress.prospring6.nine.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ALBUM")
@NamedQueries({
        @NamedQuery(name = Album.FIND_ALL, query = "select a from Album a where a.singer = :singer")
})
public class Album extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 3L;

    public static final String FIND_ALL = "Album.findAll";

    @Column
    private String title;
    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    private Singer singer;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Singer getSinger() {
        return this.singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        if (this.id != null) {
            return this.id.equals(album.id);
        }
        return Objects.equals(title, album.title) && Objects.equals(releaseDate, album.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseDate);
    }

    @Override
    public String toString() {
        return "Album - Id: " + id + ", Singer id: " + (singer == null ? null : singer.getId())
                + ", Title: " + title + ", Release Date: " + releaseDate;
    }
}
