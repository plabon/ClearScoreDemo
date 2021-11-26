package com.jukti.clearscoredemo.presentation.detail

import androidx.lifecycle.ViewModel
import com.jukti.clearscoredemo.domain.model.CreditReportInfoDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsScoreViewModel @Inject constructor(
) : ViewModel() {
    private val _state = MutableStateFlow<DetailsScoreViewState>(DetailsScoreViewState.Init)
    val mState: StateFlow<DetailsScoreViewState> get() = _state

    fun setDetails(creditInfo:CreditReportInfoDomainModel){
        _state.value = DetailsScoreViewState.SuccessResponse(creditInfo)
    }
}

sealed class DetailsScoreViewState  {
    object Init : DetailsScoreViewState()
    data class SuccessResponse(val creditInfo: CreditReportInfoDomainModel) : DetailsScoreViewState()
}