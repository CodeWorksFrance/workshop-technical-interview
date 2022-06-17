package fr.codeworks.kata

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.expect

internal class TechnicalWorkshopTest {

    @Test
    fun testshouldWork() {
        val expected = 42
        Assertions.assertThat(expected).isEqualTo(40+2)
    }
}