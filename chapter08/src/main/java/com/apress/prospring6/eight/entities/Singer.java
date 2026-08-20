package com.apress.prospring6.eight.entities;

import com.apress.prospring6.seven.base.entities.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SINGER")
@NamedQueries({
        @NamedQuery(name = Singer.FIND_ALL, query = "select s from Singer s"),
        @NamedQuery(name = Singer.FIND_SINGER_BY_ID, query = """
            select distinct s from Singer s
            left join fetch s.albums a
            left join fetch s.instruments i
            where s.id = :id
        """),
        @NamedQuery(name = Singer.FIND_ALL_WITH_ALBUM,
        query = """
            select distinct s from Singer s
            left join fetch s.albums a
            left join fetch s.instruments i
        """)
})
@SqlResultSetMapping(
        name = "singerResult",
        entities = @EntityResult(entityClass = Singer.class)
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Singer.getFirstNameById(?)",
                query = "select getfirstnamebyid(?)"
        )
})
public class Singer extends AbstractEntity {

    @Serial
    private static final long serialVersionUID = 2L;

    public static final String FIND_ALL = "Singer.findAll";
    public static final String FIND_SINGER_BY_ID = "Singer.findById";
    public static final String FIND_ALL_WITH_ALBUM = "Singer.findAllWithAlbum";

    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;
    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Album> albums = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
    joinColumns = @JoinColumn(name = "SINGER_ID"),
    inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
    private Set<Instrument> instruments = new HashSet<>();

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }
}
