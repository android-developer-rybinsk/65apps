package mobile.apps.ru.API

import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("65gb/static/raw/master/testTask.json")
    fun getListData(): Single<AnswerData>

}