package com.jukti.clearscoredemo.presentation.home

import androidx.lifecycle.*
import com.jukti.clearscoredemo.data.model.ResponseWrapper
import com.jukti.clearscoredemo.domain.creditscore.GetCreditScoreUseCase
import com.jukti.clearscoredemo.domain.model.CreditReportInfoDomainModel
import com.jukti.clearscoredemo.domain.model.ScoreDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCreditScoreUseCase: GetCreditScoreUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeCreditViewState>(HomeCreditViewState.Init)
    val mState: StateFlow<HomeCreditViewState> get() = _state

    init {
        fetchCreditScore()
    }

    fun fetchCreditScore(){
        viewModelScope.launch {
            getCreditScoreUseCase.execute()
                .onStart {
                    _state.value = HomeCreditViewState.IsLoading(true)
                }.catch {
                    _state.value = HomeCreditViewState.IsLoading(false)
                    _state.value = HomeCreditViewState.ErrorResponse("UnknownError")
                }
                .collect {
                    _state.value = HomeCreditViewState.IsLoading(false)
                    when(it){
                        is ResponseWrapper.NetworkError -> _state.value =
                            HomeCreditViewState.ShowToast("Please check your network Conection!")
                        is ResponseWrapper.GenericError -> {
                            it.error?.let { msg ->
                                _state.value = HomeCreditViewState.ShowToast(
                                    msg
                                )
                            }
                        }
                        is ResponseWrapper.Success ->
                            _state.value = HomeCreditViewState.SuccessResponse(it.value.apply
                            {
                                val cRInfoDomainModel: CreditReportInfoDomainModel? =
                                    this.creditReportInfoDomainModel
                                if (cRInfoDomainModel != null) {
                                    this.creditReportInfoDomainModel?.progressPercenrage =
                                        calculateProgressPercentage(
                                            cRInfoDomainModel.maxScoreValue,
                                            cRInfoDomainModel.minScoreValue,
                                            cRInfoDomainModel.score
                                        )
                                }
                            }
                            )
                    }
                }
        }
    }

    fun calculateProgressPercentage(maxScore:Int?,minScore: Int?, score:Int?):Int{
        if(maxScore == null || minScore == null || score==null)
            return 0
        val scoreRange = maxScore-minScore
        val percntage = (score*100)/scoreRange
        if(percntage<0) return 0
        if(percntage>100) return 100
        else return percntage
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object{
        private const val TAG = "SearchViewModel"
    }

}

sealed class HomeCreditViewState  {
    object Init : HomeCreditViewState()
    data class IsLoading(val isLoading: Boolean) : HomeCreditViewState()
    data class ShowToast(val message: String) : HomeCreditViewState()
    data class SuccessResponse(val score: ScoreDomainModel) : HomeCreditViewState()
    data class ErrorResponse(val message : String) : HomeCreditViewState()
}