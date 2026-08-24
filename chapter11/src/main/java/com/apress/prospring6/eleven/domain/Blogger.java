package com.apress.prospring6.eleven.domain;

import java.net.URL;
import java.time.LocalDate;

public record Blogger(String firstName,
                      String lastName,
                      LocalDate birthDate,
                      URL personalSite) {
}
