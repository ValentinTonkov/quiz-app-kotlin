package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentQuestionsBinding
import com.example.quizapp.question.Answer
import com.example.quizapp.question.AnswerService
import com.example.quizapp.question.SingleChoiceQuestion


class QuestionsFragment : Fragment() {

    private lateinit var answers: List<Answer>
    private lateinit var binding: FragmentQuestionsBinding
    private lateinit var questions: List<SingleChoiceQuestion>
    private lateinit var currentQuestion : SingleChoiceQuestion
    private var currentQuestionIndex = 0
    private var correctAnswersNumber = 0
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionsBinding.inflate(inflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        createQuestions()
        currentQuestionIndex = 0
        correctAnswersNumber = 0
        currentQuestion = questions[currentQuestionIndex]
        updateUiWithQuestion()

        navController = findNavController()

        binding.nextQuestionButton.setOnClickListener {
            val chosenAnswer = when (binding.answersRadioGroup.checkedRadioButtonId){
                R.id.answerOneRb -> 0
                R.id.answerTwoRb -> 1
                R.id.answerThreeRb -> 2
                R.id.answerFourRb -> 3
                else -> -1
            }

            if (checkForCorrectAnswer(chosenAnswer)){
                correctAnswersNumber++
            }

            currentQuestionIndex++
            if (currentQuestionIndex == questions.size){
                finishTheQuiz()
            } else {
                currentQuestion = questions[currentQuestionIndex]
                updateUiWithQuestion()
            }
        }

        binding.scoreChip.setOnClickListener {
            navController.navigate(QuestionsFragmentDirections.actionQuestionsFragmentToScoreBoardFragment())
        }
    }

    private fun checkForCorrectAnswer(chosenAnswer : Int) : Boolean{
        val correctAnswerIndex = AnswerService.getCorrectAnswerIndex(answers)
        return chosenAnswer == correctAnswerIndex
    }

    private fun finishTheQuiz() {
        if (correctAnswersNumber > questions.size / 2){
            navController.navigate(QuestionsFragmentDirections.actionQuestionsFragmentToSuccessFragment())
        } else {
            navController.navigate(QuestionsFragmentDirections.actionQuestionsFragmentToFailFragment())
        }
    }

    private fun updateUiWithQuestion() {
        binding.questionText.text = currentQuestion.questionText

        answers = currentQuestion.answers.shuffled()
        binding.answerOneRb.text = answers[0].toString()
        binding.answerTwoRb.text = answers[1].toString()
        binding.answerThreeRb.text = answers[2].toString()
        binding.answerFourRb.text = answers[3].toString()

        binding.answersRadioGroup.clearCheck()
    }

    private fun createQuestions() {
        questions = listOf(
            SingleChoiceQuestion("Which is the capital of Bulgaria?",
                listOf(
                    Answer("Plovdiv", false), Answer("Bourgas", false), Answer("Sofia", true), Answer("Pleven", false))
            ),
            SingleChoiceQuestion("What is the capital of France?",
                listOf(
                    Answer("London", false), Answer("Berlin", false), Answer("Paris", true), Answer("Rome", false)
                )
            ),
            SingleChoiceQuestion("What is the capital of Japan?",
                listOf(
                    Answer("Seoul", false), Answer("Beijing", false), Answer("Tokyo", true), Answer("Singapore", false)
                )
            ),
            SingleChoiceQuestion("What is the capital of Germany?",
                listOf(
                    Answer("Vienna", false), Answer("Amsterdam", false), Answer("Berlin", true), Answer("Prague", false)
                )
            )
        ).shuffled()
    }

}