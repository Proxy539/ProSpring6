package com.apress.prospring6.eleven.domain;

import com.apress.prospring6.eleven.validator.CheckCountrySinger;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@CheckCountrySinger
public class Singer {

    @NotNull
    @Size(min = 2, max = 60)
    private String firstName;

    private String lastName;

    @NotNull
    private Genre genre;

    private Gender gender;

    public boolean isCountrySinger() {
        return Genre.COUNTRY == genre;
    }

    public enum Genre {
        POP("P"), JAZZ("J"), BLUES("B"), COUNTRY("C");

        private String code;

        private Genre(String code) {
            this.code = code;
        }

        public String toString() {
            return this.code;
        }
    }

    public enum Gender {
        MALE("M"), FEMALE("F"), UNSPECIFIED("U");

        private String code;

        Gender(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code;
        }
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
