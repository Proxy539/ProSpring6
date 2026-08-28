package com.apress.prospring6.sixteen.boot.controllers;

public record SingerInput(String firstName,
                          String lastName,
                          String pseudonym,
                          String genre,
                          String birthDate) {
}
