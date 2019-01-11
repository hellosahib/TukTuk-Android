package tech.rtsproduction.tuktuk.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import tech.rtsproduction.tuktuk.ProfileActivity;
import tech.rtsproduction.tuktuk.R;
import tech.rtsproduction.tuktuk.SupportActivity;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback,SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mNavigationBtn , mConfirmBtn ;
    private DrawerLayout mDrawerLayout;
    private GoogleMap mMap;
    private NavigationView mNavigationView;
    private PlaceAutocompleteFragment locationFromFragment, locationToFragment;
    private Place fromLoc, toLoc;
    private LinearLayout mFromPlaces, mToPlaces;

    private static final int DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mNavigationBtn = findViewById(R.id.btn_navigation_menu_main);
        mNavigationView = findViewById(R.id.nav_view_main);
        mDrawerLayout = findViewById(R.id.drawer_layout_main);
        //Changing Color Of Confirm Button
        mConfirmBtn = findViewById(R.id.btn_confirm_main);
        mConfirmBtn.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_green_dark),PorterDuff.Mode.SRC_ATOP);
        mFromPlaces = findViewById(R.id.ll_places_from_main);
        mToPlaces = findViewById(R.id.ll_places_to_main);

        locationFromFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.frag_place_from_main);
        locationToFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.frag_place_to_main);
        setupPlacesFragment();

        //MARK: ONCLICK LISTENER
        mNavigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_trips:{
                        startActivity(new Intent(MainActivity.this,HistoryActivity.class));
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                    case R.id.nav_profile:{
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                    case R.id.nav_support:{
                        startActivity(new Intent(MainActivity.this,SupportActivity.class));
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                    case R.id.nav_emergency:{
                        //TODO:Implement Emergency Call
                        Toast.makeText(MainActivity.this, "To be Implemented", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromLoc != null) {
                    mConfirmBtn.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_orange_dark),PorterDuff.Mode.SRC_ATOP);
                    mConfirmBtn.setText("Looks Good!");
                    mConfirmBtn.setEnabled(false);
                    swapVisibility(mToPlaces, mFromPlaces);
                }
                if (toLoc != null) {
                    startActivity(new Intent(MainActivity.this, ConfirmationActivity.class).putExtra("points", new double[]{
                            fromLoc.getLatLng().latitude, fromLoc.getLatLng().longitude,
                            toLoc.getLatLng().latitude, toLoc.getLatLng().longitude
                    }));
                }
            }
        });
        setupSharedPref();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setupSharedPref(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        updateProfile(sharedPreferences.getString(getString(R.string.pref_name),getString(R.string.default_name)),sharedPreferences.getString(getString(R.string.pref_mobile),getString(R.string.default_phone)));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void updateProfile(String name,String phone){
        View header = mNavigationView.getHeaderView(0);
        TextView nameField = header.findViewById(R.id.tv_name_navheader);
        TextView phoneField = header.findViewById(R.id.tv_phone_navheader);
        if(name!=null) nameField.setText(name);
        if(phone!=null) phoneField.setText(phone);
    }

    private void setupPlacesFragment() {
        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                .build();
        locationFromFragment.setFilter(filter);
        locationFromFragment.setHint("From");
        locationToFragment.setFilter(filter);
        locationToFragment.setHint("To");

        locationFromFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                fromLoc = place;
                updateMap(place);
                mConfirmBtn.setEnabled(true);
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        locationToFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                toLoc = place;
                updateMap(place);
                mConfirmBtn.setEnabled(true);
            }

            public void onError(Status status) {
            }
        });
    }

    private void swapVisibility(LinearLayout l1, LinearLayout l2) {
        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.INVISIBLE);
    }

    public void updateMap(Place p) {
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p.getLatLng(), DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().position(p.getLatLng()).title(p.getName().toString()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.pref_name)))  updateProfile(sharedPreferences.getString(getString(R.string.pref_name),getString(R.string.default_name)),null);
        if(key.equals(getString(R.string.pref_mobile)))  updateProfile(null,sharedPreferences.getString(getString(R.string.pref_mobile),getString(R.string.default_phone)));
    }
}
