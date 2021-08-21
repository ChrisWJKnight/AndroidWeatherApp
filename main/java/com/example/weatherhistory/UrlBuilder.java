package com.example.weatherhistory;

import com.example.weatherhistory.ui.onthisday.CurrentWeatherDataModel;
import com.example.weatherhistory.ui.stations.StationDataModel;

public class UrlBuilder {

    public String buildURL(int reason,String inputA, String inputB){
        StationDataModel stationURL = new StationDataModel();
        CurrentWeatherDataModel weatherURL = new CurrentWeatherDataModel();
        int reasonCode = reason;
        StringBuilder url = new StringBuilder();
        if(reasonCode==1) {
            String newURL = stationURL.getStationAPI();
            url.append(newURL);
            url.append("?station=");
            url.append(inputA);
        }
        if(reasonCode==2){
            String newURL = weatherURL.getCurrentWeatherURL();
            String newAPI = weatherURL.getCurrentWeatherAPI();
            url.append(newURL);
            url.append("?lat=");
            url.append(inputA);
            url.append("&lon=");
            url.append(inputB);
            url.append("&APPID=");
            url.append(newAPI);
            url.append("&units=metric");
        }
        if(reasonCode==3){
            String newURL = weatherURL.getWeatherImgURL();
            url.append(newURL);
            url.append(inputA);
            url.append("@2x.png");
        }
    return url.toString();
    }
}
