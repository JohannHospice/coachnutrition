package fr.univ_paris_diderot.coachnutrition;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;

public class MainActivity extends AppCompatActivity {
    private NutritionResolverHandler resolverHandler;

    private TextView minCalorie;
    private TextView maxCalorie;
    private TextView currentCalorie;

    private Date today;
    private long idObjective;
    private long idDay;
    private long idStatistic;
    private int oldMinCalorie = 1000;
    private int oldMaxCalorie =5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        today = new Date();

        minCalorie = findViewById(R.id.min_calorie);
        maxCalorie = findViewById(R.id.max_calorie);
        currentCalorie = findViewById(R.id.current_calorie);

        resolverHandler = new NutritionResolverHandler(getApplicationContext());

        Cursor cursor = resolverHandler.getDay(today, null);
        if (cursor != null && cursor.getCount() > 0) {
            //jour existe
            idDay = cursor.getLong(cursor.getColumnIndex(Contract.Day._ID));
            idObjective = cursor.getLong(cursor.getColumnIndex(Contract.Day.COLUMN_NAME_OBJECTIVE_ID));
            idStatistic = cursor.getLong(cursor.getColumnIndex(Contract.Day.COLUMN_NAME_STATISTIC_ID));
        } else {
            //jour existe pas
            Cursor cursorObjective = resolverHandler.getObjective(0, null);
            if (cursorObjective != null && cursorObjective.getCount() > 0) {
                cursorObjective.moveToFirst();
                oldMinCalorie = cursorObjective.getInt(cursorObjective.getColumnIndex(Contract.Objective.COLUMN_NAME_MIN_CALORIE));
                oldMaxCalorie = cursorObjective.getInt(cursorObjective.getColumnIndex(Contract.Objective.COLUMN_NAME_MAX_CALORIE));
            }
            idObjective = resolverHandler.insertObjective(oldMinCalorie, oldMaxCalorie);
            idStatistic = resolverHandler.insertStatistic(0, 0, 0, 0);
            idDay = resolverHandler.insertDay(today, idObjective, idStatistic);
        }
    }
}
