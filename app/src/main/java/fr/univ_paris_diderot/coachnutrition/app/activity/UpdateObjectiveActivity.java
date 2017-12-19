package fr.univ_paris_diderot.coachnutrition.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import fr.univ_paris_diderot.coachnutrition.R;
import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;

public class UpdateObjectiveActivity extends AppCompatActivity {

    public static final String EXTRA_OBJECTIVE_ID = "id_objective";
    private static final String FICHIER_PREFERENCES = "fr.univ-paris-diderot.coachnutrition.PREFERENCES";
    private static final String MIN_CALORIE = "min_calorie";
    private static final String MAX_CALORIE = "max_calorie";
    private long objectiveId;
    private EditText minCalorieEdit;
    private EditText maxCalorieEdit;
    private NutritionResolverHandler resolverHandler;
    private boolean isEditPreferences = false;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_objective);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (isEditPreferences) {
            SharedPreferences shared = getSharedPreferences(FICHIER_PREFERENCES,
                    Context.MODE_PRIVATE);
        } else {

            Intent intent = getIntent();
            objectiveId = intent.getLongExtra(EXTRA_OBJECTIVE_ID, -1);
            if (objectiveId < 0) {
                Log.e("UpdateObjectiveActivity", "bad intent value feeded");
                finish();
            }

            minCalorieEdit = findViewById(R.id.min_calorie);
            maxCalorieEdit = findViewById(R.id.max_calorie);

            resolverHandler = new NutritionResolverHandler(getApplicationContext());

            Cursor cursor = resolverHandler.getObjective(objectiveId, null);
            if (cursor != null && cursor.moveToFirst()) {
                minCalorieEdit.setText(String.valueOf(
                        cursor.getInt(cursor.getColumnIndex(Contract.Objective.COLUMN_NAME_MIN_CALORIE))));
                maxCalorieEdit.setText(String.valueOf(
                        cursor.getInt(cursor.getColumnIndex(Contract.Objective.COLUMN_NAME_MAX_CALORIE))));
                cursor.close();
            }
        }
    }

    public void onClickUpdate(View v) {
        try {
            int minCalorie = Integer.parseInt(minCalorieEdit.getText().toString());
            int maxCalorie = Integer.parseInt(maxCalorieEdit.getText().toString());
            if (isEditPreferences) {
                SharedPreferences pref = getSharedPreferences(FICHIER_PREFERENCES,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt(MIN_CALORIE, minCalorie);
                editor.putInt(MAX_CALORIE, maxCalorie);
            } else {
                if (0 < minCalorie && minCalorie < maxCalorie) {
                    resolverHandler.updateObjective(objectiveId, minCalorie, maxCalorie);
                    finish();
                } else throw new Exception("invalid values");
            }
        } catch (Exception e) {
            Log.e("Update objective", e.getMessage());
        }
    }
}
