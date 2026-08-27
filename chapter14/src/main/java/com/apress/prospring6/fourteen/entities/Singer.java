package com.apress.prospring6.fourteen.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SINGER")
public class Singer extends AbstractEntity {

    @Serial
    private static final long serialVersionUID = 2L;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "LAST_NAME")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "singer")
    private Set<Album> albums = new HashSet<>();

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;
}
