package com.apress.prospring6.fourteen.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ALBUM")
@NamedQuery(name = Album.FIND_WITH_RELEASE_DATE_GREATER_THAN, query = "select a from Album a where a.releaseDate > ?1")
public class Album extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String FIND_WITH_RELEASE_DATE_GREATER_THAN = "Album.findWithReleaseDateGreaterThan";

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

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public Singer getSinger() {
        return this.singer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
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
        return "Album - Id: " + id + ", Singer id: " + (singer != null ? singer.getId() : "")
                + ", Title: " + title + ", Release Date: " + releaseDate;
    }
}
