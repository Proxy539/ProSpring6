package com.apress.prospring6.sixteen.boot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INSTRUMENT")
public class Instrument implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @Column(name = "INSTRUMENT_ID")
    private String name;

    @ManyToMany(mappedBy = "instruments")
    private Set<Singer> singers = new HashSet<>();
}
