package com.apress.prospring6.eight.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @Id
    @Column(name = "INSTRUMENT_ID")
    private String instrumentId;
    @ManyToMany(mappedBy = "instruments")
    private Set<Singer> singers = new HashSet<>();

    public String getInstrumentId() {
        return this.instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Set<Singer> getSingers() {
        return this.singers;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }
}
