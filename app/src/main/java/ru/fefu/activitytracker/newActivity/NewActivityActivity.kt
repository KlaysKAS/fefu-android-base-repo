package ru.fefu.activitytracker.newActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.BitmapTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.ParentFragmentManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.db.entity.Coordinates
import ru.fefu.activitytracker.gps.LocListener
import ru.fefu.activitytracker.gps.LocListenerInterface
import kotlin.math.roundToInt

class NewActivityActivity : AppCompatActivity(), ParentFragmentManager, LocListenerInterface {
    private var distance: Double = 0.0
    private var curId = -1
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocListener
    private var lastLocation: Location? = null

    private val polyline by lazy {
        Polyline().apply {
            outlinePaint.color = ContextCompat.getColor(
                this@NewActivityActivity,
                R.color.purple_700
            )
        }
    }
    private lateinit var map: MapView

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_activity)
//        App.INSTANCE.db.activityDao().dropUnfinishedActivities()
//        App.INSTANCE.db.activityDao().dropCoordinates()
        init()

        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.new_activity_flow,
                NewActivityChooseFragment.newInstance(),
                "chooseActivity"
            )
            commit()
        }

        val cardView: CardView = findViewById(R.id.card_with_settings)
        cardView.setBackgroundResource(R.drawable.up_corner_25dp_shape)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 230 && resultCode == RESULT_OK) {
            checkPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 228 && grantResults[0] == RESULT_OK) {
            checkPermissions()
        }
        else {
//            Toast.makeText(this, "Без доступа к геолокации мы не сможем отслеживать вашу активность :(", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 228)
        }
        else {
            checkGPSEnabled {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000L, 4f, locationListener)
                showUserLoc()
            }
        }
    }

    private fun init() {
        // GPS
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = LocListener()
        locationListener.setLocListenerI(this)

        // MAP
        Configuration.getInstance().load(this, getPreferences(Context.MODE_PRIVATE))
        map = findViewById(R.id.map_new_activity)
        initMap()

        checkPermissions()
    }

    private fun initMap() {
        map.minZoomLevel = 4.0
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        val eventReciever = object: MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean = false

            override fun longPressHelper(p: GeoPoint?): Boolean = false
        }

        map.overlays.add(MapEventsOverlay(eventReciever))
        map.overlayManager.add(polyline)
    }

    @SuppressLint("SetTextI18n")
    override fun onLocationChanged(loc: Location) {
        curId = App.INSTANCE.db.activityDao().getLastActivity()?.id?:-1
        if (curId > -1) {
            App.INSTANCE.db.activityDao().insertCoordinates(
                Coordinates(
                    0,
                    curId,
                    loc.longitude,
                    loc.latitude
                )
            )
            polyline.addPoint(GeoPoint(loc))
        }
        lastLocation = loc
        Log.i("DISTANCE", distance.toString())
    }

    fun showUserLoc() {
        val locationOverlay = MyLocationNewOverlay(
            object: GpsMyLocationProvider(applicationContext) {
                private var mapMoved = false
                override fun onLocationChanged(location: Location) {
                    location.removeBearing()
                    super.onLocationChanged(location)
                    if (mapMoved) return
                    mapMoved = true
                    map.controller.animateTo(
                        GeoPoint(
                            location.latitude,
                            location.longitude
                        ),
                        16.0,
                        1000
                    )
                }
            },
            map
        )
        val locationIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_user_location)
        locationOverlay.setDirectionArrow(locationIcon, locationIcon)
        locationOverlay.setPersonHotspot(locationIcon.width / 2f, locationIcon.height.toFloat())
        locationOverlay.enableMyLocation()
        map.overlays.add(locationOverlay)
    }

    private fun checkGPSEnabled(success: (LocationSettingsResponse) -> Unit) {
        LocationServices.getSettingsClient(this)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY))
                    .build()
            )
            .addOnSuccessListener(success)
            .addOnFailureListener {
                if (it is ResolvableApiException) {
                    it.startResolutionForResult(this, 230)
                }
                Log.d("NewActivityActivity", "GPS is OFF")
                it.printStackTrace()
            }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun getActivitiesFragmentManager(): FragmentManager = supportFragmentManager
}
