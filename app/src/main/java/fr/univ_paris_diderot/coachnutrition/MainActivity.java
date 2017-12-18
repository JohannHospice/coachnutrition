package fr.univ_paris_diderot.coachnutrition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;
import fr.univ_paris_diderot.coachnutrition.app.model.MealFood;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final int DEFAULT_MIN_CALORIE = 2000;
    private static final int DEFAULT_MAX_CALORIE = 3000;
    private static final String[] DEFAULT_MEAL_NAMES = {"Petit déjeuner", "Déjeuner", "Diner", "En-cas"};

    private NutritionResolverHandler resolverHandler;

    private TextView minCalorieView;
    private TextView maxCalorieView;
    private TextView currentCalorieView;
    private TextView dateView;
    private LinearLayout mealLayout;

    private RecyclerView mealsView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String date;
    private long idObjective;
    private long idDay;
    private long idStatistic;
    private int[] calories = new int[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minCalorieView = findViewById(R.id.min_calorie);
        maxCalorieView = findViewById(R.id.max_calorie);
        currentCalorieView = findViewById(R.id.current_calorie);
        dateView = findViewById(R.id.date);
        mealLayout = findViewById(R.id.list_meal);

        resolverHandler = new NutritionResolverHandler(getApplicationContext());
        date = DATE_FORMAT.format(new Date());

        Cursor cursor = resolverHandler.getDay(date, null);
        if (cursor != null && cursor.moveToFirst()) {
            idDay = cursor.getLong(cursor.getColumnIndex(Contract.Day._ID));
            idObjective = cursor.getLong(cursor.getColumnIndex(Contract.Day.COLUMN_NAME_OBJECTIVE_ID));
            idStatistic = cursor.getLong(cursor.getColumnIndex(Contract.Day.COLUMN_NAME_STATISTIC_ID));
            cursor.close();
        } else {
            idObjective = resolverHandler.insertObjective(DEFAULT_MIN_CALORIE, DEFAULT_MAX_CALORIE);
            idStatistic = resolverHandler.insertStatistic(0, 0, 0, 0);
            idDay = resolverHandler.insertDay(date, idObjective, idStatistic);
            for (String mealName : DEFAULT_MEAL_NAMES) {
                long idMealStatistic = resolverHandler.insertStatistic(0, 0, 0, 0);
                resolverHandler.insertMeal(mealName, idDay, idMealStatistic);
            }
        }

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Cursor cursorMeal = resolverHandler.query(
                Contract.Meal.TABLE_NAME,
                new String[]{Contract.Meal._ID, Contract.Meal.COLUMN_NAME_NAME, Contract.Meal.COLUMN_NAME_STATISTIC_ID},
                Contract.Meal.COLUMN_NAME_DAY_ID + " = ?",
                new String[]{String.valueOf(idDay)});
        if (cursorMeal != null && cursorMeal.moveToFirst()) {
            do {
                String mealName = cursorMeal.getString(cursorMeal.getColumnIndex(Contract.Meal.COLUMN_NAME_NAME));
                int mealCalorie = -1;

                final long mealId = cursorMeal.getLong(cursorMeal.getColumnIndex(Contract.Meal._ID));
                long mealStatisticId = cursorMeal.getLong(cursorMeal.getColumnIndex(Contract.Meal.COLUMN_NAME_STATISTIC_ID));

                Cursor cursorStatistic = resolverHandler.getStatistic(mealStatisticId, new String[]{Contract.Statistic.COLUMN_NAME_CALORIE});
                if (cursorStatistic != null && cursorStatistic.moveToFirst()) {
                    mealCalorie = cursorStatistic.getInt(cursorStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE));
                    cursorStatistic.close();
                }

                View mealView = vi.inflate(R.layout.meal, null);
                ((TextView) mealView.findViewById(R.id.name)).setText(mealName);
                ((TextView) mealView.findViewById(R.id.calorie)).setText(String.valueOf(mealCalorie));

                List<MealFood> mealFoodList = new ArrayList<>();

                Cursor cursorFoodMeal = resolverHandler.query(
                        Contract.FoodMeal.TABLE_NAME,
                        new String[]{Contract.FoodMeal._ID, Contract.FoodMeal.COLUMN_NAME_FOOD_ID, Contract.FoodMeal.COLUMN_NAME_GRAMME},
                        Contract.FoodMeal.COLUMN_NAME_MEAL_ID + " = ?",
                        new String[]{String.valueOf(mealId)});
                if (cursorFoodMeal != null && cursorFoodMeal.moveToFirst()) {
                    do {
                        long mealFoodId = cursorFoodMeal.getLong(cursorFoodMeal.getColumnIndex(Contract.FoodMeal._ID));
                        int mealFoodGramme = cursorFoodMeal.getInt(cursorFoodMeal.getColumnIndex(Contract.FoodMeal.COLUMN_NAME_GRAMME));

                        Cursor cursorFood = resolverHandler.query(
                                Contract.Food.TABLE_NAME,
                                new String[]{Contract.Food.COLUMN_NAME_NAME, Contract.Food.COLUMN_NAME_STATISTIC_ID},
                                Contract.Food._ID + " = ?",
                                new String[]{String.valueOf(mealFoodId)});
                        if (cursorFood != null && cursorFood.moveToFirst()) {
                            int mealFoodStatisticId = cursorFood.getInt(cursorFood.getColumnIndex(Contract.Food.COLUMN_NAME_STATISTIC_ID));
                            String mealFoodName = cursorFoodMeal.getString(cursorFood.getColumnIndex(Contract.Food.COLUMN_NAME_NAME));
                            cursorFood.close();
                            MealFood mealFood = new MealFood();
                            mealFood.setName(mealFoodName);
                            mealFood.setId(mealFoodId);

                            Cursor cursorFoodStatistic = resolverHandler.query(
                                    Contract.Statistic.TABLE_NAME,
                                    new String[]{Contract.Statistic.COLUMN_NAME_CALORIE},
                                    Contract.Statistic._ID + " = ?",
                                    new String[]{String.valueOf(mealFoodStatisticId)});
                            if (cursorFoodStatistic != null && cursorFoodStatistic.moveToFirst()) {
                                int mealFoodStatisticCalorie = cursorFoodStatistic.getInt(cursorFoodStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE));
                                mealFood.setCalorie(mealFoodStatisticCalorie * mealFoodGramme);
                                cursorFoodStatistic.close();
                            }
                            mealFoodList.add(mealFood);
                        }
                    } while (cursorFoodMeal.moveToNext());
                    cursorFoodMeal.close();
                }

                final MealFoodAdapter mealFoodAdapter = new MealFoodAdapter(mealFoodList);
                RecyclerView calorieFoodView = mealView.findViewById(R.id.list_food);
                mealView.findViewById(R.id.add_food).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra(SearchActivity.EXTRA_MEAL, mealId);
                    }
                });
                calorieFoodView.setAdapter(mealFoodAdapter);
                /*
                calorieFoodView.setOnClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MealFood item = calorieFoodAdapter.getItem(position);
                        Toast.makeText(MainActivity.this, item.getName(), Toast.LENGTH_SHORT).show();
                        resolverHandler.delete(Contract.FoodMeal.TABLE_NAME, Contract.FoodMeal._ID + " = ", new String[]{String.valueOf(item.getId())});
                        calorieFoodAdapter.remove(item);
                        calorieFoodAdapter.notifyDataSetChanged();
                    }
                });*/
                mealLayout.addView(mealView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            } while (cursorMeal.moveToNext());
            cursorMeal.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dateView.setText(date);

        try {
            calories = getObjective(idObjective);
            minCalorieView.setText(String.valueOf(calories[0]));
            maxCalorieView.setText(String.valueOf(calories[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int currentCalorie = getCalorieOfStatistic(idStatistic);
            currentCalorieView.setText(String.valueOf(currentCalorie));

            if (currentCalorie < calories[0])
                currentCalorieView.setTextColor(Color.BLUE);
            else if (currentCalorie > calories[1])
                currentCalorieView.setTextColor(Color.RED);
            else
                currentCalorieView.setTextColor(Color.GREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getObjective(long idObjective) throws Exception {
        Cursor cursor = resolverHandler.getObjective(idObjective, null);
        if (cursor == null || cursor.getCount() <= 0)
            throw new Exception();
        cursor.moveToFirst();
        int minCalorie = cursor.getInt(cursor.getColumnIndex(Contract.Objective.COLUMN_NAME_MIN_CALORIE));
        int maxCalorie = cursor.getInt(cursor.getColumnIndex(Contract.Objective.COLUMN_NAME_MAX_CALORIE));
        cursor.close();
        return new int[]{minCalorie, maxCalorie};
    }

    public int getCalorieOfStatistic(long idStatistic) throws Exception {
        Cursor cursor = resolverHandler.getStatistic(idStatistic, new String[]{Contract.Statistic.COLUMN_NAME_CALORIE});
        if (cursor == null || cursor.getCount() <= 0)
            throw new Exception();
        cursor.moveToFirst();
        int calorie = cursor.getInt(cursor.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE));
        cursor.close();
        return calorie;
    }

    public void onClickUpdateObjective(View v) {
        Intent intent = new Intent(this, UpdateObjectiveActivity.class);
        intent.putExtra(UpdateObjectiveActivity.EXTRA_OBJECTIVE_ID, idObjective);
        startActivity(intent);
    }
}
