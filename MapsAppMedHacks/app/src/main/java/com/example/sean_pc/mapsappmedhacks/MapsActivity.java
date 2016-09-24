package com.example.sean_pc.mapsappmedhacks;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap)   {
        mMap = googleMap;
        KmlLayer layer;
        KmlPlacemark pl;
        // Add a marker in Sydney and move the camera\
        try{
         layer= new KmlLayer(googleMap, R.raw.Pollen, getApplicationContext();

            layer.addLayerToMap();
        }catch(java.io.IOException e){
            layer.removeLayerFromMap();
            System.out.println("java");
        }catch(org.xmlpull.v1.XmlPullParserException e){
            layer.removeLayerFromMap();
            System.out.println("xmlpull");

        }
        for(KmlContainer container: layer.getContainers()){
            if(layer.hasContainers()){
                googleMap.addMarker(new MarkerOptions().title(getLayoutInflater().toString()));
            }


        }

        for(KmlPlacemark placemark: layer.getPlacemarks()){
            if(layer.hasPlacemarks()) {
                MarkerOptions m = new MarkerOptions();

//                mMap.addMarker(new MarkerOptions()).getPosition(placemark.getProperty("coordiantes"));
                mMap.addMarker(new MarkerOptions().position());
                mMap.
            }
        }
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }
}
