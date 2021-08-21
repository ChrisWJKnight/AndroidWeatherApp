package com.example.weatherhistory.ui.onthisday;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherhistory.R;
import com.example.weatherhistory.UrlBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnThisDayFragment extends Fragment implements LocationListener {
    private LocationManager locationManager;
    private double lat;
    private double lon;
    private String latString;
    private String lonString;
    private String result;
    TextView titleText, currentTemp,currentTempFeel, currentTempMin, currentTempMax, currentWind, currentCloud, currentDesc;
    ImageView currentImg;
    Button refresh;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION };

    @SuppressLint("MissingPermission")//suppress warning about location permission request. request is implemented
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_onthisday, container, false);

        titleText = root.findViewById(R.id.text_onthisday); //TextView Variables are assigned to layout ID's
        currentTemp = root.findViewById(R.id.current_temp);
        currentTempFeel = root.findViewById(R.id.current_temp_feel);
        currentTempMax = root.findViewById(R.id.current_temp_max);
        currentTempMin = root.findViewById(R.id.current_temp_min);
        currentCloud = root.findViewById(R.id.current_cloud);
        currentWind = root.findViewById(R.id.current_wind);
        currentDesc = root.findViewById(R.id.current_desc);
        currentImg = root.findViewById(R.id.current_img);
        refresh = root.findViewById(R.id.refresh_button);
        checkPermissions();
        getLocation();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


       return root;
    }

    /**
     * getLocation requests the last known location of the device, converts the Latitude and Longitude
     * from Double to String and passes them to getWeather()
     */
    @SuppressLint("MissingPermission")//suppress warning about location permission request. request is implemented
    private void getLocation() {
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 20,this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lat = location.getLatitude();
        lon = location.getLongitude();
        latString = String.valueOf(lat);
        lonString = String.valueOf(lon);
        getWeather(latString,lonString);
    }

    /**
     * checkPermissions() finds out if the app has permission to "ACCESS_FINE_LOCATION"
     * if permission is not granted the missing permission is requested
     */
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    /**
     * getWeather() requests UrlBuilder to construct the required URL for request
     * using latString, lonString and the reason code.
     * if a successful response is received the contents of the JSON is inserted in to the UI
     * @param latString String holds latitude information converted from Double
     * @param lonString String holds longitude information converted from Double
     */
       public void getWeather(String latString,String lonString){
           final UrlBuilder newURL = new UrlBuilder();
           String URLstring = newURL.buildURL(2,latString,lonString);
           StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                   new com.android.volley.Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           try {
                               JSONObject obj = new JSONObject(response);

                               result = obj.get("cod").toString();
                               if (result.matches("200")) {
                               JSONArray weatherObj = obj.getJSONArray("weather"); //JSON Arrays need to be extracted to JSONArray
                               JSONObject weatherArr = weatherObj.getJSONObject(0); //Converts the JSONArray index 0 to JSONObject
                               currentTemp.setText(new StringBuilder().append("Currently:\n").append(obj.getJSONObject("main").getString("temp")).append("\u2103").toString()); //TextViews Strings are built with data from the JSONObject
                               currentTempFeel.setText(new StringBuilder().append("Feels Like:\n").append(obj.getJSONObject("main").getString("feels_like")).append("\u2103").toString());
                               currentTempMax.setText(new StringBuilder().append("Max Temp:\n").append(obj.getJSONObject("main").getString("temp_max")).append("\u2103").toString());
                               currentTempMin.setText(new StringBuilder().append("Min Temp:\n").append(obj.getJSONObject("main").getString("temp_min")).append("\u2103").toString());
                               currentCloud.setText(new StringBuilder().append("Cloud Cover:\n").append(obj.getJSONObject("clouds").getString("all")).append("%").toString());
                               currentWind.setText(new StringBuilder().append("Wind Speed:\n").append(obj.getJSONObject("wind").getString("speed")).append(" Meter p/s").toString());
                               titleText.setText(obj.get("name").toString());
                               currentDesc.setText(new StringBuilder().append("Description:  ").append(weatherArr.getString("description")).toString());
                               String imgCode = weatherArr.getString("icon");
                               String imgURL = newURL.buildURL(3, imgCode, null); //URL for weather image is requested
                               Picasso.get().load(imgURL).into(currentImg); //Picasso is used to fetch ImageView src from URL to display current weather image

                           }
                           } catch (JSONException e) {
                               e.printStackTrace();
                               titleText.setText(R.string.data_fetch_error); //Changes page title to indicate an error.
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

           RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext()); //requests new volley request to be queued
           requestQueue.add(stringRequest);

       }

    /**
     * If permission is not granted this method informs the user that location can't be pulled.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressLint("MissingPermission")//suppress warning about location permission request. request is implemented
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity().getApplicationContext(), "Required permission '" + permissions[index]
                                + "' not granted, unable to get location", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                break;
        }
    }

    @Override //if location is changed update setLat, setLon in CurrentWeatherDataModel
    public void onLocationChanged(Location location) {
        CurrentWeatherDataModel setLatLon = new CurrentWeatherDataModel();
        setLatLon.setLat(location.getLatitude());
        setLatLon.setLon(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}


