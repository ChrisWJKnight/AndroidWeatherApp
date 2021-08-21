package com.example.weatherhistory.ui.stations;

/**
 * Station data model contains variables for StationFragment that will store data extracted from JSON
 */
public class StationDataModel {

    private int id;
    private int yearID;
    private String year;
    private int month;
    private String tempMax;
    private String tempMin;
    private String airFrost;
    private String rainFall;
    private String sunHours;
    final String stationAPI = "http://86.3.49.209/weatherapi/select_station.php"; //URL to call to historical database. Database hosted on home server


//getters and setters for data model
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getAirFrost() {
        return airFrost;
    }

    public void setAirFrost(String airFrost) {
        this.airFrost = airFrost;
    }

    public String getRainFall() {
        return rainFall;
    }

    public void setRainFall(String rainFall) {
        this.rainFall = rainFall;
    }

    public String getSunHours() {
        return sunHours;
    }

    public void setSunHours(String sunHours) {
        this.sunHours = sunHours;
    }

    public String getStationAPI() {
        return stationAPI;
    }

    public int getYearID() {
        return yearID;
    }

    public void setYearID(int yearID) {
        this.yearID = yearID;
    }
}
