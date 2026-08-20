package com.apress.prospring6.seven.jooq;

import java.time.LocalDate;
import java.util.List;

public record SingerWithAlbums(String firstName,
                               String lastName,
                               LocalDate birthDate,
                               List<AlbumRecord> albums) {
}
