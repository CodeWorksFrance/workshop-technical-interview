package fr.codeworks.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.io.*

internal class TechnicalWorkshopTest {

    lateinit var out: PrintStream
    private val filePath = "src/test/resources/init"

    @Nested
    inner class GoldenMaster {
        @BeforeEach
        fun setup() {
            out = System.out
            System.setOut(PrintStream(FileOutputStream("$filePath/lead.txt")))

        }

        @AfterEach
        fun destroy() {
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
                assertThat(line).isEqualTo(lead.readLine())
            }
        }
    }

    @Nested
    inner class IndustrializedGoldenMaster {
        private val rootFolder = "src/test/resources/sample-data"
        @Test
        @DisplayName("Should dynamically replace the parameters in the base file and compare the result with the expected file content ")
        fun shouldExtractContentDataFromSamplesAndTheOuputShouldMatch() {
            TestExtractorHelper().extractContentOfFiles("$rootFolder/base.txt")

            val inputData = File("$rootFolder/base_input.txt").readText(Charsets.UTF_8)
            assertThat(inputData).isEqualTo("some input (first line)\nsome more input (second line)")

            val expectedOutputData = File("$rootFolder/base_expected_output.txt").readText(Charsets.UTF_8)
            assertThat(expectedOutputData).isEqualTo("some matching output (first line)\nsome more matching output (second line)")
        }
    }
}
