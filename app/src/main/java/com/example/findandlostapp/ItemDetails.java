package com.example.findandlostapp;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class ItemDetails extends Activity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    EditText name, phone, desc, date, location;
    FusedLocationProviderClient fusedLocationProviderClient;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button add_btn, getloc;

    public double long_tude;
    public double lat_tude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        name = findViewById(R.id.Name_txt);
        phone = findViewById(R.id.Phone_txt);
        desc = findViewById(R.id.desc_txt);
        date = findViewById(R.id.Date_txt);
        location = findViewById(R.id.Address_txt);
        add_btn = findViewById(R.id.submit_btn);
        radioGroup = findViewById(R.id.radioGroup);
        getloc = findViewById(R.id.getCurrentloc);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getlastlocation();
                location.setText("Wattle Avenue, Glen Huntly Vic 3163, Australia");
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) group.findViewById(checkedId);
                //Toast.makeText(ItemDetails.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAkaLQHWJdivYecsddU70x9hWGL_6VKq_k");
        }

        location.setFocusable(true);
        location.setOnClickListener(v-> {

                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList).build(ItemDetails.this);
                startActivityForResult(intent,1);

        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ItemDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(ItemDetails.this);
                mydb.add_item(radioButton.getText().toString().trim(),
                        name.getText().toString().trim(),
                        phone.getText().toString().trim(),
                        desc.getText().toString().trim(),
                        date.getText().toString().trim(),
                        location.getText().toString().trim(),
                        lat_tude,
                        long_tude
                );
                Intent intent = new Intent(ItemDetails.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }

    private void getlastlocation() {
        askPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location loc) {
                    Geocoder geocoder = new Geocoder(ItemDetails.this, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                        Toast.makeText(getApplicationContext(),addresses.get(0).toString(),Toast.LENGTH_SHORT).show();
                        location.setText(addresses.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(ItemDetails.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},1);
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getlastlocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 || resultCode == RESULT_OK){

           Place place = Autocomplete.getPlaceFromIntent(data);
            Toast.makeText(getApplicationContext(),place.getAddress().toString(),Toast.LENGTH_SHORT).show();
            lat_tude = place.getLatLng().latitude;
            long_tude = place.getLatLng().longitude;
            location.setText(place.getAddress().toString());

    }else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();

    }
}
}