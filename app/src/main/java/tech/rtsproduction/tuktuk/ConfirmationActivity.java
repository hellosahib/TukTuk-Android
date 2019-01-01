package tech.rtsproduction.tuktuk;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.github.igla.ferriswheel.FerrisWheelView;

public class ConfirmationActivity extends FragmentActivity implements OnMapReadyCallback, DatePickerDialog.OnDateSetListener {

    private LatLng startPos, endPos, mapPos;
    private GoogleMap mMap;
    private Button mBookNow, mClose;
    private TextView mDate;
    private Spinner mTime;
    private FerrisWheelView animFerris;

    private final String TAG = getClass().getName();

    private final int DIFF_TIME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        mBookNow = findViewById(R.id.btn_book_now_confirmation);
        mClose = findViewById(R.id.btn_close_confirmation);
        mDate = findViewById(R.id.tv_date_confirmation);
        mTime = findViewById(R.id.spinner_time_confirmation);
        animFerris = findViewById(R.id.anim_ferris_confirmation);
        mTime.setAdapter(null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frame_map_confirmation);
        mapFragment.getMapAsync(this);

        populateSpinnerDate();
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

        mBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmationActivity.this, HistoryActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    //TODO: Enable Confirm Button After Selection of Both Date and Time
    public void enableConfirm() {
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
        /*
        View v = findViewById(R.id.view_offset_confirmation);
        animFerris.setVisibility(View.VISIBLE);
        v.setVisibility(View.VISIBLE);
        animFerris.startAnimation();
        */
    }

    public void setMapPoints() {
        mMap.addMarker(new MarkerOptions().position(startPos));
        mMap.addMarker(new MarkerOptions().position(endPos));
    }


    //TODO: Change 24Hours Timeline to 12Hours Timeline
    public void populateSpinnerDate() {
        String[] values = getResources().getStringArray(R.array.booking_times);
        ArrayList<String> loadedValues = new ArrayList<>();
        //Getting Current Time of Device
        Calendar cc = Calendar.getInstance();
        cc.add(Calendar.HOUR, DIFF_TIME);
        Date t = cc.getTime();
        //Iterating Every Value To See If Time Diff is estimated Time or Not
        for (String s : values) {
            if (checkTiming(t, s)) loadedValues.add(s);
        }
        //Declaration and Initialization Of Spinner Adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loadedValues);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTime.setAdapter(spinnerAdapter);
    }

    //Method To Check If Specified Time @Param(endtime) is @Value(DIFF_TIME) ahead or not
    private boolean checkTiming(Date date, String endtime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            Date date1 = sdf.parse(sdf.format(date));
            Date date2 = sdf.parse(endtime);
            return (date1.before(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
