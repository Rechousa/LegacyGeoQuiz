package com.rechousa.geoquiz

import androidx.annotation.StringRes

data class Question(
    @StringRes val textResourceId: Int,
    val answer: Boolean,
    var correct: Boolean? = null,
)
