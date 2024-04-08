package com.example.quizapp.question


class SingleChoiceQuestion (val questionText : String, val answers : List<Answer>) {

    init {
        var correctAnswersNumber = 0;
        answers.forEach{
            if (it.correct){
                correctAnswersNumber++
            }
        }
        require(correctAnswersNumber == 1){"There must be one correct answer."}
    }


}