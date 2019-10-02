package curtin.edu.citysim.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.Settings;
import curtin.edu.citysim.Core.Model.Structure;
import curtin.edu.citysim.Core.Model.StructureData;
import curtin.edu.citysim.MainActivity;
import curtin.edu.citysim.R;
import curtin.edu.citysim.UI.Fragment.CommercialFragment;
import curtin.edu.citysim.UI.Fragment.MapFragment;
import curtin.edu.citysim.UI.Fragment.ResidentialFragment;
import curtin.edu.citysim.UI.Fragment.RoadFragment;
import curtin.edu.citysim.UI.Fragment.SelectorFragment;
import curtin.edu.citysim.UI.Fragment.StatusFragment;

public class MapActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();

        GameData game = (GameData)intent.getSerializableExtra(MainActivity.GAME);
        StructureData structures = (StructureData)intent.getSerializableExtra(MainActivity.STRUCT);

        FragmentManager fm = getSupportFragmentManager();

        MapFragment mapFrag = (MapFragment)fm.findFragmentById(R.id.map);
        SelectorFragment resFrag = (SelectorFragment)fm.findFragmentById(R.id.selector_residential);
        SelectorFragment comFrag = (SelectorFragment)fm.findFragmentById(R.id.selector_commercial);
        SelectorFragment roadFrag = (SelectorFragment)fm.findFragmentById(R.id.selector_road);
        StatusFragment statusFrag = (StatusFragment)fm.findFragmentById(R.id.status);

        mapFrag = mapFrag != null ? mapFrag : new MapFragment();
        resFrag = resFrag != null ? resFrag : new ResidentialFragment(structures.getAllResidentials());
        comFrag = comFrag != null ? comFrag : new CommercialFragment(structures.getAllCommercials());
        roadFrag = roadFrag != null ? roadFrag : new RoadFragment(structures.getAllRoads());
        statusFrag = statusFrag != null ? statusFrag : new StatusFragment(game);

        fm.beginTransaction().add(R.id.map, mapFrag).commit();
        fm.beginTransaction().add(R.id.selector_residential, resFrag).commit();
        fm.beginTransaction().add(R.id.selector_commercial, comFrag).commit();
        fm.beginTransaction().add(R.id.selector_road, roadFrag).commit();
        fm.beginTransaction().add(R.id.status, statusFrag).commit();
    }
}
