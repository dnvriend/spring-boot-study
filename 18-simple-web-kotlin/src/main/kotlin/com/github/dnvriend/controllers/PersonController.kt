package com.github.dnvriend.controllers

import com.github.dnvriend.repositories.PersonRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(val personRepository: PersonRepository) {
    @GetMapping("/{id}")
    fun getPerson(@PathVariable("id") id: Long): Person? {
        return personRepository.findByIdOrNull(id)
    }

    @GetMapping
    fun getPersons(): List<Person> {
        return personRepository.findAll()
    }

    @PutMapping
    fun putPerson(@RequestBody person: Person): Person {
        return personRepository.save(person)
    }

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable("id") id: Long) {
        personRepository.deleteById(id)
    }
}

// top level functions, ie 'static'

fun createPerson(id: Int): Person {
    return Person(id, "dnvriend", 42)
}

/**
 * Can be accessed by prefixing the method name wih the package name
 * of import the package, and then access the function
 */
fun createListOfPersons(): List<Person> {
    return listOf(
            Person(1, "dnvriend", 42),
            Person(2, "dnvriend", 43),
            Person(3, "dnvriend", 44),
            Person(4, "dnvriend", 45)
    )
}
