package fr.univ_paris_diderot.coachnutrition;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;

public class FoodActivity extends AppCompatActivity {
    //Food attribute
    private TextView name;

    //Stat attribute
    private TextView gramme;
    private TextView calorie;
    private TextView protein;
    private TextView glucide;
    private TextView lipide;

    public static final String EXTRA_ID_FOOD = "jhf";
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


        Intent intent = getIntent();
        id = intent.getLongExtra(EXTRA_ID_FOOD, -1);

        NutritionResolverHandler resolverHandler = new NutritionResolverHandler(getApplicationContext());

        Cursor cursor =resolverHandler.getFood(id);
        System.out.println(cursor.getCount());
        if(cursor.moveToFirst()){
            for(String g: cursor.getColumnNames()){
                System.out.println(g + ": " +cursor.getColumnIndex(g));
            }
        }
    }
}
