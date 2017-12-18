package fr.univ_paris_diderot.coachnutrition;import android.content.Intent;import android.database.Cursor;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.text.Editable;import android.text.TextWatcher;import android.view.View;import android.widget.ArrayAdapter;import android.widget.EditText;import android.widget.Spinner;import android.widget.TextView;import android.widget.Toast;import java.util.ArrayList;import java.util.List;import fr.univ_paris_diderot.coachnutrition.app.database.Contract;import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;public class InsertFoodMealActivity extends AppCompatActivity {    public static final String EXTRA_ID_FOOD = "id_food";    private static final String EXTRA_ID_MEAL = "id_meal";    //Food attribute    private TextView nameView;    private TextView calorieView;    private TextView proteinView;    private TextView glucideView;    private TextView lipideView;    private EditText grammeEdit;    private Spinner mealSpinner;    private long idFood;    private long idMeal;    private int calorie;    private int glucide;    private int lipide;    private int protein;    private NutritionResolverHandler resolverHandler;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_insert_food_meal);        Intent intent = getIntent();        idFood = intent.getLongExtra(EXTRA_ID_FOOD, -1);        idMeal = intent.getLongExtra(EXTRA_ID_MEAL, -1);        nameView = findViewById(R.id.name);        grammeEdit = findViewById(R.id.gramme);        calorieView = findViewById(R.id.calorie);        proteinView = findViewById(R.id.protein);        glucideView = findViewById(R.id.glucide);        lipideView = findViewById(R.id.lipide);        mealSpinner = findViewById(R.id.meal_spinner);        resolverHandler = new NutritionResolverHandler(getApplicationContext());        grammeEdit.addTextChangedListener(new TextWatcher() {            @Override            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }            @Override            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {                try {                    int val = Integer.parseInt(charSequence.toString());                    updateStatistic(calorie * val, protein * val, glucide * val, lipide * val);                } catch (Exception e) {                    Toast.makeText(InsertFoodMealActivity.this, "error gramme", Toast.LENGTH_LONG);                }            }            @Override            public void afterTextChanged(Editable editable) {            }        });        //spinner        List<CharSequence> mealNames = new ArrayList<>();        mealNames.add("nouveau repas");        Cursor cursorMeal = resolverHandler.getMeals(new String[]{Contract.Meal.COLUMN_NAME_NAME});        if (cursorMeal != null && cursorMeal.getCount() > 0) {            cursorMeal.moveToFirst();            for (int i = 0; i < cursorMeal.getCount(); i++) {                mealNames.add(cursorMeal.getString(cursorMeal.getColumnIndex(Contract.Meal.COLUMN_NAME_NAME)));                cursorMeal.moveToNext();            }            cursorMeal.close();        } else            Toast.makeText(this, "error meal", Toast.LENGTH_LONG);        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,                android.R.layout.simple_spinner_item, mealNames);        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        mealSpinner.setAdapter(adapter);        //food info        Cursor cursorFood = resolverHandler.getFood(idFood, null);        if (cursorFood != null && cursorFood.getCount() > 0) {            cursorFood.moveToFirst();            String name = cursorFood.getString(cursorFood.getColumnIndex(Contract.Food.COLUMN_NAME_NAME));            long idStatistic = cursorFood.getLong(cursorFood.getColumnIndex(Contract.Food.COLUMN_NAME_STATISTIC_ID));            cursorFood.close();            //stat of food info            Cursor cursorStatistic = resolverHandler.getStatistic(idStatistic, null);            if (cursorStatistic != null && cursorStatistic.getCount() > 0) {                cursorStatistic.moveToFirst();                calorie = cursorStatistic.getInt(cursorStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE));                glucide = cursorStatistic.getInt(cursorStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_GLUCIDE));                lipide = cursorStatistic.getInt(cursorStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_LIPIDE));                protein = cursorStatistic.getInt(cursorStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_PROTEIN));                cursorStatistic.close();                nameView.setText(name);                updateStatistic(calorie, protein, glucide, lipide);            } else                Toast.makeText(this, "error: statistic", Toast.LENGTH_LONG);        } else            Toast.makeText(this, "error: food", Toast.LENGTH_LONG);    }    public void updateStatistic(int calorie, int protein, int glucide, int lipide) {        calorieView.setText(String.valueOf(calorie));        proteinView.setText(String.valueOf(protein));        glucideView.setText(String.valueOf(glucide));        lipideView.setText(String.valueOf(lipide));    }    public void onClickAdd(View v) {        resolverHandler.insertFoodMeal(Integer.parseInt(grammeEdit.getText().toString()), idFood, idMeal);        finish();    }}