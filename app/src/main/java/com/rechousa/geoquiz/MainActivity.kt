package com.rechousa.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.rechousa.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questionTextView.setOnClickListener { nextQuestion() }

        binding.trueButton.setOnClickListener { view: View -> checkAnswer(view, true) }

        binding.falseButton.setOnClickListener { view: View -> checkAnswer(view, false) }

        binding.previousButton.setOnClickListener { previousQuestion() }

        binding.nextButton.setOnClickListener { nextQuestion() }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun previousQuestion() {
        currentIndex = (currentIndex + questionBank.size - 1) % questionBank.size
        updateQuestion()
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun updateQuestion() {
        val question = questionBank[currentIndex]
        val questionTextResourceId = question.textResourceId
        binding.questionTextView.setText(questionTextResourceId)

        val buttonsEnabled = question.correct == null
        binding.trueButton.isEnabled = buttonsEnabled
        binding.falseButton.isEnabled = buttonsEnabled

        val missingQuestions = questionBank.count { it.correct == null }
        if (missingQuestions == 0) {
            computerScore()
        }
    }

    private fun computerScore() {
        val correctAnswers = questionBank.count { it.correct == true }
        val wrongAnswers = questionBank.count { it.correct == false }
        val percentage: Double = correctAnswers / (correctAnswers.toDouble() + wrongAnswers)
        val percentageAsInt = (percentage * 100).toInt()
        val text: CharSequence = "Your score is: ${percentageAsInt}%"

        Toast.makeText(
            applicationContext,
            text,
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun checkAnswer(view: View, userAnswer: Boolean) {
        val question = questionBank[currentIndex]
        val correctAnswer = question.answer
        val userAnswerIsCorrect = userAnswer == correctAnswer
        val messageResourceId = if (userAnswerIsCorrect) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        question.correct = userAnswerIsCorrect
        updateQuestion()

        Snackbar.make(
            view,
            messageResourceId,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}