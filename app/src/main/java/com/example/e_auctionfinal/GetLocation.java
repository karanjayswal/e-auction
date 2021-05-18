package com.example.e_auctionfinal;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class GetLocation extends FragmentActivity implements OnMapReadyCallback {
    String id, name, desc, price, owner, url;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    String location;
    DatabaseReference reference;
    Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        name = getIntent().getStringExtra("name");
        desc = getIntent().getStringExtra("desc");
        price = getIntent().getStringExtra("price");
        owner = getIntent().getStringExtra("owner");
        url = getIntent().getStringExtra("url");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Items").child(String.valueOf(System.currentTimeMillis()));
        searchView = findViewById(R.id.sv_location);
        progressDialog = new ProgressDialog(GetLocation.this);
        progressDialog.setTitle("Getting the location");
        progressDialog.setMessage("Please Wait......");


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(GetLocation.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    address = addressList.get(0);

                    LatLng latLng = new LatLng(address.getLatitude()
                            , address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    public void SetLocation(View view) {
        progressDialog.show();
//        loc.setLatitude(String.valueOf(address.getLatitude()));
//        loc.setLongitude(String.valueOf(address.getLongitude()));
        Items model = new Items();
        model.setUrl(url);
        model.setDesc(desc);
        model.setName(name);
        model.setBaseprice(price);
        model.setOwner(owner);
        model.setLongitude(String.valueOf(address.getLongitude()));
        model.setLatitude(String.valueOf(address.getLatitude()));
        model.setPlacename(location);

        reference.setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(GetLocation.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(GetLocation.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}