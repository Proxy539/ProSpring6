package com.apress.prospring6.seven.jooq;

import java.time.LocalDate;

public record AlbumRecord(String title,
                          LocalDate releaseDate) {
}
