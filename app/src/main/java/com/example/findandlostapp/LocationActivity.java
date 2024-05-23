package com.example.findandlostapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    ArrayList<Double> item_lat, item_long;
    public static  final ArrrayList<LatLng> locationArrayList ;
    MyDatabaseHelper mydb;

    // creating array list for adding all our locations.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationArrayList = new ArrrayList<LatLng>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        storedata();
    }

    void storedata(){
        Cursor cursor = mydb.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Items lost of Found", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                item_lat.add(cursor.getDouble(),6);
                item_long.add(cursor.getDouble(),7);
                LatLng locat_cord = new LatLng(item_lat, item_long);
                locationArrayList.add(locat_cord);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // inside on map ready method
        // we will be displaying all our markers.
        // for adding markers we are running for loop and
        // inside that we are drawing marker on our map.
        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title("Marker"));

            // below line is use to zoom our camera on map.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }
    }
}