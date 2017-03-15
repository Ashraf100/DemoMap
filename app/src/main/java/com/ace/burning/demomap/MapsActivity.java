package com.ace.burning.demomap;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import android.widget.ZoomButtonsController;
import android.widget.ZoomControls;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    ZoomControls zoomControls;
    private static final int MY_PERMISSION_FINE_LOCATION = 101;
    private static final String TAG = MapsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Retrieve the content view that renders the map.
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Styling map in through json code.
        boolean success = googleMap.
                setMapStyle(new MapStyleOptions(getResources()
                        .getString(R.string.style_json)));

        //check point if map style fails.
        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }

        // Add a marker in Sydney and move the camera
        LatLng nepal = new LatLng(28.3949, 84.1240);
        // marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mMap.addMarker(new MarkerOptions().position(nepal).title("Nepal"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nepal));
        mMap.getUiSettings().isZoomControlsEnabled();
        mMap.getUiSettings().isCompassEnabled();
        mMap.getUiSettings().isRotateGesturesEnabled();
        mMap.getUiSettings().isMapToolbarEnabled();

        //To get current location of user by using self permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }


        //Adding single polyline on map.

        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .color(Color.GREEN)
                .geodesic(true)
                .width(3)
                .add(
                        new LatLng(28.3949, 84.1240),
                        new LatLng(28.4456, 84.5270),
                        new LatLng(28.7455, 84.5612),
                        new LatLng(28.7888, 84.7871),
                        new LatLng(29.1001, 84.9701),
                        new LatLng(29.1223, 85.1001)));
        polyline1.setTag("A");


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                }else{
                    Toast.makeText(this, "The application requires location permission to be granted", Toast.LENGTH_SHORT).show();
                    finish();

            }

                break;
        }
    }
}





















