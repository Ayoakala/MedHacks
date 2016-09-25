package com.example.sean_pc.mapsappmedhacks;

import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;

import com.google.maps.android.kml.KmlPlacemark;

import com.google.maps.android.kml.*;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.sean_pc.mapsappmedhacks.R.raw.pollen;
import static com.example.sean_pc.mapsappmedhacks.R.raw.sulfatedata;

import com.google.android.gms.location.Geofence;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private LocationRequest mLocationRequest;
    private ArrayList<MyGeof> a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API).build();
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        ArrayList<MyGeof>[] a = (ArrayList<MyGeof>[])new ArrayList[7];
        /* initialize coordinates */
        MyGeof x = new MyGeof("one",-111.3333001408167,42,1000,250000,(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT)); /* id lat long radius exp transition */
        x.toGeofence();

        MyGeof y = new MyGeof("one",-57.785,-51.69099979883324,1000,250000,(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT)); /* id lat long radius exp transition */
        y.toGeofence();

        MyGeof z = new MyGeof("one",15.25,-23.41669985918327,1000,250000,(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT)); /* id lat long radius exp transition */
        z.toGeofence();

        MyGeof w = new MyGeof("one",28.75,-24.43330014081674,1000,250000,(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT)); /* id lat long radius exp transition */
        w.toGeofence();

        MyGeof zz = new MyGeof("one",0.00000, 6.2718281828,100000,250000,(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT)); /* id lat long radius exp transition */
        zz.toGeofence();

        MyGeof b = new MyGeof("one",-16.1415926, 99.31,1000,80000,(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT)); /* id lat long radius exp transition */
        b.toGeofence();

        MyGeof c = new MyGeof("one",-7.5,154.28219,1000,120000,(Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_EXIT)); /* id lat long radius exp transition */
        c.toGeofence();
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
    @Override

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        KmlLayer layer = null;
        KmlLayer sulferData = null;

        try {
            layer = new KmlLayer(googleMap, pollen, getApplicationContext());
            sulferData = new KmlLayer(googleMap, sulfatedata, getApplicationContext());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        KmlPlacemark pl;
        // Add a marker in Sydney and move the camera\


        // Add a marker in Sydney and move the camera
        //layer= new KmlLayer(googleMap, pollen, getApplicationContext());

        try {
            layer = new KmlLayer(googleMap, pollen, getApplicationContext());

            layer.addLayerToMap();
        } catch (java.io.IOException e) {
            System.out.println("java");
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            layer.removeLayerFromMap();
            System.out.println("xmlpull");

        }
        for (KmlPlacemark placemark : layer.getPlacemarks()) {
            if (layer.hasPlacemarks()) {

                mMap.addMarker(new MarkerOptions()).setPosition(new LatLng(placemark.getProperty("coordinates").indexOf(0), placemark.getProperty("coordinates").indexOf(1)));
            }
        }

        for (KmlContainer container : layer.getContainers()) {
            if (layer.hasContainers()) {
                googleMap.addMarker(new MarkerOptions().title(getLayoutInflater().toString()));
            }


        }

        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "We are now connected, Yay!");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(location == null){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }else{
            handleNewLocation(location);
        }
    }


    public void handleNewLocation(Location location){
        Log.d(TAG,location.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection was suspended, Boo!!!");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
}
    @Override
    public void onResume(){
        super.onResume();
        mGoogleApiClient.connect();
    }
    @Override
    public void onPause(){
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
    
    public static void requestGeolocation(){
        String apiKey  ;

    }
}
