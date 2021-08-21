package com.example.weatherhistory.ui.stations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherhistory.R;

import java.util.ArrayList;

/**
 * StationListAdapter populates TextView and ImageView elements of the Layout xml with information
 * from the StationDataModel
 */
public class StationListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StationDataModel> dataModelArrayList;

    public StationListAdapter(Context context, ArrayList<StationDataModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    /**
     * Creates TextView and ImageView variables
     */
    private class ViewHolder {

        TextView weatherYear, weatherTempMax, weatherTempMin,weatherAirFrost,weatherRainfall,weatherSunHours;
        ImageView weatherImg;
    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * getView adds each index of DataModelArrayList to the ViewHolder and assigns them to
     * the Layout xml IDs for display. this is then added to converView
     * @param position Position of datamodelarraylist that is being added to the listview
     * @param convertView View that will be returned when task is complete
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.station_listview, null, true);

            holder.weatherImg = convertView.findViewById(R.id.month_img);//assigns the TextView and ImageView variables to Layout xml ID's
            holder.weatherYear = convertView.findViewById(R.id.weather_year);
            holder.weatherTempMax = convertView.findViewById(R.id.weather_tempMax);
            holder.weatherTempMin = convertView.findViewById(R.id.weather_tempMin);
            holder.weatherAirFrost = convertView.findViewById(R.id.weather_airFrost);
            holder.weatherRainfall = convertView.findViewById(R.id.weather_rainFall);
            holder.weatherSunHours = convertView.findViewById(R.id.weather_sunHours);

            convertView.setTag(holder);
        }else {

            holder = (ViewHolder)convertView.getTag();
        }
        if(dataModelArrayList.get(position).getMonth() == 1){//sets the month to an image from the relevant res drawable ID
            holder.weatherImg.setImageResource(R.drawable.ic_jan);
        }else if(dataModelArrayList.get(position).getMonth() == 2){
            holder.weatherImg.setImageResource(R.drawable.ic_feb);
        }else if(dataModelArrayList.get(position).getMonth() == 3){
            holder.weatherImg.setImageResource(R.drawable.ic_mar);
        }else if(dataModelArrayList.get(position).getMonth() == 4){
            holder.weatherImg.setImageResource(R.drawable.ic_apr);
        }else if(dataModelArrayList.get(position).getMonth() == 5){
            holder.weatherImg.setImageResource(R.drawable.ic_may);
        }else if(dataModelArrayList.get(position).getMonth() == 6){
            holder.weatherImg.setImageResource(R.drawable.ic_jun);
        }else if(dataModelArrayList.get(position).getMonth() == 7){
            holder.weatherImg.setImageResource(R.drawable.ic_jul);
        }else if(dataModelArrayList.get(position).getMonth() == 8){
            holder.weatherImg.setImageResource(R.drawable.ic_aug);
        }else if(dataModelArrayList.get(position).getMonth() == 9){
            holder.weatherImg.setImageResource(R.drawable.ic_sep);
        }else if(dataModelArrayList.get(position).getMonth() == 10){
            holder.weatherImg.setImageResource(R.drawable.ic_oct);
        }else if(dataModelArrayList.get(position).getMonth() == 11){
            holder.weatherImg.setImageResource(R.drawable.ic_nov);
        }else {
            holder.weatherImg.setImageResource(R.drawable.ic_dec);
        }
        holder.weatherYear.setText(dataModelArrayList.get(position).getYear());
        holder.weatherTempMax.setText(new StringBuilder().append("Max Temp: ").append(dataModelArrayList.get(position).getTempMax()).append("\u2103").toString());//sets the TextView of each variable and formats with StringBuilder
        holder.weatherTempMin.setText(new StringBuilder().append("Min Temp: ").append(dataModelArrayList.get(position).getTempMin()).append("\u2103").toString());
        holder.weatherAirFrost.setText(new StringBuilder().append("Days Of Airfrost: ").append(dataModelArrayList.get(position).getAirFrost()).toString());
        holder.weatherRainfall.setText(new StringBuilder().append("Rainfall mm: ").append(dataModelArrayList.get(position).getRainFall()).toString());
        holder.weatherSunHours.setText(new StringBuilder().append("Sun Hours: ").append(dataModelArrayList.get(position).getSunHours()).toString());


        return convertView;
    }



}
