package montreal2016.angelhack.com.montreal2016

import android.app.PendingIntent
import android.content.Intent
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AnkoLogger {

    private lateinit var gapi: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gapi = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
    }

    override fun onStart() {
        gapi.connect()
        super.onStart()
    }

    override fun onStop() {
        gapi.disconnect()
        super.onStop()
    }

    override fun onConnected(connectionHint: Bundle?) {
        val location: Location? = LocationServices.FusedLocationApi.getLastLocation(gapi)
        if (location != null) {
            info("got location ${location.latitude}, ${location.longitude}")
        }

        val locationRequest = LocationRequest()
        locationRequest.interval = 5000L
        locationRequest.fastestInterval = 500L
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val intent = Intent(this, BackgroundLocationService::class.java)
        val locationIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        LocationServices.FusedLocationApi.requestLocationUpdates(gapi, locationRequest, locationIntent)
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }
}
