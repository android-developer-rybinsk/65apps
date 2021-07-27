package mobile.apps.ru.API

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.Disposable

open class Activity65apps() : AppCompatActivity() {

    @JvmField
    val api = Requests()

    private val disposable = AndroidDisposable()

    fun doRequest(request: Disposable) {
        disposable.add(request)
    }

    fun onErrorRequest(message: String?) {
        Log.e("Error request", "$this : $message")
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}