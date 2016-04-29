package com.example.valerio.lab_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String next[] = {};
        List<String[]> list = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("elenco.csv")));
            for (;;) {
                next = reader.readNext();
                if (next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList comuni = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            comuni.add(list.get(i)[5]);
        }

        final AutoCompleteTextView comune = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, comuni);

        // Set the adapter
        comune.setAdapter(adapter);
        comune.setThreshold(1);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng bergamo = new LatLng(45.70, 9.66);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(bergamo, 15));
        map.addMarker(new MarkerOptions().title("BERGAMO").snippet("Dove ha sede il famosissimo PALEOCAPA").position(bergamo));
    }
}

