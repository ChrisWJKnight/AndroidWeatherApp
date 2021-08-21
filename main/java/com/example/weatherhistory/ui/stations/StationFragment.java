package com.example.weatherhistory.ui.stations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherhistory.R;
import com.example.weatherhistory.UrlBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

/**
 * Station fragment displays historical data from weather stations selected from the map in historical search.
 * the data is downloaded, stored in the datamodel and a list adapter is used to populate a list based on the JSON retrieved
 */
public class StationFragment extends Fragment {
    private TextView stationKey;
    private ArrayList<StationDataModel> dataModelArrayList;
    private TextView headerText;
    private ListView myListView;
    private Spinner mySpinner;
    private ProgressBar loadingBar;
    private StationListAdapter searchListAdapter;
    private ArrayList<String> yearSelection;

    /**
     * when the view is created the listview, spinner and loadingbar is assigned to the layout ID's
     * the URL is constructed using the station key and correct reason code
     * the requested string is added to an array list and passed to the data model
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View station = inflater.inflate(R.layout.fragment_station, container, false);
        final String stationKey = getArguments().getString("Station");
        mySpinner = station.findViewById(R.id.mySpinner);
        myListView= station.findViewById(R.id.lv);
        loadingBar = station.findViewById(R.id.station_progress);
        loadingBar.setVisibility(View.VISIBLE);
        headerText = station.findViewById(R.id.headerLabel);
        headerText.setText(stationKey+" Weather Station");
        UrlBuilder newURL = new UrlBuilder();
        String URLstring = newURL.buildURL(1,stationKey,null);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            dataModelArrayList = new ArrayList<>();
                            yearSelection = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray(stationKey);
                            for (int i = 0; i < dataArray.length(); i++) {
                                final StationDataModel playerModel = new StationDataModel();
                                final JSONObject dataobj = dataArray.getJSONObject(i);
                                playerModel.setId(dataobj.getInt("id"));
                                playerModel.setYearID(dataobj.getInt("yearID"));
                                playerModel.setYear(dataobj.getString("year"));
                                playerModel.setMonth(dataobj.getInt("month"));
                                playerModel.setTempMax(dataobj.getString("tempmax"));
                                playerModel.setTempMin(dataobj.getString("tempmin"));
                                playerModel.setAirFrost(dataobj.getString("airfrost"));
                                playerModel.setRainFall(dataobj.getString("rainfall"));
                                playerModel.setSunHours(dataobj.getString("sunhours"));
                                dataModelArrayList.add(playerModel);
                            }
                            yearSelection.add("All"); // This for loop populates the filter spinner menu with the available years and increments 12 each loop to skip the 12 entries per year
                            for (int i = 0; i < dataArray.length(); i+=12){

                                final JSONObject dataobj = dataArray.getJSONObject(i);
                                yearSelection.add(dataobj.getString("year"));
                            }
                            setupListview();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        // request queue for volley request
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
        return station;
    }

    /**
     * setupListView hides the loading bar and populates the list with the information stored in the datamodel
     * Listadapter returns result and is displayed in the ListView
     *
     * Spinner is populated with the yearSelection array generated earlier
     * onItemSelected listener passes spinner position to getSelectedYearData
     */
    private void setupListview() {
        loadingBar.setVisibility(View.GONE);
        searchListAdapter = new StationListAdapter(getActivity().getApplicationContext(), dataModelArrayList);
        myListView.setAdapter(searchListAdapter);

        mySpinner.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), simple_list_item_1, yearSelection));
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                int size = yearSelection.size();
                if (position >= 0 && position < size) { // position 0 = "All" on the spinner
                    getSelectedYearData(position);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Selected Category Does not Exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     * filters the listadapter using the yearID to only select years matching
     * @param yearUID created by the position of the spinner
     */
    private void getSelectedYearData(int yearUID) {
        //arraylist to hold selected cosmic bodies
        ArrayList<StationDataModel> filterYear = new ArrayList<>();
        // StationListAdapter adapter;
        int yearID = yearUID;
        if (yearID == 0) {
            searchListAdapter = new StationListAdapter(getActivity().getApplicationContext(), dataModelArrayList);
        } else {
            //filter by yearId
            for (StationDataModel yearSelect : dataModelArrayList) {
                if (yearSelect.getYearID()== yearID) {
                    filterYear.add(yearSelect);
                }
            }
            //create a new listadapter
            searchListAdapter = new StationListAdapter(getActivity().getApplicationContext(), filterYear);
        }
        //set the adapter to listview
        myListView.setAdapter(searchListAdapter);
    }
}