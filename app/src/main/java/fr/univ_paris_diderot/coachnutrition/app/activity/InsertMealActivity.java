package fr.univ_paris_diderot.coachnutrition.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.univ_paris_diderot.coachnutrition.R;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;

public class InsertMealActivity extends AppCompatActivity {
    private static final String EXTRA_DAY_ID = "dayid";

    private NutritionResolverHandler nutritionResolverHandler;
    private TextView nameView;
    private long dayId;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_meal);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dayId = getIntent().getLongExtra(EXTRA_DAY_ID, -1);
        nutritionResolverHandler = new NutritionResolverHandler(getApplicationContext());

        nameView = findViewById(R.id.name);
    }

    private boolean validateName(String value){
        return true;
    }
    public void onClickInsertMeal(View v) {
        String name = nameView.getText().toString();
        if(validateName(name)){
            long idStat = nutritionResolverHandler.insertStatistic(0, 0, 0, 0);
            nutritionResolverHandler.insertMeal(name, dayId, idStat);
            finish();
        }
        else Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }
}
