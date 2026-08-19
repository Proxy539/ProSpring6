package com.apress.prospring6.seven.base.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDate;

@Entity
@Table(name = "ALBUM")
public class Album extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 3L;

    private String title;
    private LocalDate releaseDate;
    private Singer singer;

    @Column
    public String getTitle() {
        return this.title;
    }

    @Column(name = "RELEASE_DATE")
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    public Singer getSinger() {
        return this.singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
