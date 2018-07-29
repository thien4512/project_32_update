package com.example.thien.project_32;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> mylist_ll = new ArrayList<LatLng>();
    private LatLng tt;
    LatLng my_ll = new LatLng(10.774136, 106.651757);
    LatLng my_ll_2 = new LatLng(10.776136, 106.654757);
    Button Clear;
    String my_local;
    LatLng my_ll_3;
    double my_lat, my_lng;
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Clear = (Button) findViewById(R.id.clear_position);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                mylist_ll = new ArrayList<LatLng>();
            }
        });
    }

    private  BroadcastReceiver  bReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            //put here whaterver you want your activity to do with the intent received
           // my_local = intent.getDataString();
            //my_local = intent.getStringExtra("Lat");
            my_lat = Double.parseDouble(intent.getStringExtra("Lat"));
            my_lng = Double.parseDouble(intent.getStringExtra("Lng"));
            my_ll_3 = new LatLng(my_lat,my_lng);
            mylist_ll.add(my_ll_3);
            drawPolyLineOnMap(mylist_ll);
            //Toast.makeText(getApplicationContext(),String.valueOf(my_lat)+","+String.valueOf(my_lng),Toast.LENGTH_LONG).show();
            if(a==0) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mylist_ll.get(0)));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
                a = 1;
            }
        }
    };
    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(bReceiver, new IntentFilter("message"));
    }
    protected void onPause (){
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bReceiver);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(10.773136, 106.551757);
        //LatLng sydney = mylist_ll.get(0);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("My address"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.addMarker(new MarkerOptions().position(my_ll).title("My Address 2"));

        //mylist_ll.add(sydney);
        //mylist_ll.add(my_ll);
        //mylist_ll.add(my_ll_2);
        //drawPolyLineOnMap(mylist_ll);

    }

    // Draw polyline on map
    public void drawPolyLineOnMap(List<LatLng>list) {
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(5);
        polyOptions.addAll(list);

        mMap.clear();
        mMap.addPolyline(polyOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }

        final LatLngBounds bounds = builder.build();
        mMap.addMarker(new MarkerOptions().position(list.get(0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name_2)).title("Start:"+my_javaclass.name));
        mMap.addMarker(new MarkerOptions().position(list.get(list.size()-1)).title(my_javaclass.name));

        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,30);
       // mMap.animateCamera(cu);
    }

}
