package montreal2016.angelhack.com.montreal2016

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_howl.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.startActivity
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by daniel on 2016-06-04.
 */

class HowlFragment : Fragment(), AnkoLogger {
    private lateinit var map: GoogleMap
    private var markers: MutableList<Marker> = mutableListOf()
    private var zoomed = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_howl, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mapFragment = SupportMapFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.container, mapFragment)
            .commit()
        mapFragment.getMapAsync { map ->
            this.map = map
            map.isMyLocationEnabled = true
            howlButton.alpha = 1f
            howlButton.onClick { loadPacks() }

            map.setOnMyLocationChangeListener { location ->
                if (!zoomed) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 16f))
                    zoomed = true
                }
            }

            map.setOnMarkerClickListener { marker ->
                val name = marker.title
                val description = marker.snippet
                startActivity<ProfileActivity>(
                    ProfileActivity.EXTRA_NAME to name,
                    ProfileActivity.EXTRA_DESCRIPTION to description
                )
                true
            }
        }
    }

    private fun loadPacks() {
        val app = activity.application as Montreal2016App
        app.netService.howl(app.name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ packs ->
                info("got the packs ${packs.size}")
                markers.forEach { it.remove() }
                markers = packs.map {
                    map.addMarker(MarkerOptions()
                        .position(LatLng(it.locationLong, it.locationLat))
                        .title(it.packName)
                        .snippet(it.packDescription)
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wolf))
                    )
                }.toMutableList()
            }, { throwable ->
                error("something bad happened", throwable)
            })
    }
}
