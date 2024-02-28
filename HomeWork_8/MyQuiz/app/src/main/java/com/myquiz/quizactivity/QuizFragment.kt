package com.myquiz.quizactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import com.example.quizapplicationskillbox.quiz.QuizStorage
import com.myquiz.R
import com.myquiz.databinding.FragmentQuizBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QuizFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentQuizBinding? = null
    private var results: MutableList<Int> = mutableListOf()
    private var radioGroupList: MutableList<RadioGroup> = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        val bundle = Bundle()
        radioGroupList = inflateRadioGroup()

        binding.backButton.setOnClickListener {
            activity?.finish()
        }

        binding.sendButton.setOnClickListener {
            results = getResultFromRadioGroup(radioGroupList)
            if (results.size < 3) {
                Toast.makeText(this.context, getText(R.string.not_all_is_marked), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val quizAnswers = QuizStorage.answer(QuizStorage.getQuiz(QuizStorage.Locale.Ru), results)
            bundle.putString("quizAnswers", quizAnswers)
            findNavController().navigate(R.id.action_QuizFragment_to_ResultsFragment, args = bundle)
        }

        return binding.root

    }

    private fun getResultFromRadioGroup(radioGroupList: MutableList<RadioGroup>): MutableList<Int> {
        val resultList: MutableList<Int> = mutableListOf()
        radioGroupList.forEachIndexed { _, radioGroup ->
            if (radioGroup.checkedRadioButtonId >= 0) resultList.add(radioGroup.checkedRadioButtonId)
        }
        return resultList
    }

    private fun inflateRadioGroup(): MutableList<RadioGroup> {
        radioGroupList.clear()
        val paddingDp = 16
        val paddingPx = (resources.displayMetrics.density * paddingDp + 0.5F).toInt()
        var radioGroup: RadioGroup
        val linearLayout = binding.linearLayout
        val questions = QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions
        for (question in questions.indices) {
            var answerId = 0
            radioGroup = RadioGroup(this.context)
            radioGroup.orientation = RadioGroup.VERTICAL

            // Анимация появления элементов radioGroup
            radioGroup.alpha = 0f
            radioGroup.animate().apply {
                duration = 2000
                alphaBy(1f)
            }

            val questionText = TextView(this.context)
            questionText.text = questions[question].question
            questionText.id = View.generateViewId()
            questionText.setPadding(paddingPx)
            radioGroup.addView(questionText)
            for (answer in questions[question].answers.indices) {
                val radioButton = RadioButton(this.context)
                radioButton.text = questions[question].answers[answer]
                radioButton.id = answerId
                radioGroup.addView(radioButton)
                answerId++
            }
            linearLayout.addView(radioGroup)
            radioGroupList.add(radioGroup)
        }
        return radioGroupList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        results.clear()
        radioGroupList.clear()
    }
}