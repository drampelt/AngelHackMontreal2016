package montreal2016.angelhack.com.montreal2016;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        // TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
        Event event = events[position];

        textViewName.setText(event.getTitle());
        // textViewDesc.setText(desc[position]);
        // image.setImageResource(image[position]);
        return  listViewItem;
    }
}
