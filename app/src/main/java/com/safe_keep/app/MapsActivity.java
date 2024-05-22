package com.safe_keep.app;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.safe_keep.app.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng telAviv = new LatLng(32, 34);
        mMap.addMarker(new MarkerOptions().position(telAviv).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(telAviv));


        // Set a long click listener on the map
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                // Show a dialog to input the radius TODO:DRAGABLE RADIUS
                showRadiusInputDialog(latLng);
            }
        });
    }

    private void showRadiusInputDialog(final LatLng latLng) {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Radius");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String radiusStr = input.getText().toString();
                if (!radiusStr.isEmpty()) {
                    double radius = Double.parseDouble(radiusStr);
                    drawCircle(latLng, radius);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void drawCircle(LatLng latLng, double radius) {
        // Add a circle to the map
        double radiusInKM = radius * 1000;// change radius from meters to KM
        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(radiusInKM)
                .strokeColor(Color.BLUE)
                .fillColor(0x30ff0000) // 30% transparency
                .strokeWidth(2);
        mMap.addCircle(circleOptions);

        // Move the camera to the circle
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}