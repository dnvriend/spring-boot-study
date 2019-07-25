package com.github.dnvriend.services

import org.springframework.stereotype.Service

@Service
class CalculatorService {
    fun add(x: Int, y: Int): Int = x + y
}