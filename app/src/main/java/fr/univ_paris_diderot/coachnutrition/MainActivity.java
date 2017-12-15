package fr.univ_paris_diderot.coachnutrition;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Currency;
import java.util.Date;

import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;

public class MainActivity extends AppCompatActivity {
    private NutritionResolverHandler resolverHandler;
    private TextView minCalorie;
    private TextView maxCalorie;
    private TextView currentCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolverHandler = new NutritionResolverHandler(getApplicationContext());
        /*
        Date today = new Date();
        Cursor cursor = resolverHandler.getDay(today);

        if (cursor.getCount() != 0) {

        }
        minCalorie = findViewById(R.id.min_calorie);
        maxCalorie = findViewById(R.id.max_calorie);
        currentCalorie = findViewById(R.id.current_calorie);
         */
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra(FoodActivity.EXTRA_ID_FOOD, 0);
        startActivity(intent);
    }

}
