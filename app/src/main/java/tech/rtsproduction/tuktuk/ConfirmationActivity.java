package tech.rtsproduction.tuktuk;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ConfirmationActivity extends FragmentActivity implements OnMapReadyCallback, DatePickerDialog.OnDateSetListener {

    private LatLng startPos, endPos, mapPos;
    private GoogleMap mMap;
    private Button mBookNow, mClose;
    private TextView mDate, mTime;
    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frame_map_confirmation);
        mapFragment.getMapAsync(this);
        mBookNow = findViewById(R.id.btn_book_now_confirmation);
        mClose = findViewById(R.id.btn_close_confirmation);
        mDate = findViewById(R.id.tv_date_confirmation);
        mTime = findViewById(R.id.tv_time_confirmation);

        double[] points;
        try {
            points = getIntent().getDoubleArrayExtra("points");
            startPos = new LatLng(points[0], points[1]);
            endPos = new LatLng(points[2], points[3]);
            mapPos = new LatLng((points[0] + points[2]) / 2, (points[1] + points[3]) / 2);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(ConfirmationActivity.this, ConfirmationActivity.this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                c.add(Calendar.DATE, +4);
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePicker.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePicker.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        mDate.setText(dateFormat.format(c.getTime()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapPos, 14));
        setMapPoints();
    }

    public void setMapPoints() {
        mMap.addMarker(new MarkerOptions().position(startPos));
        mMap.addMarker(new MarkerOptions().position(endPos));
    }
}
