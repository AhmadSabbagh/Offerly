package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class Maps extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, RoutingListener {
    private GoogleMap mMap;
     String [] branchNames ;
    String []  branchPhones ;
    double [] barnchLat;
    double [] branchLon;
    int position;
    Location myLocation;
    double latitude,lonitude;
    LatLng userLocation, sydney;
    Marker pos;
    TextView distance ;
    LatLng [] sydney1;
    Button call;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimary,R.color.colorAccent,R.color.colorPrimaryDark,R.color.colorPrimary,R.color.primary_dark_material_light};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent3=getIntent();
        branchNames = intent3.getStringArrayExtra("branchName");
        branchPhones = intent3.getStringArrayExtra("branchPhone");
        barnchLat = intent3.getDoubleArrayExtra("branchLat");
        branchLon = intent3.getDoubleArrayExtra("branchLon");
        position =intent3.getIntExtra("pos",100000);
        distance = (TextView)findViewById(R.id.tvDistance);
        call =(Button)findViewById(R.id.CallBu);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        sydney1 =new LatLng[barnchLat.length];
   if(position!=100000) {
       if (barnchLat != null && branchLon != null) {
           sydney = new LatLng(barnchLat[position], branchLon[position]);
           mMap.addMarker(new MarkerOptions().position(sydney).title(branchNames[position])).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
           ;
           //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       }
   }
   else
   {
       for (int i = 0; i < barnchLat.length; i++) {
           sydney1[i] = new LatLng((barnchLat[i]),branchLon[i]);

       }
       for (int i = 0; i < sydney1.length; i++) {
           mMap.addMarker(new MarkerOptions().position(sydney1[i]).title(branchNames[i] + " " + branchPhones[i]))
                   .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

           //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydeny[i], 5));
       }
   }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        if (ActivityCompat.checkSelfPermission(Maps.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Maps.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Maps.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            if (!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            myLocation = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
            }

            if (myLocation != null) {
                latitude = myLocation.getLatitude();
                lonitude = myLocation.getLongitude();
                userLocation = new LatLng(latitude, lonitude);
                //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12), 1500, null);
                pos = mMap.addMarker(new MarkerOptions().position(userLocation).title("Your location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13));
            }
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (sydney != null && userLocation!=null) {
           /* PolylineOptions line =
                    new PolylineOptions().add(userLocation, sydney
                    )
                            .width(8).color(Color.RED);

            mMap.addPolyline(line);*/
            Routing routing = new Routing.Builder()
                    .travelMode(Routing.TravelMode.WALKING)
                    .withListener(this)
                    .waypoints(userLocation,sydney)
                    .build();
            routing.execute();
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (barnchLat != null && branchLon != null && position!=100000) {
            float results[] = new float[10];
            Location.distanceBetween(barnchLat[position], branchLon[position], latitude, lonitude, results);
            distance.setVisibility(View.VISIBLE);
            distance.setText("Distance to Target :   " + (int) results[0] / 1000 + " Km");

        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }


    public void Call(View view) {
        if(position!=100000) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + branchPhones[position]));
            startActivity(intent);
        }
        else
        {
            call.setEnabled(false);
        }

    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

          //  Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(userLocation);
        //options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(sydney);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        mMap.addMarker(options);

    }

    @Override
    public void onRoutingCancelled() {

    }
}
