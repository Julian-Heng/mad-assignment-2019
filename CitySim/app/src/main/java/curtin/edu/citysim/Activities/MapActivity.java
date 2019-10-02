package curtin.edu.citysim.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import java.nio.channels.Selector;

import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.Settings;
import curtin.edu.citysim.Core.Model.StructureData;
import curtin.edu.citysim.MainActivity;
import curtin.edu.citysim.R;

public class MapActivity extends AppCompatActivity
{
    private GameData game;
    private Settings settings;
    private StructureData structs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        game = (GameData)getIntent().getSerializableExtra(MainActivity.GAME);
        settings = game.getSettings();
        structs = (StructureData)getIntent().getSerializableExtra(MainActivity.STRUCT);

        FragmentManager fm = getSupportFragmentManager();

        MapFragment fragMap = (MapFragment)fm.findFragmentById(R.id.map);
        SelectorFragment fragSelect = (SelectorFragment)fm.findFragmentById(R.id.selector);
        StatusFragment fragStatus = (StatusFragment)fm.findFragmentById(R.id.status);

        fragMap = fragMap == null ? new MapFragment(game, settings) : fragMap;
        fragSelect = fragSelect == null ? new SelectorFragment(structs) : fragSelect;
        fragStatus = fragStatus == null ? new StatusFragment() : fragStatus;

        fragMap.setSelector(fragSelect);

        fm.beginTransaction().add(R.id.map, fragMap).commit();
        fm.beginTransaction().add(R.id.selector, fragSelect).commit();
        fm.beginTransaction().add(R.id.status, fragStatus).commit();
    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.GAME, game);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
