package curtin.edu.citysim.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import curtin.edu.citysim.Core.Model.Game.GameData;
import curtin.edu.citysim.Core.Model.Structures.StructureData;
import curtin.edu.citysim.MainActivity;
import curtin.edu.citysim.R;
import curtin.edu.citysim.UI.Fragment.MapFragment;
import curtin.edu.citysim.UI.Fragment.SelectorFragment;
import curtin.edu.citysim.UI.Fragment.StatusFragment;

public class MapActivity extends AppCompatActivity
{
    private GameData game;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Fetch data from activity
        Intent intent = getIntent();

        game = (GameData)intent.getSerializableExtra(MainActivity.GAME);
        StructureData structures = (StructureData)intent.getSerializableExtra(MainActivity.STRUCT);

        // Setup fragments
        FragmentManager fm = getSupportFragmentManager();

        MapFragment mapFrag = (MapFragment)fm.findFragmentById(R.id.map);
        SelectorFragment resFrag = (SelectorFragment)fm.findFragmentById(R.id.selector_residential);
        SelectorFragment comFrag = (SelectorFragment)fm.findFragmentById(R.id.selector_commercial);
        SelectorFragment roadFrag = (SelectorFragment)fm.findFragmentById(R.id.selector_road);
        StatusFragment statusFrag = (StatusFragment)fm.findFragmentById(R.id.status);

        // Check if fragments are not already created
        mapFrag = mapFrag != null ? mapFrag : new MapFragment(game);
        resFrag = resFrag != null ? resFrag : new SelectorFragment(structures.getAllResidentials(), "Residential", game);
        comFrag = comFrag != null ? comFrag : new SelectorFragment(structures.getAllCommercials(), "Commercial", game);
        roadFrag = roadFrag != null ? roadFrag : new SelectorFragment(structures.getAllRoads(), "Road", game);
        statusFrag = statusFrag != null ? statusFrag : new StatusFragment(game);

        fm.beginTransaction().add(R.id.map, mapFrag).commit();
        fm.beginTransaction().add(R.id.selector_residential, resFrag).commit();
        fm.beginTransaction().add(R.id.selector_commercial, comFrag).commit();
        fm.beginTransaction().add(R.id.selector_road, roadFrag).commit();
        fm.beginTransaction().add(R.id.status, statusFrag).commit();
    }

    @Override
    public void onBackPressed()
    {
        // Return data back to calling activity
        Intent intent = new Intent();
        intent.putExtra(MainActivity.GAME, game);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
