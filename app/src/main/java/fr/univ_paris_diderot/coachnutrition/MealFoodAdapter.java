package fr.univ_paris_diderot.coachnutrition;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.univ_paris_diderot.coachnutrition.app.model.MealFood;


public class MealFoodAdapter extends RecyclerView.Adapter<MealFoodAdapter.ViewHolder> {

    private List<MealFood> list;

    public MealFoodAdapter(List<MealFood> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.food, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MealFood mealFood = list.get(position);
        holder.nameView.setText(mealFood.getName());
        holder.calorieView.setText(String.valueOf(mealFood.getCalorie()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView calorieView;
        public TextView nameView;

        public ViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.name);
            calorieView = v.findViewById(R.id.calorie);
        }
    }

}
