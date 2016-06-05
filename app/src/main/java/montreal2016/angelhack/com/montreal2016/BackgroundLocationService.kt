package montreal2016.angelhack.com.montreal2016

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info

/**
 * Created by daniel on 2016-06-04.
 */

class BackgroundLocationService : BroadcastReceiver(), AnkoLogger {
    override fun onReceive(context: Context, intent: Intent) {
        val location: Location? = intent.extras.getParcelable<Location>("com.google.android.location.LOCATION")
        if (location != null) {
            info("got update ${location.longitude}, ${location.latitude}")
            val app = context.applicationContext as Montreal2016App
            app.netService.updateLocation(User(app.username, location.latitude, location.longitude)).subscribe({ res ->
                info("got response ${res.string()}")
            }, { throwable ->
                error("got an error", throwable)
            })
        }
    }

}
