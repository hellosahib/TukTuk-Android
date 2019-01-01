package tech.rtsproduction.tuktuk.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tech.rtsproduction.tuktuk.Model.BookingData;
import tech.rtsproduction.tuktuk.R;

public class BookingsListAdapter extends ArrayAdapter<BookingData> {

    private ArrayList<BookingData> data;
    private Context context;

    public BookingsListAdapter(Context context, ArrayList<BookingData> objects) {
        super(context, 0, objects);
        this.context = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history_data, parent, false);
        }
        BookingData item = data.get(position);
        //Find By Id's
        TextView pickup = convertView.findViewById(R.id.tv_pickup_history_item);
        TextView destination = convertView.findViewById(R.id.tv_destination_history_item);
        TextView time = convertView.findViewById(R.id.tv_time_history_item);
        TextView date = convertView.findViewById(R.id.tv_date_history_item);
        //Set Text
        pickup.setText(item.getPickupLocation());
        destination.setText(item.getDropoffLocation());
        time.setText(item.getBookingTime());
        date.setText(item.getmBookingDate());
        return convertView;
    }
}
