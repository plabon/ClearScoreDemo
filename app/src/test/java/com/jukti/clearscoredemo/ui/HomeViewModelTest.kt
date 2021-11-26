package com.jukti.clearscoredemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jukti.clearscoredemo.data.model.ResponseWrapper
import com.jukti.clearscoredemo.domain.creditscore.GetCreditScoreUseCase
import com.jukti.clearscoredemo.domain.model.ScoreDomainModel
import com.jukti.clearscoredemo.presentation.home.HomeCreditViewState
import com.jukti.clearscoredemo.presentation.home.HomeViewModel
import com.jukti.clearscoredemo.utils.TestUtil
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val testDispatcher = coroutineRule.testDispatcher

    private val getCreditScoreUseCase = mock(GetCreditScoreUseCase::class.java)

    private lateinit var homeViewModel: HomeViewModel



    @Before
    fun setup(){
        coroutineRule.runBlockingTest {
            val responseFlow = flow<ResponseWrapper<ScoreDomainModel>> {
                emit(TestUtil.createResponseWrapper())
            }
            `when`(getCreditScoreUseCase.execute()).thenReturn(responseFlow)
            homeViewModel = HomeViewModel(getCreditScoreUseCase)
        }
    }

    @Test
    fun testCallUseCaseAndViewState() {
        coroutineRule.runBlockingTest {
            homeViewModel.mState.test {
                assertEquals(awaitItem(), HomeCreditViewState.SuccessResponse(TestUtil.createTestScoreResponse()))
                homeViewModel.fetchCreditScore()
                assertEquals(awaitItem(), HomeCreditViewState.IsLoading(true))
                assertEquals(awaitItem(), HomeCreditViewState.IsLoading(false))
                assertEquals(awaitItem(), HomeCreditViewState.SuccessResponse(TestUtil.createTestScoreResponse()))
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun calculateProgressPercentage() {
        var result = homeViewModel.calculateProgressPercentage(null,null,100)
        assertEquals(result,0)
        result = homeViewModel.calculateProgressPercentage(100,0,100)
        assertEquals(result,100)
        result = homeViewModel.calculateProgressPercentage(700,0,514)
        assertEquals(result,73)
    }

}