package fr.univ_paris_diderot.coachnutrition.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.univ_paris_diderot.coachnutrition.R;
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        name = findViewById(R.id.name);
        gramme = findViewById(R.id.gramme);
        calorie = findViewById(R.id.calorie);
        protein = findViewById(R.id.protein);
        glucide = findViewById(R.id.glucide);
        lipide = findViewById(R.id.lipide);

        resolverHandler = new NutritionResolverHandler(getApplicationContext());
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
        Toast.makeText(this, R.string.food_created, Toast.LENGTH_SHORT).show();
        finish();
    }
}
