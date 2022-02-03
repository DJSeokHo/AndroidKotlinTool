package com.swein.androidkotlintool.main.examples.googlemapexample

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.googlemapexample.popup.GoogleMapPopupView
import java.io.InputStream
import java.net.URL


class GoogleMapExampleActivity : AppCompatActivity() {

    private var dataList = mutableListOf<FilmingLocationData>()
    private var markerList = mutableListOf<Marker>()

    private var handler = Handler(Looper.getMainLooper())

    // if you need
//    private lateinit var googleMap: GoogleMap

    private var googleMapPopupView: GoogleMapPopupView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map_example)

        val fragmentMap = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        fragmentMap.getMapAsync { googleMap ->

            // if you need
//            this.googleMap = googleMap

            initData()

            mapSetting(googleMap)
            initMap(googleMap)
            initMarker(googleMap)
            setMarkerListener(googleMap)
        }
    }

    private fun mapSetting(googleMap: GoogleMap) {

        googleMap.setMinZoomPreference(1f)
        googleMap.setMaxZoomPreference(20f)
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = true
        googleMap.uiSettings.isCompassEnabled = true
    }

    private fun initData() {

        dataList.clear()

        dataList.add(
            FilmingLocationData(
                nameInDrama = "효산고등학교(Hyosan High School)",
                realName = "성희여자고등학교(SEONGHUI GIRL'S HIGH SCHOOL)",
                description = "The school in drama",
                lat = 36.551891,
                long = 128.728980,
                imageUrl = "https://w.namu.la/s/68b27bf6383d06d437fadbbc626cf48c684d984c0d241c0625596d0b5f068f95708c01cc5824014f09135642826366df49d92bc94fd0d0934f7e68937e9d959d41721fea15d6f1c15bd6aba22fa1c893"
            )
        )

        dataList.add(
            FilmingLocationData(
                nameInDrama = "청산치킨(Cheongsan Chicken)",
                realName = "썬더치킨(Thunder Chicken)",
                description = "The chicken shop in drama",
                lat = 37.584018,
                long = 126.997401,
                imageUrl = "https://blog.kakaocdn.net/dn/bSSRgD/btrr9g3qqGz/NiUm5q4lqUcpZrYJWkZyk0/img.png"
            )
        )

        dataList.add(
            FilmingLocationData(
                description = "The street of zombies escaping",
                lat = 37.483571,
                long = 126.856643
            )
        )

        dataList.add(
            FilmingLocationData(
                nameInDrama = "rich student's apartment",
                realName = "",
                description = "The apartment in drama",
                lat = 37.280256,
                long = 127.081170,
                imageUrl = "https://postfiles.pstatic.net/MjAyMjAxMjlfMjM4/MDAxNjQzNDEyMjQ3Mjkz.pvAtObDxkCZIxFLXmsTMylb_yZ_GKEIUKjlQ5W5cMLUg.4Hhcu_Fii0MHRBmOUHTCnG8I-RY5K2OlpmxRMX7iPfYg.JPEG.yellbs/output_1009409374.jpg?type=w773"
            )
        )
    }

    private fun initMap(googleMap: GoogleMap) {
        // move to init position
        val seoul = LatLng( 37.539705, 126.985277)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 7f))
    }

    private fun initMarker(googleMap: GoogleMap) {

        for (marker in markerList) {
            marker.remove()
        }

        markerList.clear()

        Thread {

            for (item in dataList) {

                val bitmapResult = if (item.imageUrl == "") {
                    getBitmap(resources, R.drawable.drame_image, true)
                }
                else {
                    getBitmap(item.imageUrl, true)
                }

                bitmapResult?.let { bitmap ->

                    handler.post {

                        googleMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(item.lat, item.long))
                                .title(item.description)
                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                        )?.also { marker ->
                            markerList.add(marker)
                        }
                    }
                }

            }

        }.start()
    }

    private fun setMarkerListener(googleMap: GoogleMap) {

        googleMap.setOnMarkerClickListener { marker ->

            Thread {

                val index = markerList.indexOf(marker)

                val data = dataList[index]

                val info = "${data.nameInDrama}\n\n" +
                        "${data.realName}\n\n" +
                        data.description

                val bitmapResult = if (data.imageUrl == "") {
                    getBitmap(resources, R.drawable.drame_image)
                }
                else {
                    getBitmap(data.imageUrl)
                }

                bitmapResult?.let { bitmap ->

                    handler.post {

                        // open custom info popup
                        googleMapPopupView = GoogleMapPopupView(
                            context = this,
                            bitmap = bitmap,
                            info = info
                        )

                        findViewById<FrameLayout>(R.id.container).addView(googleMapPopupView)
                    }

                }

            }.start()

            false
        }

    }

    private fun getBitmap(url: String, isThumbnail: Boolean = false): Bitmap? {

        return try {
            val inputStream: InputStream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            if (isThumbnail) {
                Bitmap.createScaledBitmap(bitmap, 100, 100, false)
            }
            else {
                bitmap
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getBitmap(resources: Resources, resource: Int, isThumbnail: Boolean = false): Bitmap? {

        return try {
            val bitmap = BitmapFactory.decodeResource(resources, resource)

            if (isThumbnail) {
                Bitmap.createScaledBitmap(bitmap, 100, 100, false)
            }
            else {
                bitmap
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onBackPressed() {

        if (googleMapPopupView != null) {
            findViewById<FrameLayout>(R.id.container).removeView(googleMapPopupView)
            googleMapPopupView = null
            return
        }

        finish()
    }
}

data class FilmingLocationData(
    var nameInDrama: String = "",
    var realName: String = "",
    var description: String = "",
    var lat: Double = 0.0,
    var long: Double = 0.0,
    var imageUrl: String = ""
)