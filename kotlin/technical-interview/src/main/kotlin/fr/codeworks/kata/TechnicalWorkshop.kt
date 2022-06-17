package fr.codeworks.kata

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

data class Question( val label: String, val answer: String,val difficulty: Int)
data class CategorizedQuestions(val label: String, val questions: List<Question>)
data class CandidateResponse(val response : String, val question: Question)
data class Candidate(var firstname: String, var lastname: String, var email: String) {}

class TechnicalWorkshop {
    internal var categories = mutableSetOf<String>()
    internal var candidate: Candidate? = null

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
        val file = File(resource.path)
        val allCategoriesAsRaw = file.bufferedReader().use { it.readText() }
        val categoryType = object : TypeToken<List<CategorizedQuestions>>() {}.type
        val list = Gson().fromJson<List<CategorizedQuestions>?>(allCategoriesAsRaw, categoryType)
        val questions = list.find { q -> q.label == c }
        val cq = questions
        val question: List<Question>? = cq?.questions
        val responses = mutableListOf<CandidateResponse>()
        var s = 0.0 // s is for score
        println("Welcome to the interview game. You'll have ${question?.size} questions on ${c}")
        print("Are you ready? (y) to start?\n")
        val value = readLine() //response of the user
        if (value == "y") {
            println("Let's go!")
            print("***************** Questions *****************\n")
            question?.forEach { q ->
                print(q.label)
                val a = readLine()
                if (a != null) {
                    responses.add(CandidateResponse(a, q))
                }
            }
            println("Thank you for your participation!")
        }
        println("***************** Response from: ${this.candidate?.firstname} *****************\n")
        responses.forEach { candidateResponse ->
            val currentQuestion = candidateResponse.question
            println("> Question: ${candidateResponse.question} ? \n")
            println(">>> Response: ${candidateResponse.response}. \n")
            print("----> What is your evaluation: t=true or f=false ?\n")
            val answer = readLine()
            if (answer != null) {
                if (answer.equals("t") || answer.equals("T")) {
                    when (currentQuestion.difficulty) {
                        1 -> s += 0.25
                        2 -> s += 0.5
                        3 -> s += 0.75
                        4 -> s += 1
                    }
                }
            }
        }
        return s
    }

}


fun main() {
    val codeTest = TechnicalWorkshop()
    codeTest.addCat("SQL")
    codeTest.addCan("Toto", "Titi", "titi@mail.fr")
    val score = codeTest.runCodeTest("Java")
    println("The candidate as a total of $score points.")
}
