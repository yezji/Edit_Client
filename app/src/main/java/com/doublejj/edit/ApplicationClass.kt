package com.doublejj.edit

import android.app.Application
import android.content.SharedPreferences
import com.doublejj.edit.data.api.interceptors.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        // 서버 주소
//        const val API_URL = "https://app.team-edit.shop/" // product server
        const val API_URL = "https://app.team-edit.shop/" // test server

        // 만들어져있는 SharedPreferences를 사용 (재생성하지 않도록 유념)
        lateinit var sSharedPreferences: SharedPreferences

        // JWT Token Header 키 값
        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

        // Retrofit 인스턴스, 앱 실행 시 한번만 생성하여 사용
        lateinit var sRetrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        // SharedPreferences 인스턴스 최초 생성
        sSharedPreferences = applicationContext.getSharedPreferences("EDIT", MODE_PRIVATE)

        // Retrofit 인스턴스 최초 생성
        initRetrofitInstance()
    }

    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    private fun initRetrofitInstance() {
        // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, Interceptor, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}