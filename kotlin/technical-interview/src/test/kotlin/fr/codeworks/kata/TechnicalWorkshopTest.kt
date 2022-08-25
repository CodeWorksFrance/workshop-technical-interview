package fr.codeworks.kata

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class TechnicalWorkshopTest {

    @Test
    fun shouldWork() {
        val expected = 42
        Assertions.assertThat(expected).isEqualTo(40+2)
    }
}
