package com.example.taxi

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.taxi.Requests.ProfileRequest
import com.example.taxi.Responses.CarsResponse
import com.example.taxi.Responses.ProfileResponse

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.taxi.databinding.ActivityMapsBinding
import com.example.taxi.queries.ApiClient
import com.google.android.gms.maps.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var apiClient: ApiClient

    private var cars = ArrayList<CarsResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiClient = ApiClient()
        getUserProfile()



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getUserProfile(){
        if(intent.hasExtra("token")){
            val token = intent.getIntExtra("token",0)
            println(token)

//            apiClient.getApiService().profile(ProfileRequest(token = token))
//                .enqueue(object: Callback<ProfileResponse> {
//                    override fun onResponse(
//                        call: Call<ProfileResponse>,
//                        response: Response<ProfileResponse>
//                    ) {
//                        val cameraPosition= CameraPosition.Builder()
//                            .target()
//                    }
//
//                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                        t.printStackTrace()
//                    }
//
//                })

            apiClient.getApiService().cars().enqueue(object : Callback<List<CarsResponse>>{
                override fun onResponse(
                    call: Call<List<CarsResponse>>,
                    response: Response<List<CarsResponse>>
                ) {
                        val freeCars = response.body()
                    if (freeCars != null) {
                        for(car in freeCars.iterator()){
                            cars.add(car)
                        }
                    }
                    cars.forEach{ car ->
                        println("id ${car.id} model ${car.model} lon ${car.lon} lat ${car.lat}")
                    }

//
//                    val carIcon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.car)
//                    cars.forEach { car ->
//
//                    }

                    for(car in cars.iterator()){
                        createMarker(car.lat.toDouble(), car.lon.toDouble(), title =  car.model, iconId = R.drawable.car)
                    }


                }

                override fun onFailure(call: Call<List<CarsResponse>>, t: Throwable) {
                    t.printStackTrace()
                }

            })

        }else{
            println("Что-то пошло не так")
        }
    }
    protected fun createMarker(latitude: Double, longitude:Double, title:String,iconId:Int): Marker? {
        return mMap.addMarker(MarkerOptions()
            .position(LatLng(longitude,latitude))
            .title(title)
            .icon(BitmapDescriptorFactory.fromResource(iconId))
        )
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10f))
    }


}