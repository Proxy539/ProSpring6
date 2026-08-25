package com.apress.prospring6.eleven.domain;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SingerTwo {

    @NotNull
    @Size(min = 2, max = 60)
    private String firstName;

    private String lastName;

    @NotNull
    private Singer.Genre genre;

    private Singer.Gender gender;

    @AssertTrue(message = "ERROR! Individual singer should have gender and last name defined")
    public boolean isCountrySinger() {
        return genre == null || (!genre.equals(Singer.Genre.COUNTRY) ||
                (gender != null && lastName != null));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Singer.Genre getGenre() {
        return genre;
    }

    public void setGenre(Singer.Genre genre) {
        this.genre = genre;
    }

    public Singer.Gender getGender() {
        return gender;
    }

    public void setGender(Singer.Gender gender) {
        this.gender = gender;
    }
}
