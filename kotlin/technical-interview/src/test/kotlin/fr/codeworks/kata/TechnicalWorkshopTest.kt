package fr.codeworks.kata

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader
import java.io.PrintStream

internal class TechnicalWorkshopTest {

    lateinit var out: PrintStream
    private val filePath = "src/test/resources/init"

    @BeforeEach
    fun setup(){
        out = System.out
        System.setOut(PrintStream(FileOutputStream("$filePath/lead.txt")))

    }
    @AfterEach
    fun destroy(){
        System.setOut(out)
    }

    @Test
    @DisplayName("Should make sure the last outputs matches gold")
    fun shouldRunMain() {
        val gold = BufferedReader(FileReader("$filePath/gold.txt"))
        val lead = BufferedReader(FileReader("$filePath/lead.txt"))

        main()

        var line: String?
        while (gold.readLine().also { line = it } != null) {
            Assertions.assertThat(line).isEqualTo(lead.readLine())
        }

    }
}
