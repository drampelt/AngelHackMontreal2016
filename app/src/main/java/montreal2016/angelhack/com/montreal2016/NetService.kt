package montreal2016.angelhack.com.montreal2016

import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * Created by daniel on 2016-06-04.
 */

interface NetService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String): Observable<ResponseBody>
}
