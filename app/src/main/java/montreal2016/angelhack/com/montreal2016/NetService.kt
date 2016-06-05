package montreal2016.angelhack.com.montreal2016

import okhttp3.ResponseBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by daniel on 2016-06-04.
 */

interface NetService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String): Observable<ResponseBody>

    @POST("location")
    fun updateLocation(@Body user: User): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("sendHowl")
    fun howl(@Field("username") username: String): Observable<MutableList<Pack>>

    @FormUrlEncoded
    @POST("join")
    fun joinPack(@Field("username") username: String, @Field("packname") packname: String): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("whichPack")
    fun whichPack(@Field("username") username: String): Observable<WhichPackResponse>
}