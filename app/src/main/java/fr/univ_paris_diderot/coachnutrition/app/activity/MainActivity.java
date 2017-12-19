package fr.univ_paris_diderot.coachnutrition.app.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.univ_paris_diderot.coachnutrition.app.tools.MealFoodAdapter;
import fr.univ_paris_diderot.coachnutrition.R;
import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;
import fr.univ_paris_diderot.coachnutrition.app.model.MealFood;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DATE_FORMAT_PRETTY = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateFormat DATE_FORMAT_DB = new SimpleDateFormat("yyyyMMdd");
    private static final int DEFAULT_MIN_CALORIE = 2000;
    private static final int DEFAULT_MAX_CALORIE = 3000;
    private static final String[] DEFAULT_MEAL_NAMES = {"Petit déjeuner", "Déjeuner", "Diner", "En-cas"};

    private NutritionResolverHandler resolverHandler;

    private TextView minCalorieView;
    private TextView maxCalorieView;
    private TextView currentCalorieView;
    private TextView dateView;
    private LinearLayout mealLayout;

    private Calendar calendar = Calendar.getInstance();
    private int[] calories = new int[2];
    private long idObjective;
    private long idDay;
    private long idStatistic;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_objective:
                Intent intent = new Intent(this, UpdateObjectiveActivity.class);
                intent.putExtra(UpdateObjectiveActivity.EXTRA_OBJECTIVE_ID, idObjective);
                startActivity(intent);
                return true;
            case R.id.insert_food:
                startActivity(new Intent(this, InsertFoodActivity.class));
                return true;
            case R.id.insert_meal:
                startActivity(new Intent(this, InsertMealActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickNext(View v) {
        calendar.add(Calendar.DATE, 1);
        update();
    }


    public void onClickPrev(View v) {
        calendar.add(Calendar.DATE, -1);
        update();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        minCalorieView = findViewById(R.id.min_calorie);
        maxCalorieView = findViewById(R.id.max_calorie);
        currentCalorieView = findViewById(R.id.current_calorie);
        dateView = findViewById(R.id.date);
        mealLayout = findViewById(R.id.list_meal);

        resolverHandler = new NutritionResolverHandler(getApplicationContext());
        calendar.setTime(new Date());
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void update() {
        Date date = calendar.getTime();
        String dbDate = DATE_FORMAT_DB.format(date);
        Cursor cursorDay = resolverHandler.getDay(dbDate, null);
        if (cursorDay != null && cursorDay.moveToFirst()) {
            idDay = cursorDay.getLong(cursorDay.getColumnIndex(Contract.Day._ID));
            idObjective = cursorDay.getLong(cursorDay.getColumnIndex(Contract.Day.COLUMN_NAME_OBJECTIVE_ID));
            idStatistic = cursorDay.getLong(cursorDay.getColumnIndex(Contract.Day.COLUMN_NAME_STATISTIC_ID));
            cursorDay.close();
        } else {
            idObjective = resolverHandler.insertObjective(DEFAULT_MIN_CALORIE, DEFAULT_MAX_CALORIE);
            idStatistic = resolverHandler.insertStatistic(0, 0, 0, 0);
            idDay = resolverHandler.insertDay(dbDate, idObjective, idStatistic);
            for (String mealName : DEFAULT_MEAL_NAMES) {
                long idMealStatistic = resolverHandler.insertStatistic(0, 0, 0, 0);
                resolverHandler.insertMeal(mealName, idDay, idMealStatistic);
            }
        }

        dateView.setText(DATE_FORMAT_PRETTY.format(date));

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
        fillMeals();
    }

    private void fillMeals() {
        final LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mealLayout.removeAllViews();
        // ajouter les repas et aliment dans repas
        Cursor cursorMeal = resolverHandler.query(
                Contract.Meal.TABLE_NAME,
                new String[]{Contract.Meal._ID, Contract.Meal.COLUMN_NAME_NAME, Contract.Meal.COLUMN_NAME_STATISTIC_ID},
                Contract.Meal.COLUMN_NAME_DAY_ID + " = ?",
                new String[]{String.valueOf(idDay)});
        if (cursorMeal != null && cursorMeal.moveToFirst()) {
            do {
                View mealView = vi.inflate(R.layout.meal, null);
                mealLayout.addView(mealView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                RecyclerView mealFoodRecycler = mealView.findViewById(R.id.list_food);
                mealFoodRecycler.setLayoutManager(new LinearLayoutManager(mealView.getContext()));

                String mealName = cursorMeal.getString(cursorMeal.getColumnIndex(Contract.Meal.COLUMN_NAME_NAME));
                final long mealId = cursorMeal.getLong(cursorMeal.getColumnIndex(Contract.Meal._ID));
                long mealStatisticId = cursorMeal.getLong(cursorMeal.getColumnIndex(Contract.Meal.COLUMN_NAME_STATISTIC_ID));

                mealFoodRecycler.setAdapter(new MealFoodAdapter(createMealFoodList(mealId), new MealFoodAdapter.Callable() {
                    @Override
                    public void call(MealFoodAdapter adapter, MealFood item) {
                        /*
                        Intent iii = new Intent(MainActivity.this, UpdateFoodMealActivity.class);
                        iii.putExtra(InsertFoodMealActivity.EXTRA_ID_FOOD, item.getId());
                        startActivity(iii);
                        */
                        resolverHandler.deleteFoodMeal(item.getId());
                        adapter.remove(item);
                        onResume();
                        Toast.makeText(MainActivity.this, "aliment supprimé", Toast.LENGTH_SHORT).show();
                    }
                }));
                ((TextView) mealView.findViewById(R.id.name)).setText(mealName);
                mealView.findViewById(R.id.add_food).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, SearchFoodActivity.class);
                        intent.putExtra(SearchFoodActivity.EXTRA_MEAL, mealId);
                        startActivity(intent);
                    }
                });

                Cursor cursorStatistic = resolverHandler.getStatistic(mealStatisticId, new String[]{Contract.Statistic.COLUMN_NAME_CALORIE});
                if (cursorStatistic != null && cursorStatistic.moveToFirst()) {
                    int mealCalorie = cursorStatistic.getInt(cursorStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE));
                    ((TextView) mealView.findViewById(R.id.calorie)).setText(String.valueOf(mealCalorie) + " cal");
                    cursorStatistic.close();
                }
            } while (cursorMeal.moveToNext());
            cursorMeal.close();
        }
    }

    private List<MealFood> createMealFoodList(long mealId) {
        List<MealFood> mealFoodList = new ArrayList<>();

        Cursor cursorFoodMeal = resolverHandler.query(
                Contract.FoodMeal.TABLE_NAME,
                new String[]{Contract.FoodMeal._ID, Contract.FoodMeal.COLUMN_NAME_FOOD_ID, Contract.FoodMeal.COLUMN_NAME_GRAMME},
                Contract.FoodMeal.COLUMN_NAME_MEAL_ID + " = ?",
                new String[]{String.valueOf(mealId)});
        if (cursorFoodMeal != null && cursorFoodMeal.moveToFirst()) {
            do {
                long mealFoodId = cursorFoodMeal.getLong(cursorFoodMeal.getColumnIndex(Contract.FoodMeal._ID));
                long foodId = cursorFoodMeal.getLong(cursorFoodMeal.getColumnIndex(Contract.FoodMeal.COLUMN_NAME_FOOD_ID));
                int mealFoodGramme = cursorFoodMeal.getInt(cursorFoodMeal.getColumnIndex(Contract.FoodMeal.COLUMN_NAME_GRAMME));

                Cursor cursorFood = resolverHandler.query(
                        Contract.Food.TABLE_NAME,
                        new String[]{Contract.Food.COLUMN_NAME_NAME, Contract.Food.COLUMN_NAME_STATISTIC_ID},
                        Contract.Food._ID + " = ?",
                        new String[]{String.valueOf(foodId)});
                if (cursorFood != null && cursorFood.moveToFirst()) {
                    mealFoodList.add(createMealFood(cursorFood, mealFoodId, mealFoodGramme));
                    cursorFood.close();
                }
            } while (cursorFoodMeal.moveToNext());
            cursorFoodMeal.close();
        }
        return mealFoodList;
    }

    private MealFood createMealFood(Cursor cursor, long mealFoodId, int mealFoodGramme) {
        String mealFoodName = cursor.getString(cursor.getColumnIndex(Contract.Food.COLUMN_NAME_NAME));
        long mealFoodStatisticId = cursor.getInt(cursor.getColumnIndex(Contract.Food.COLUMN_NAME_STATISTIC_ID));

        MealFood mealFood = new MealFood();
        mealFood.setId(mealFoodId);
        mealFood.setName(mealFoodName);

        Cursor cursorFoodStatistic = resolverHandler.query(
                Contract.Statistic.TABLE_NAME,
                new String[]{Contract.Statistic.COLUMN_NAME_CALORIE},
                Contract.Statistic._ID + " = ?",
                new String[]{String.valueOf(mealFoodStatisticId)});
        if (cursorFoodStatistic != null && cursorFoodStatistic.moveToFirst()) {
            int mealFoodStatisticCalorie = cursorFoodStatistic.getInt(cursorFoodStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE));
            cursorFoodStatistic.close();
            mealFood.setCalorie(mealFoodStatisticCalorie * mealFoodGramme);
        }
        return mealFood;
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
}
