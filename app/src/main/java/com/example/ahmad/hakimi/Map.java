package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
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

public class Map extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, RoutingListener {

    private GoogleMap mMap;
    int [] agentID ;
    String [] agnentName;
    double []agentLat,agentLon;
    LatLng [] sydney1;
    Location myLocation;
    LatLng userLocation,agentloc,hotLoc;
    double latitude,lonitude;
    double  agentLatitude,agentLongitude,hot_event_lat,hot_lon;
    Marker pos;
    String agent_single_name;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimary,R.color.colorAccent,R.color.colorPrimaryDark,R.color.colorPrimary,R.color.primary_dark_material_light};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent3=getIntent();
        agentID = intent3.getIntArrayExtra("agentID");
        agnentName = intent3.getStringArrayExtra("agentName");
        agentLat = intent3.getDoubleArrayExtra("agentsLAt");
       agentLon = intent3.getDoubleArrayExtra("agentLOn");
        agentLongitude=intent3.getDoubleExtra("agent_lon",0);
       agentLatitude=intent3.getDoubleExtra("agent_lat",0);
        agent_single_name=intent3.getStringExtra("agent_name_single");


        hot_event_lat=intent3.getDoubleExtra("hotlat",0);
        hot_lon=intent3.getDoubleExtra("hotlon",0);


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(agentLat!=null) {
            sydney1= new LatLng[agentLat.length];
            for (int i = 0; i < agentLat.length; i++) {
                sydney1[i] = new LatLng((agentLat[i]), agentLon[i]);

            }
            for (int i = 0; i < sydney1.length; i++) {
                mMap.addMarker(new MarkerOptions().position(sydney1[i]).title(agnentName[i]))
                        .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            }
        }
       else  if(agent_single_name!=null)
        {
            agentloc = new LatLng(agentLatitude,agentLongitude);
            mMap.addMarker(new MarkerOptions().position(agentloc).title(agent_single_name))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        }
        else if (hot_event_lat!=0)
        {
            agentloc = new LatLng(hot_event_lat,hot_lon);
            mMap.addMarker(new MarkerOptions().position(agentloc).title("Hot Event"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        if (ActivityCompat.checkSelfPermission(Map.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Map.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Map.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
        if (agentloc != null && userLocation!=null ) {
            Routing routing = new Routing.Builder()
                    .travelMode(Routing.TravelMode.WALKING)
                    .withListener(this)
                    .waypoints(userLocation,agentloc)
                    .build();
            routing.execute();
        }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

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

           // Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(userLocation);
        //options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(agentloc);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        mMap.addMarker(options);

    }

    @Override
    public void onRoutingCancelled() {

    }
}
