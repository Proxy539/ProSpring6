package com.apress.prospring6.eleven.domain;

import java.net.URL;
import java.time.LocalDate;

public class BloggerWithAddress {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private URL personalSite;
    private Address address;

    public BloggerWithAddress(String firstName, String lastName, LocalDate birthDate, URL personalSite, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.personalSite = personalSite;
        this.address = address;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public URL getPersonalSite() {
        return personalSite;
    }

    public void setPersonalSite(URL personalSite) {
        this.personalSite = personalSite;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
