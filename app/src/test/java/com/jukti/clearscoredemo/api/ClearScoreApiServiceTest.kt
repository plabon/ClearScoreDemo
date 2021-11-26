package com.jukti.clearscoredemo.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jukti.clearscoredemo.api.ClearScoreApiService
import com.jukti.clearscoredemo.data.model.CreditReportInfo
import com.jukti.clearscoredemo.data.model.Score
import com.jukti.unrd.utilities.*
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ClearScoreApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ClearScoreApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClearScoreApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

    @Test
    fun getStackExchangeUsersResponse() {
        runBlocking {
            enqueueResponse("clearscore_response.json")
            val response = (service.getCreditScore()as Score)

            val request = mockWebServer.takeRequest()
            assertThat(
                request.path,
                `is`("/endpoint.json")
            )

            assertThat<Score>(response, notNullValue())
            assertThat(response.accountIDVStatus, `is`("PASS"))
            assertThat<CreditReportInfo>(response.creditReportInfo, notNullValue())
            assertThat(response.creditReportInfo?.score, `is`(514))
        }
    }
}