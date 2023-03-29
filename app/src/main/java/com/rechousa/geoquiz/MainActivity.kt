package com.rechousa.geoquiz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.rechousa.geoquiz.databinding.ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questionTextView.setOnClickListener { nextQuestion() }

        binding.trueButton.setOnClickListener { view: View -> checkAnswer(view, true) }

        binding.falseButton.setOnClickListener { view: View -> checkAnswer(view, false) }

        binding.previousButton.setOnClickListener { previousQuestion() }

        binding.nextButton.setOnClickListener { nextQuestion() }

        updateQuestion()
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
        val questionTextResourceId = questionBank[currentIndex].textResourceId
        binding.questionTextView.setText(questionTextResourceId)
    }

    private fun checkAnswer(view: View, userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResourceId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Snackbar.make(
            view,
            messageResourceId,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}