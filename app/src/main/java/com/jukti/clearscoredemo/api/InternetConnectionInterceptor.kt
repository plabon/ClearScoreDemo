package com.jukti.clearscoredemo.api

import android.content.Context
import android.net.NetworkInfo

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetConnectionInterceptor @Inject constructor(@ApplicationContext private val mContext:Context) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            if (!isConnected) {
                throw NoConnectivityException()
                // Throwing our custom exception 'NoConnectivityException'
            }
            val builder: Request.Builder = chain.request().newBuilder()
            return chain.proceed(builder.build())
        }

        val isConnected: Boolean
            get() {
                    val connectivityManager =
                        mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val capabilities =
                            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                        if (capabilities != null) {
                            when {
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                                    return true
                                }
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                                    return true
                                }
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                                    return true
                                }
                            }
                        }
                    } else {
                        val activeNetworkInfo = connectivityManager.activeNetworkInfo
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                            return true
                        }
                    }
                    return false
                }



    }
