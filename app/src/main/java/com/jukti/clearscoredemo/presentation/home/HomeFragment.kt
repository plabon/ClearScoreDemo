package com.jukti.clearscoredemo.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jukti.clearscoredemo.R
import com.jukti.clearscoredemo.databinding.FragmentHomeBinding
import com.jukti.clearscoredemo.domain.model.ScoreDomainModel
import com.jukti.unrd.utilities.CREDIT_ITEM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    lateinit var homeViewModel: HomeViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
      _binding = FragmentHomeBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

    }

    private fun initObservers() {
        homeViewModel.mState
            .flowWithLifecycle(lifecycle,  Lifecycle.State.STARTED)
            .onEach { it -> processResponse(it)}
            .launchIn(lifecycleScope)
    }


    private fun processResponse(state: HomeCreditViewState) {
        when(state){
            is HomeCreditViewState.Init -> Unit
            is HomeCreditViewState.ErrorResponse -> handleError(state.message)
            is HomeCreditViewState.SuccessResponse -> handleSuccessResponse(state.score)
            is HomeCreditViewState.ShowToast -> showToast(state.message)
            is HomeCreditViewState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleError(rawResponse: String) {
        activity?.let {
            Snackbar.make(it.findViewById(android.R.id.content), rawResponse, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun handleSuccessResponse(score: ScoreDomainModel) {
        binding.loadingProgress.visibility = View.GONE
        binding.bottomTv.visibility = View.VISIBLE
        binding.centerTv.visibility = View.VISIBLE
        binding.topTv.visibility = View.VISIBLE
        binding.topTv.text = getString(R.string.top_text)
        binding.centerTv.text = score.creditReportInfoDomainModel?.score?.toString()
        binding.bottomTv.text = getString(R.string.bottom_text,score.creditReportInfoDomainModel?.maxScoreValue?.toString())
        binding.progressBar.visibility=View.VISIBLE
        binding.progressBar.progress = score.creditReportInfoDomainModel?.progressPercenrage!!

        binding.progressBar.setOnClickListener {
            val bundle = bundleOf(CREDIT_ITEM to score.creditReportInfoDomainModel)
            view?.findNavController()?.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
        }
    }

    private fun showToast(message: String) {
        activity?.let {
            Snackbar.make(it.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun handleLoading(loading: Boolean) {
        if(loading) {
            binding.loadingProgress.visibility = View.VISIBLE
            binding.bottomTv.visibility = View.GONE
            binding.centerTv.visibility = View.GONE
            binding.topTv.visibility = View.GONE
            binding.progressBar.progress = 0
        }
        else
            binding.loadingProgress.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val TAG = "HomeFragment"
    }
}