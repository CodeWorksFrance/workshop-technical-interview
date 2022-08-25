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
    inner class CharacterizationTesting{

        @Test
        fun `Should return a score of 0 when the candidate refuses to play`(){

            val result = TechnicalWorkshop().runCodeTest("Java")

            assertThat(result).isEqualTo(0.0)
        }

        @Test
        fun `Should return a score of 2,5 when the candidates is half correct`(){
            val workshop = TestableTechnicalWorkshop()

            val fakeQ1 = Question("Q1", "R1", 2)
            val fakeQ2 = Question("Q2", "R2", 3)
            val fakeQ3 = Question("Q3", "R3", 3)

            val fakeR1 = CandidateResponse("R1", fakeQ1)
            val fakeR2 = CandidateResponse("R1", fakeQ2)
            val fakeR3 = CandidateResponse("R1", fakeQ3)

            val allResponses = mutableListOf(fakeR1, fakeR2, fakeR3)
            workshop.collectCandidatesAnswersToQuestions(listOf(fakeQ1,fakeQ2, fakeQ3), allResponses)

            val finalScore = workshop.runCodeTest("Java")
            assertThat(finalScore).isEqualTo(2.5)
        }

        inner class TestableTechnicalWorkshop: TechnicalWorkshop(){

            override fun readTheUserResponseForPlaying() = "y"
            override fun readCandidateAnswersToQuestions() = "random"
            override fun computeScore(responses: MutableList<CandidateResponse>): Double = 2.5
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
