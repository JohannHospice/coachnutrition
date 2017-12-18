package fr.univ_paris_diderot.coachnutrition;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;

public class UpdateObjectiveActivity extends AppCompatActivity {

    public static final String EXTRA_OBJECTIVE_ID = "id_objective";
    private long objectiveId;
    private EditText minCalorieEdit;
    private EditText maxCalorieEdit;
    private NutritionResolverHandler resolverHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_objective);

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

    public void onClickUpdate(View v) {
        try {
            int minCalorie = Integer.parseInt(minCalorieEdit.getText().toString());
            int maxCalorie = Integer.parseInt(maxCalorieEdit.getText().toString());
            if (0 < minCalorie && minCalorie < maxCalorie) {
                resolverHandler.updateObjective(objectiveId, minCalorie, maxCalorie);
                finish();
            } else throw new Exception("invalid values");
        } catch (Exception e) {
            Log.e("Update objective", e.getMessage());
        }
    }
}
