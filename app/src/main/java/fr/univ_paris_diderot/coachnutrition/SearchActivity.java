package fr.univ_paris_diderot.coachnutrition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {

    public static final String EXTRA_MEAL = "meal_id";
    private long idMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        idMeal = getIntent().getLongExtra(SearchActivity.EXTRA_MEAL, -1);

        if (idMeal < 0) {
            System.out.println("intent error");
            return;
        }
    }


}
