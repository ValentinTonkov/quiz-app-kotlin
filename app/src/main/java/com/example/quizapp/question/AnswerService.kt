package com.example.quizapp.question

class AnswerService {

    companion object {
        fun getCorrectAnswerIndex(answers : List<Answer>) : Int{
                for (i in answers.indices) {
                    if (answers.get(i).correct == true) {
                        return i
                    }
                }
                return 0
            }

    }
}