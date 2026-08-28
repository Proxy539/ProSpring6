package com.apress.prospring6.sixteen.boot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "AWARD")
public class Award implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Version
    @Column(name = "VERSION")
    protected int version;

    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    private Singer singer;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "TYPE")
    private String category;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "AWARD_NAME")
    private String awardName;
}
