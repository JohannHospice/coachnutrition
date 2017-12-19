package fr.univ_paris_diderot.coachnutrition.app.activity;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.univ_paris_diderot.coachnutrition.R;
import fr.univ_paris_diderot.coachnutrition.app.database.Contract;
import fr.univ_paris_diderot.coachnutrition.app.database.NutritionResolverHandler;
import fr.univ_paris_diderot.coachnutrition.app.model.MealFood;
import fr.univ_paris_diderot.coachnutrition.app.tools.MealFoodAdapter;

public class SearchFoodActivity extends AppCompatActivity {
    /*
        private String authority;
        authority = getResources().getString(R.string.authority);
      */
    public static final String EXTRA_MEAL = "meal_id";

    private long idMeal;
    private String authority;
    private MealFoodAdapter adapter;
    SearchView searchView = null;
    ListView listView;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_food, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.search_item);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() < 3)
                    adapter.setList(allMealFood());
                else
                    search(searchView.getQuery().toString());
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search_item:
                return onSearchRequested();
            case R.id.insert_food:
                startActivity(new Intent(this, InsertFoodActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        authority = getResources().getString(R.string.authority);

        idMeal = getIntent().getLongExtra(SearchFoodActivity.EXTRA_MEAL, -1);

        adapter = new MealFoodAdapter(allMealFood(), new MealFoodAdapter.Callable() {
            @Override
            public void call(MealFoodAdapter adapter, MealFood item) {
                Intent iii = new Intent(SearchFoodActivity.this, InsertFoodMealActivity.class);
                iii.putExtra(InsertFoodMealActivity.EXTRA_ID_FOOD, item.getId());
                iii.putExtra(InsertFoodMealActivity.EXTRA_ID_MEAL, idMeal);
                startActivity(iii);
            }
        });

        RecyclerView mealFoodRecycler = findViewById(R.id.list_food);
        mealFoodRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mealFoodRecycler.setAdapter(adapter);
    }

    private List<MealFood> allMealFood() {
        List<MealFood> mealFoodList = new ArrayList<>();
        NutritionResolverHandler resolverHandler = new NutritionResolverHandler(this);
        Cursor cursorFood = resolverHandler.query(Contract.Food.TABLE_NAME, new String[]{Contract.Food._ID, Contract.Food.COLUMN_NAME_NAME, Contract.Food.COLUMN_NAME_STATISTIC_ID}, null, null);
        if (cursorFood != null && cursorFood.moveToFirst()) {
            do {
                long id = cursorFood.getLong(cursorFood.getColumnIndex(Contract.Food._ID));
                long idStat = cursorFood.getLong(cursorFood.getColumnIndex(Contract.Food.COLUMN_NAME_STATISTIC_ID));
                String name = cursorFood.getString(cursorFood.getColumnIndex(Contract.Food.COLUMN_NAME_NAME));
                Cursor cursorStatistic = resolverHandler.query(Contract.Statistic.TABLE_NAME, new String[]{Contract.Statistic.COLUMN_NAME_CALORIE}, Contract.Statistic._ID + "=?", new String[]{String.valueOf(idStat)});
                if (cursorStatistic != null && cursorStatistic.moveToFirst()) {
                    int calorie = cursorStatistic.getInt(cursorStatistic.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE));
                    mealFoodList.add(new MealFood(id, calorie, name));
                }
            }
            while (cursorFood.moveToNext());
        }
        return mealFoodList;
    }


    public void search(final String query) {
        final Uri uri = new Uri.Builder().scheme("content")
                .authority(authority)
                .appendPath(Contract.Food.TABLE_NAME + Contract.Statistic.TABLE_NAME)
                .build();
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                return new CursorLoader(SearchFoodActivity.this, uri, new String[]{Contract.Food._ID, Contract.Food.COLUMN_NAME_NAME, Contract.Statistic.COLUMN_NAME_CALORIE},
                        null, new String[]{query}, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                adapter.setCursor(cursor);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                adapter.setCursor(null);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
