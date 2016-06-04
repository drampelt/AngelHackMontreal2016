package montreal2016.angelhack.com.montreal2016

import android.app.PendingIntent
import android.content.Intent
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AnkoLogger {

    private lateinit var gapi: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.drawer_howl -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HowlFragment())
                        .commit()
                }
                R.id.drawer_serendipity -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SerendipityFragment())
                        .commit()
                }
            }
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

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
            .setInterval(10000L)
            .setFastestInterval(5000L)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        val intent = Intent(this, BackgroundLocationService::class.java)
        val locationIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        LocationServices.FusedLocationApi.requestLocationUpdates(gapi, locationRequest, locationIntent)
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }
}
