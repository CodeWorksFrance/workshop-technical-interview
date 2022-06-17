package fr.codeworks.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.io.*

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
            assertThat(line).isEqualTo(lead.readLine())
        }
    }

    @Nested
   inner class IndustrializedGoldenMaster{
        private val baseFile = "src/test/resources/sample-data/base.txt"
        private val baseFileArgumentsAsInput = "src/test/resources/sample-data/base_input.txt"
        private val baseFileExpectedOutput = "src/test/resources/sample-data/base_expected_output.txt"

        @Test
        @DisplayName("Should dynamically replace the parameters in the base file and compare the result with the expected file content ")
        fun shouldExtractContentDataFromSamples() {
            val fileWithStaticContent = File(baseFile)
            fileWithStaticContent.bufferedWriter().use { out ->
                out.write("# a sample test data for testing purposes\n")
                out.write("< some input (first line)\n")
                out.write("> some matching output (first line)\n")
                out.write("# some more comment\n")
                out.write("< some more input (second line)\n")
                out.write("> some more matching output (second line)\n")
            }
            TestExtractorHelper().extractContentOfFiles(baseFile)

            val inputData = File(baseFileArgumentsAsInput).readText(Charsets.UTF_8)
            assertThat(inputData).isEqualTo("some input (first line)\nsome more input (second line)")

            val expectedOutputData = File(baseFileExpectedOutput).readText(Charsets.UTF_8)
            assertThat(expectedOutputData).isEqualTo("some matching output (first line)\nsome more matching output (second line)")
        }
   }
}
