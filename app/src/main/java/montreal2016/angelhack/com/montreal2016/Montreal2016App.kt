package montreal2016.angelhack.com.montreal2016

import android.app.Application
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers

/**
 * Created by daniel on 2016-06-04.
 */

class Montreal2016App : Application() {
    var username = ""
    lateinit var netService: NetService

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://angelhackmtl-cherylyli.c9users.io/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        netService = retrofit.create(NetService::class.java)
    }
}
