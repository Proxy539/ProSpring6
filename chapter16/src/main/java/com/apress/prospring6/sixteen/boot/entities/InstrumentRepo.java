package com.apress.prospring6.sixteen.boot.entities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepo extends JpaRepository<Instrument, String> {
}
