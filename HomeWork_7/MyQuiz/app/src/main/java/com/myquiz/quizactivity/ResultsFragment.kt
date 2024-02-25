package com.myquiz.quizactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.myquiz.R
import com.myquiz.databinding.FragmentResultsBinding

private const val ARG_PARAM1 = "quizAnswers"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ResultsFragment : Fragment() {
    private var quizAnswers: String? = null
    private var param2: String? = null

    private var _binding: FragmentResultsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizAnswers = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        binding.againButton.setOnClickListener {
            parentFragmentManager.clearBackStack(ResultsFragment::class.java.simpleName)
            findNavController().navigate(R.id.action_ResultsFragment_to_QuizFragment)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultsTextView.text = quizAnswers
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}