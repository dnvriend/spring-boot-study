package com.github.dnvriend

import arrow.core.None
import arrow.core.Option
import com.github.dnvriend.services.CalculatorService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KotlinLanguageTest {

    var calculator: CalculatorService? = null

    @BeforeEach
    fun setUp() {
        calculator = CalculatorService()
    }

    @Test
    fun `Add two numbers`() {
        val actual = calculator?.add(1, 2)
        assertThat(actual).isEqualTo(3)
    }

    @Test
    fun `handle optional value - map - map`() {
        val opt: Int? = 1
        // map -> map
        val actual: Int? = opt
                ?.let { it + 1 }
                ?.let { it + 2 }
        assertThat(actual).isEqualTo(4)
    }

    @Test
    fun `handle optional value - flatMap - flatMap `() {
        val opt: Int? = 1
        // map -> map
        val actual: Int? = opt
                ?.let { handleFirst(it) }
                ?.let { handleSecond(it) }
                ?.let { handleThird(it) }
        assertThat(actual).isNull()
    }

    @Test
    fun `handle optional value fp way`() {
        val opt: Option<Int> = Option(1)
        val actual: Option<Int> = opt.map { it + 1 }.filter { it > 10 }
        assertThat(actual).isEqualTo(None)
    }

    @Test
    fun `map a list`() {
        val xs: List<Int> = listOf(1, 2, 3)
        val ys = xs.map { x -> x + 1 }
        val zs = xs.map { it + 1 }
        assertThat(ys).isEqualTo(listOf(2, 3, 4))
        assertThat(zs).isEqualTo(listOf(2, 3, 4))
    }

    @Test
    fun `flatMap lists`() {
        val xs: List<Int> = listOf(1, 2, 3)
        val ys: List<Int> = listOf(4, 5, 6)
        val zs: List<Int> = xs.flatMap { x -> ys.map { it + x } }
        assertThat(zs).isEqualTo(listOf(5, 6, 7, 6, 7, 8, 7, 8, 9))
    }
}

fun handleFirst(x: Int?): Int? {
    println("Handling first: $x")
    return 1
}

fun handleSecond(x: Int?): Int? {
    println("Handling second: $x")
    return null
}

fun handleThird(x: Int?): Int? {
    println("Handling third: $x")
    return null
}


