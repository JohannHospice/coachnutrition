package fr.univ_paris_diderot.coachnutrition.app.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CalorieFoodAdapter extends ArrayAdapter<CalorieFood> {

    public CalorieFoodAdapter(@NonNull Context context, int resource, @NonNull List<CalorieFood> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
/*
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_calorie_food, parent, false);
        }

        CalorieFoodViewHolder viewHolder = (CalorieFoodViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new CalorieFoodViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.calorie = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }
        CalorieFood calorieFood = getItem(position);

        viewHolder.name.setText(String.valueOf(calorieFood.getCalorie()));
        viewHolder.calorie.setText(calorieFood.getName());

*/
        return convertView;
    }

    private class CalorieFoodViewHolder {
        public TextView name;
        public TextView calorie;
    }
}
