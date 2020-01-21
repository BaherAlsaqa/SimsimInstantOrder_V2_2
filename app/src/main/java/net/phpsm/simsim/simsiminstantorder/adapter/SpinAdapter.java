package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.models.responses.CarBrands;

import java.util.ArrayList;

/**
 * Created by baher on 07/08/2017.
 */

public class SpinAdapter extends ArrayAdapter<CarBrands> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<CarBrands> values;

    public SpinAdapter(Context context, int textViewResourceId,
                       ArrayList<CarBrands> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public CarBrands getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setHeight(40);
        label.setGravity(Gravity.CENTER | Gravity.START);
        label.setPadding(10,0,10,0);
        label.setTextSize(16);
        label.setTextColor(Color.parseColor("#C4C4C4"));
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setHeight(70);
        label.setGravity(Gravity.CENTER | Gravity.RIGHT);
        label.setPadding(0,0,10,0);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getName());

        return label;
    }
}

