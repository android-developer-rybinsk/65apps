package mobile.apps.ru.API

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetModule {

    private fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    private fun provideCountryApi(gson: Gson, okHttpClient: OkHttpClient): Api {
        val httpClientBuilder = okHttpClient.newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)


        httpClientBuilder.addInterceptor{
            it.proceed(
                it.request()
                    .newBuilder()
                    .header("accept", "application/json")
                    .build()
            )
        }

        return Retrofit.Builder()
            .baseUrl("http://gitlab.65apps.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .callFactory(httpClientBuilder.build())
            .build().create(Api::class.java)
    }

    internal fun provideCountryApi() = provideCountryApi(provideGson(), provideOkHttpClient())

}