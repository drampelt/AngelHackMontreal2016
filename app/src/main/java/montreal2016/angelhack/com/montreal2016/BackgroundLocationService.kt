package montreal2016.angelhack.com.montreal2016

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.widget.Toast
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by daniel on 2016-06-04.
 */

class BackgroundLocationService : BroadcastReceiver(), AnkoLogger {
    override fun onReceive(context: Context, intent: Intent) {
        intent.extras.keySet().forEach { info("key $it") }
        val location: Location? = intent.extras.getParcelable<Location>("com.google.android.location.LOCATION")
        if (location != null) {
            Toast.makeText(context, "got update ${location.longitude} ${location.latitude}", Toast.LENGTH_SHORT).show()
            info("got update ${location.longitude}, ${location.latitude}")
        }
    }

}
