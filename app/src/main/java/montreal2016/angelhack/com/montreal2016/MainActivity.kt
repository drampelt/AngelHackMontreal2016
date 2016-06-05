package montreal2016.angelhack.com.montreal2016

import android.app.PendingIntent
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AnkoLogger {

    private lateinit var gapi: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as Montreal2016App

        setSupportActionBar(toolbar)

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.drawer_howl -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HowlFragment())
                        .commit()
                }
                R.id.drawer_pack -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PackProfileFragment())
                        .commit()
                }
                R.id.drawer_serendipity -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SerendipityFragment())
                        .commit()
                }
            }
            supportActionBar?.title = item.title
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HowlFragment())
            .commit()
        supportActionBar?.title = "Howl"

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val header = navigationView.getHeaderView(0)
        val picture = header.findViewById(R.id.profilePicture) as ImageView
        Picasso.with(this@MainActivity)
            .load("http://placekitten.com/152/${147 + Random().nextInt(10)}")
            .into(picture)
        val username = header.findViewById(R.id.username) as TextView
        username.text = app.username

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
            .setInterval(120000L)
            .setFastestInterval(15000L)
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        val intent = Intent(this, BackgroundLocationService::class.java)
        val locationIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        LocationServices.FusedLocationApi.requestLocationUpdates(gapi, locationRequest, locationIntent)
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }
}
