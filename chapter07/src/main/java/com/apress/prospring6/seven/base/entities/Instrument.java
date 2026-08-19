package com.apress.prospring6.seven.base.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INSTRUMENT")
public class Instrument implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;
    private String instrumentId;
    private Set<Singer> singers = new HashSet<>();

    @Id
    @Column(name = "INSTRUMENT_ID")
    public String getInstrumentId() {
        return this.instrumentId;
    }

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
    joinColumns = @JoinColumn(name = "INSTRUMENT_ID"),
    inverseJoinColumns = @JoinColumn(name = "SINGER_ID"))
    public Set<Singer> getSingers() {
        return this.singers;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }
}
