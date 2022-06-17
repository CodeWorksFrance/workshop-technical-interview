package fr.codeworks.kata

import java.io.File

class TestExtractorHelper {
    fun extractContentOfFiles(testFileName: String) {
        val extension = testFileName.substringAfterLast('.',"")
        val fileNameWithoutExt = testFileName.substringBeforeLast(".")
        val inputFileName = fileNameWithoutExt.plus("_input.").plus(extension)
        val expectedOutputFileName = fileNameWithoutExt.plus("_expected_output.").plus(extension)
        val extraction = extractTestDataParts(File(testFileName).bufferedReader().readLines())
        File(inputFileName).writeText(extraction.component1())
        File(expectedOutputFileName).writeText(extraction.component2())
    }

    private fun extractTestDataParts(testDataLines: List<String>): Pair<String, String> {
        val inputLines = testDataLines.filter { (it[0] == '<') }
        val expectedOutputLines = testDataLines.filter { (it[0] == '>') }
        return Pair(extractTestDataLines(inputLines)
                , extractTestDataLines(expectedOutputLines))
    }
    private fun extractTestDataLines(testDataLines: List<String>): String {
        return testDataLines.joinToString("\n", "", "") { extractTestDataLine(it) }
    }
    private fun extractTestDataLine(testDataLine: String): String {
        return testDataLine.drop(2)
    }
}
