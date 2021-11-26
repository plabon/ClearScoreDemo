package com.jukti.clearscoredemo.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.jukti.clearscoredemo.databinding.FragmentDetailsBinding
import com.jukti.clearscoredemo.domain.model.CreditReportInfoDomainModel
import com.jukti.unrd.utilities.CREDIT_ITEM
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsScoreFragment : Fragment() {

    lateinit var detailsScoreViewModel: DetailsScoreViewModel
    private var _binding: FragmentDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsScoreViewModel = ViewModelProvider(this).get(DetailsScoreViewModel::class.java)
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        arguments?.getParcelable<CreditReportInfoDomainModel>(CREDIT_ITEM)?.let {
            detailsScoreViewModel.setDetails(it)
        }


    }

    private fun initObservers() {
        detailsScoreViewModel.mState
            .flowWithLifecycle(lifecycle,  Lifecycle.State.STARTED)
            .onEach { it -> processResponse(it)}
            .launchIn(lifecycleScope)
    }

    private fun processResponse(state: DetailsScoreViewState) {
        when(state){
            is DetailsScoreViewState.Init -> Unit
            is DetailsScoreViewState.SuccessResponse -> handleSuccessResponse(state.creditInfo)
        }
    }

    private fun handleSuccessResponse(cinfo:CreditReportInfoDomainModel) {
        binding.creditInfo = cinfo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}