package hl.univ_paris_diderot.coachnutrition.app.database;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import hl.univ_paris_diderot.coachnutrition.app.modele.Objective;
import hl.univ_paris_diderot.coachnutrition.app.modele.Statistic;

public class NutritionResolverHandler {
    private ContentResolver resolver;

    NutritionResolverHandler(Context context) {
        resolver = context.getContentResolver();
    }

    public void insert(Statistic statistic) {
        try {
            resolver.insert(buildUri(Contract.Statistic.TABLE_NAME), statistic.toContentValues());
            Log.d("ok", "insertion complete");
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    public void insert(Objective objective) {
        try {
            resolver.insert(buildUri(Contract.Objective.TABLE_NAME), objective.toContentValues());
            Log.d("ok", "insertion complete");
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    private Uri buildUri(String table) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(NutritionProvider.AUTHORITY).appendPath(table);
        return builder.build();
    }
}
