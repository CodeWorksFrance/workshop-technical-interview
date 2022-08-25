package fr.codeworks.kata

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader
import java.io.PrintStream

internal class TechnicalWorkshopTest {

    lateinit var out: PrintStream

    @Nested
    inner class InitialState {
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

    @Nested
    inner class RefactoredVersion {
        private val anotherFilePath = "src/test/resources/step1"

        @BeforeEach
        fun setup(){
            out = System.out
            System.setOut(PrintStream(FileOutputStream("$anotherFilePath/lead_ok_to_play.txt")))

        }

        @AfterEach
        fun destroy(){
            System.setOut(out)
        }

        @Test
        fun `Should make sure the output matches gold when the candidate agrees to play`(){
            val gold = BufferedReader(FileReader("$anotherFilePath/gold_ok_to_play.txt"))
            val lead = BufferedReader(FileReader("$anotherFilePath/lead_ok_to_play.txt"))

           val testableWorkshop = TestableTechnicalWorkshop()
            testableWorkshop.runCodeTest("Java")

            var line: String?
            while (gold.readLine().also { line = it } != null) {
                Assertions.assertThat(line).isEqualTo(lead.readLine())
            }
        }
    }

}

class TestableTechnicalWorkshop : TechnicalWorkshop() {
    override fun readCandidateConsentOnPlaying()= "y"

}
