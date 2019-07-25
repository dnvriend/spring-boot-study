package com.github.dnvriend.repositories

import com.github.dnvriend.controllers.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long> {

}