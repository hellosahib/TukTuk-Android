package tech.rtsproduction.tuktuk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toolbar;

public class HistoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView viewRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = findViewById(R.id.toolbar_history);
        viewRecord = findViewById(R.id.listview_history);
        toolbar.setTitle("History");
        setActionBar(toolbar);

        viewRecord.setAdapter(null);
    }
}
