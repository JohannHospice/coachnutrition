package fr.univ_paris_diderot.coachnutrition.app.tools;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fr.univ_paris_diderot.coachnutrition.R;
import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;
import fr.univ_paris_diderot.coachnutrition.app.model.MealFood;


public class MealFoodAdapter extends RecyclerView.Adapter<MealFoodAdapter.MealFoodViewHolder> {
    private final Callable callable;
    private List<MealFood> list;

    public MealFoodAdapter(List<MealFood> list, Callable callable) {
        this.list = list;
        this.callable = callable;
    }

    @Override
    public MealFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MealFoodViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.food, parent, false));
    }

    @Override
    public void onBindViewHolder(final MealFoodViewHolder holder, int position) {
        final MealFood mealFood = list.get(position);
        holder.nameView.setText(mealFood.getName());
        holder.calorieView.setText(String.valueOf(mealFood.getCalorie()) + " cal");
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callable.call(MealFoodAdapter.this, mealFood);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setCursor(Cursor cursor) {
        list.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(Contract.Food._ID));
                String name = cursor.getString(cursor.getColumnIndex(Contract.Food.COLUMN_NAME_NAME));
                float calorie = cursor.getFloat(cursor.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE));
                list.add(new MealFood(id, calorie, name));
            }
            while (cursor.moveToNext());
        }
    }

    public void setList(List<MealFood> mealFoods) {
        list.clear();
        list.addAll(mealFoods);
        notifyDataSetChanged();
    }

    public void remove(MealFood item) {
        list.remove(item);
        notifyDataSetChanged();
    }

    public static class MealFoodViewHolder extends RecyclerView.ViewHolder {
        TextView calorieView;
        TextView nameView;
        View root;

        MealFoodViewHolder(View v) {
            super(v);
            this.root = v;
            nameView = v.findViewById(R.id.name);
            calorieView = v.findViewById(R.id.calorie);
        }
    }

    public interface Callable {
        void call(MealFoodAdapter adapter, MealFood item);
    }

}
