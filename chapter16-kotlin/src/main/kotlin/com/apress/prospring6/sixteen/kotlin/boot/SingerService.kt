package com.apress.prospring6.sixteen.kotlin.boot

import jakarta.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Transactional
@Service
class SingerService(private val repository: SingerRepository) {

    @Throws(NotFoundException::class)
    fun findAll(): List<Singer> {
        val singers: List<Singer> = repository.findAll() as List<Singer>
        if (singers.isEmpty()) {
                throw NotFoundException(Singer::javaClass.name)
        }
        return singers
    }

    @Throws(NotFoundException::class)
    fun findById(id: Long?): Singer? {
        return id?.let { repository.findById(id).orElseThrow { NotFoundException(Singer::javaClass.name, id)}}
    }

    @Throws(DataIntegrityViolationException::class)
    fun save(singer: Singer?): Singer? {
        return singer?.let { repository.save(it) }
    }

    @Throws(NotFoundException::class, DataIntegrityViolationException::class)
    fun update(id: Long?, singer: Singer): Singer? {
        return id?.let { repository.findById(id).map { update(it, singer, repository)}
            .orElseThrow {NotFoundException(Singer::javaClass.name, id)}}
    }

    @Throws(NotFoundException::class)
    fun delete(id: Long?) {
        id?.let { repository.findById(id).orElseThrow { NotFoundException(Singer::javaClass.name, id)}}
        id?.let { repository.deleteById(id)}
    }

    fun update(it: Singer, singer: Singer, repository: SingerRepository): Singer {
        it.firstName = singer.firstName
        it.lastName = singer.lastName
        it.birthDate = singer.birthDate

        return repository.save(it)
    }
}