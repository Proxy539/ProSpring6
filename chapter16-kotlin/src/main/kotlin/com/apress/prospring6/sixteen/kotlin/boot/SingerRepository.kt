package com.apress.prospring6.sixteen.kotlin.boot

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface SingerRepository : CrudRepository<Singer, Long> {

    @Query("select s from Singer s where s.firstName = :fn")
    fun findByFirstName(@Param("fn") firstName: String?): Iterable<Singer?>?

    @Query("select s from Singer s where s.firstName like %?1%")
    fun findByFirstNameLike(firstName: String?): Iterable<Singer?>?
}