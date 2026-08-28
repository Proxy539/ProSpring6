package com.apress.prospring6.sixteen.kotlin.boot

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@Entity
@Table(name = "SINGER")
data class Singer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore //do not serialize
    var id: Long? = null,

    @Version
    @Column(name = "VERSION")
    @JsonIgnore // do not serialize
    var version: Int = 0,

    @Column(name = "FIRST_NAME")
    @NotEmpty
    @Size(min = 2, max = 30)
    var firstName: String?,

    @Column(name = "LAST_NAME")
    @NotEmpty
    @Size(min = 2, max = 30)
    var lastName: String? = null,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BIRTH_DATE")
    var birthDate: LocalDate? = null
) {}