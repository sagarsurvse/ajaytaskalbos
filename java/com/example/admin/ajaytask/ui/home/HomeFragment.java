package com.example.admin.ajaytask.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.ajaytask.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements OnMapReadyCallback
          {
    SupportMapFragment mapFragment;
    Location loc;
    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double lat =0, lng=0;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

              public double latitude;
              public double longitude;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.frg);
        mapFragment.getMapAsync(this);







        return rootView;
    }





              public void onMapReady(GoogleMap googleMap) {

                  mMap = googleMap;

                  mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
                  mMap.setOnMyLocationClickListener(onMyLocationClickListener);
                  enableMyLocationIfPermitted();


                  mMap.setMinZoomPreference(11);
                  mMap.getUiSettings().setZoomControlsEnabled(true);



        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(18.640099, 73.793393), 10));

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

              private void enableMyLocationIfPermitted() {
              if (ContextCompat.checkSelfPermission(getContext(),
                      Manifest.permission.ACCESS_FINE_LOCATION)
                      != PackageManager.PERMISSION_GRANTED) {
                  ActivityCompat.requestPermissions((Activity) getContext(),
                          new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                  Manifest.permission.ACCESS_FINE_LOCATION},
                          LOCATION_PERMISSION_REQUEST_CODE);
              } else if (mMap != null) {
                  mMap.setMyLocationEnabled(true);
              }
          }

              private void showDefaultLocation() {
                  Toast.makeText(getContext(), "Location permission not granted, " +
                                  "showing default location",
                          Toast.LENGTH_SHORT).show();
                  LatLng redmond = new LatLng(18.640099, 73.793393);
                  mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
              }

              @Override
              public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
                  switch (requestCode) {
                      case LOCATION_PERMISSION_REQUEST_CODE: {
                          if (grantResults.length > 0
                                  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                              enableMyLocationIfPermitted();
                          } else {
                              showDefaultLocation();
                          }
                          return;
                      }

                  }
              }

              private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
                      new GoogleMap.OnMyLocationButtonClickListener() {
                          @Override
                          public boolean onMyLocationButtonClick() {
                              mMap.setMinZoomPreference(15);
                              return false;
                          }
                      };

              private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
                      new GoogleMap.OnMyLocationClickListener() {
                          @Override
                          public void onMyLocationClick(@NonNull Location location) {

                              mMap.setMinZoomPreference(12);

                              CircleOptions circleOptions = new CircleOptions();
                              circleOptions.center(new LatLng(location.getLatitude(),
                                      location.getLongitude()));

                              circleOptions.radius(200);
                              circleOptions.fillColor(Color.RED);
                              circleOptions.strokeWidth(6);

                              mMap.addCircle(circleOptions);
                          }
                      };




}

