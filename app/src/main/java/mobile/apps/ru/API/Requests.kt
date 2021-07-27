package mobile.apps.ru.API

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Requests {

    private var api = NetModule().provideCountryApi()

    fun getUid() = api.getListData()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

}