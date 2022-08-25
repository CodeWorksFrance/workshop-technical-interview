package fr.codeworks.kata

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

data class Question( val label: String, val answer: String,val difficulty: Int)
data class CategorizedQuestions(val label: String, val questions: List<Question>)
data class CandidateResponse(val response : String, val question: Question)
data class Candidate(var firstname: String, var lastname: String, var email: String)

open class TechnicalWorkshop {
    private var categories = mutableSetOf<String>()
    private var candidate: Candidate? = null

    fun addCat(c: String) {
        println("Adding $c as categories")
        categories.add(c)
    }

    fun addCan(lastname: String, fistname: String, email: String) {
        println("Adding $fistname as candidate")
        this.candidate = Candidate(lastname, fistname, email)
    }

    fun runCodeTest(c: String): Double {
        val resource = javaClass.getResource("/categories.json")
        val file = File(resource!!.path)
        val allCategoriesAsRaw = file.bufferedReader().use { it.readText() }
        val categoryType = object : TypeToken<List<CategorizedQuestions>>() {}.type
        val list = Gson().fromJson<List<CategorizedQuestions>?>(allCategoriesAsRaw, categoryType)
        val questions = list.find { q -> q.label == c }
        val question: List<Question>? = questions?.questions
        val responses = mutableListOf<CandidateResponse>()
        println("Welcome to the interview game. You'll have ${question?.size} questions on $c")
        print("Are you ready? (y) to start?\n")
        val value = readTheUserResponseForPlaying() //response of the user
        if (value == "y") {
            println("Let's go!")
            print("***************** Questions *****************\n")
            collectCandidatesAnswersToQuestions(question, responses)
            println("Thank you for your participation!")
        }
        println("***************** Response from: ${this.candidate?.firstname} *****************\n")
        return computeScore(responses)
    }

    open fun computeScore(responses: MutableList<CandidateResponse>): Double {
        var score = 0.0 // s is for score
        responses.forEach { candidateResponse ->
            val currentQuestion = candidateResponse.question
            println("> Question: ${candidateResponse.question} ?\n")
            println(">>> Response: ${candidateResponse.response}.\n")
            print("----> What is your evaluation: t=true or f=false ?\n")
            val answer = readCodeworkerEvaluation()
            if (answer != null) {
                if (answer == "t" || answer == "T") {
                    when (currentQuestion.difficulty ) {
                        1 -> score += 0.25
                        2 -> score += 0.5
                        3 -> score += 0.75
                        4 -> score += 1
                    }
                }
            }
        }
        return score
    }

    open fun collectCandidatesAnswersToQuestions(
        question: List<Question>?,
        responses: MutableList<CandidateResponse>
    ) {
        question?.forEach { q ->
            print(q.label)
            val a = readCandidateAnswersToQuestions()
            print("\n")
            if (a != null) {
                responses.add(CandidateResponse(a, q))
            }
        }
    }

    open fun readTheUserResponseForPlaying() = readLine()

    open fun readCodeworkerEvaluation() = readLine()

    open fun readCandidateAnswersToQuestions() = readLine()

}


fun main() {
    runTheWholeGame(TechnicalWorkshop())
}

fun runTheWholeGame(technicalWorkshop: TechnicalWorkshop) {
    technicalWorkshop.addCat("SQL")
    technicalWorkshop.addCan("Toto", "Titi", "titi@mail.fr")
    val score = technicalWorkshop.runCodeTest("Java")
    println("The candidate as a total of $score points.")
}
