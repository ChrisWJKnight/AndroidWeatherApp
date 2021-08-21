package com.example.weatherhistory.ui.onthisday;

public class CurrentWeatherDataModel {

    /**
     * Contains data for CurrentWeather to be displayed on the OnThisDayFragment
     *
     */
    private String currentWeatherURL = "http://api.openweathermap.org/data/2.5/weather";//basic url for requesting weather data with no values added
    private String currentWeatherAPI = "b22765d57f287a740aa4bfcccca160f8";//api key for requesting weather data from openweathermap.org
    private String weatherImgURL = "http://openweathermap.org/img/wn/"; //basic url for requesting weather image with no values added
    private Double lat; //holds Latitude data
    private Double lon; //holds Longitude data



    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getCurrentWeatherURL() {
        return currentWeatherURL;
    }

    public String getCurrentWeatherAPI() {
        return currentWeatherAPI;
    }

    public String getWeatherImgURL() {
        return weatherImgURL;
    }
}
