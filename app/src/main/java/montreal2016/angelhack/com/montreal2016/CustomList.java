package montreal2016.angelhack.com.montreal2016;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class CustomList extends ArrayAdapter<Event> {
    private Event[] events;
    private Context context;


    public CustomList(Activity context, Event[] events) {
        super(context, R.layout.list_layout, events);
        this.context = context;
        this.events = events;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
        TextView distance = (TextView) listViewItem.findViewById(R.id.distance);
        Event event = events[position];

        Montreal2016App app = (Montreal2016App) context.getApplicationContext();
        String latlng[] = event.getLatlng().split(",");
        Location location = new Location("dummyprovider");
        location.setLatitude(Double.parseDouble(latlng[0]));
        location.setLongitude(Double.parseDouble(latlng[1]));
        if (app.getLocation() != null) {
            float dist = location.distanceTo(app.getLocation()) / 1000;
            DecimalFormat df = new DecimalFormat("#.0");
            distance.setText(df.format(dist) + "km away");
        } else {
            distance.setText("");
        }

        textViewName.setText(event.getTitle());
        Picasso.with(context).load("http://" + event.getImageUrl()).into(image);
        return  listViewItem;
    }
}
