package montreal2016.angelhack.com.montreal2016;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by daniel on 2016-06-04.
 */
public class SerendipityFragment extends Fragment {

    private ListView listView;
    private Event[] myEvents;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_serendipity, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        Montreal2016App app = (Montreal2016App) getActivity().getApplication();
        app.netService.getEvents(((Montreal2016App)getActivity().getApplication()).getUsername()).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {

                List<Event> myListofevents = response.body();
               CustomList customList = new CustomList(getActivity(),myListofevents.toArray(new Event[myListofevents.size()]));

                listView = (ListView) getView().findViewById(R.id.listView);
                listView.setAdapter(customList);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getActivity().getApplicationContext(),"You Clicked ", Toast.LENGTH_SHORT).show();
                    }
                });

                

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });


        /*
                Setting up List View
         */

//        CustomList customList = new CustomList(getActivity(),);






    }
}
