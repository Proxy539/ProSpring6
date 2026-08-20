package com.apress.prospring6.eight.entities;

import com.apress.prospring6.seven.base.entities.AbstractEntity;
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
}
