package tech.rtsproduction.tuktuk.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import tech.rtsproduction.tuktuk.Controller.BookingsListAdapter;
import tech.rtsproduction.tuktuk.Model.BookingData;
import tech.rtsproduction.tuktuk.R;

public class HistoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager pager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = findViewById(R.id.toolbar_history);
        pager = findViewById(R.id.pager_history);
        tabLayout = findViewById(R.id.tab_layout_history);
        toolbar.setTitle("History");
        setSupportActionBar(toolbar);
        pager.setAdapter(new HistoryAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager, true);
        this.registerReceiver(new IntentReceiver(),null);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HistoryActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    private class IntentReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            tabLayout.getTabAt(1).select();
        }
    }
}

//Fragment Adapter

class HistoryAdapter extends FragmentPagerAdapter {
    public HistoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Completed";
            case 1:
                return "Up-Coming";
            case 2:
                return "Cancelled";
        }
        return super.getPageTitle(position);
    }
}

//FRAGMENTS

class Fragment1 extends Fragment {
    public Fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_history_data, container, false);
        ListView listView = root.findViewById(R.id.listview_frag_history_item);
        BookingsListAdapter adapter = new BookingsListAdapter(getContext(), getData());
        listView.setAdapter(adapter);
        return root;
    }

    public ArrayList<BookingData> getData() {
        ArrayList<BookingData> completedData = new ArrayList<>();
        completedData.add(new BookingData("ISBT 1", "Elante Mall 1", "8:00 AM", "31 Jan 2018"));
        completedData.add(new BookingData("ISBT 2", "Elante Mall 2", "7:00 AM", "24 Jan 2018"));
        completedData.add(new BookingData("ISBT 3", "Elante Mall 3", "5:00 PM", "18 Jan 2018"));
        completedData.add(new BookingData("ISBT 4", "Elante Mall 4", "6:00 PM", "12 Jan 2018"));
        return completedData;
    }
}

class Fragment2 extends Fragment {
    public Fragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_history_data, container, false);
        ListView listView = root.findViewById(R.id.listview_frag_history_item);
        BookingsListAdapter adapter = new BookingsListAdapter(getContext(), getData());
        listView.setAdapter(adapter);
        return root;
    }

    public ArrayList<BookingData> getData() {
        ArrayList<BookingData> completedData = new ArrayList<>();
        completedData.add(new BookingData("ISBT 5", "Elante Mall 1", "8:00 AM", "31 Jan 2018"));
        completedData.add(new BookingData("ISBT 6", "Elante Mall 2", "7:00 AM", "24 Jan 2018"));
        return completedData;
    }
}

class Fragment3 extends Fragment {
    public Fragment3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_history_data, container, false);
        ListView listView = root.findViewById(R.id.listview_frag_history_item);
        BookingsListAdapter adapter = new BookingsListAdapter(getContext(), getData());
        listView.setAdapter(adapter);
        return root;
    }

    public ArrayList<BookingData> getData() {
        ArrayList<BookingData> completedData = new ArrayList<>();
        completedData.add(new BookingData("ISBT 7", "Elante Mall 1", "8:00 AM", "31 Jan 2018"));
        return completedData;
    }
}
