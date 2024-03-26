package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentQuestionsBinding


class QuestionsFragment : Fragment() {

    private lateinit var binding: FragmentQuestionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionsBinding.inflate(inflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextQuestionButton.setOnClickListener {
            val navController = findNavController()
           if (binding.answersRadioGroup.checkedRadioButtonId == R.id.answer2Rb) {
               navController.navigate(QuestionsFragmentDirections.actionQuestionsFragmentToSuccessFragment())
           } else {
               navController.navigate(QuestionsFragmentDirections.actionQuestionsFragmentToFailFragment())
           }
        }

    }

}