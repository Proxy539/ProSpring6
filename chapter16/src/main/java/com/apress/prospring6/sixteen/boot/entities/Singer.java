package com.apress.prospring6.sixteen.boot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "SINGER")
public class Singer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID")
    protected Long id;

    @Version
    @Column(name = "VERSION")
    protected int version;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PSEUDONYM")
    private String pseudonym;

    @Column(name = "GENRE")
    private String genre;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "singer")
    private Set<Award> awards;

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
    joinColumns = @JoinColumn(name = "SINGER_ID"),
    inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
    private Set<Instrument> instruments;
}
