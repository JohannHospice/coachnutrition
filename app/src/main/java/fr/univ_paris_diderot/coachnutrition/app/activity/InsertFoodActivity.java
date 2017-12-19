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
    private EditText nameView;

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
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        nameView = findViewById(R.id.name);
        gramme = findViewById(R.id.gramme);
        calorie = findViewById(R.id.calorie);
        protein = findViewById(R.id.protein);
        glucide = findViewById(R.id.glucide);
        lipide = findViewById(R.id.lipide);

        resolverHandler = new NutritionResolverHandler(getApplicationContext());
    }

    public void onClickAdd(View view) {
        try {
            String name = this.nameView.getText().toString().trim();
            if (name.length() <= 0)
                throw new Exception();
            long idStatistic = resolverHandler.insertStatistic(
                    Float.parseFloat(calorie.getText().toString()),
                    Float.parseFloat(glucide.getText().toString()),
                    Float.parseFloat(lipide.getText().toString()),
                    Float.parseFloat(protein.getText().toString()));
            resolverHandler.insertFood(
                    name,
                    idStatistic);
            Toast.makeText(this, R.string.food_created, Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Veillez verifier vos champs", Toast.LENGTH_SHORT).show();
        }
    }
}
