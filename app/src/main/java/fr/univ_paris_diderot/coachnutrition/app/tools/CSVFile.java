package fr.univ_paris_diderot.coachnutrition.app.tools;


import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;

public class CSVFile {
    private InputStream inputStream;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void insertAllFoods(NutritionResolverHandler resolverHandler) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                if (validRow(row)) {
                    String name = row[0];
                    float calorie = Float.parseFloat(row[1]);
                    float protein = Float.parseFloat(row[2]);
                    float glucide = Float.parseFloat(row[3]);
                    float lipide = Float.parseFloat(row[4]);

                    long statId = resolverHandler.insertStatistic(calorie, protein, glucide, lipide);
                    resolverHandler.insertFood(name, statId);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
    }

    private boolean validRow(String[] row) {
        return row.length == 5;
    }
}
