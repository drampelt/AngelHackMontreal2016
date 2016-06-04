package montreal2016.angelhack.com.montreal2016

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderApi
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by daniel on 2016-06-04.
 */

class BackgroundLocationService : BroadcastReceiver(), AnkoLogger {
    override fun onReceive(context: Context, intent: Intent) {
        val location = intent.extras.get(FusedLocationProviderApi.KEY_LOCATION_CHANGED) as Location
        info("got update ${location.longitude}, ${location.latitude}")
    }

}
