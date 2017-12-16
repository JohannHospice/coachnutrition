package fr.univ_paris_diderot.coachnutrition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;

public class InsertFoodActivity extends AppCompatActivity {

    //Food attribute
    private EditText name;

    //Stat attribute
    private TextView gramme;
    private EditText calorie;
    private EditText protein;
    private EditText glucide;
    private EditText lipide;

    private NutritionResolverHandler resolverHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food);
        resolverHandler = new NutritionResolverHandler(getApplicationContext());

        name = findViewById(R.id.name);
        gramme = findViewById(R.id.gramme);
        calorie = findViewById(R.id.calorie);
        protein = findViewById(R.id.protein);
        glucide = findViewById(R.id.glucide);
        lipide = findViewById(R.id.lipide);
    }

    public void onClickAdd(View view) {
        long idStatistic = resolverHandler.insertStatistic(
                Integer.parseInt(calorie.getText().toString()),
                Integer.parseInt(glucide.getText().toString()),
                Integer.parseInt(lipide.getText().toString()),
                Integer.parseInt(protein.getText().toString()));
        resolverHandler.insertFood(
                name.getText().toString(),
                idStatistic);
    }
}
