package curtin.edu.citysim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import curtin.edu.citysim.UI.Activity.MapActivity;
import curtin.edu.citysim.UI.Activity.SettingsActivity;
import curtin.edu.citysim.Core.Controller.GameDataSaveManager;
import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.StructureData;

public class MainActivity extends AppCompatActivity
{
    public static final String GAME = "game";
    public static final String STRUCT = "structures";

    private static final int REQUEST_SETTINGS = 1;
    private static final int REQUEST_MAP = 2;

    private Button btnMap;
    private Button btnSettings;

    private GameDataSaveManager saveManager = null;
    private StructureData structures = null;

    private GameData game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMap = (Button)findViewById(R.id.btnMap);
        btnSettings = (Button)findViewById(R.id.btnSettings);

        if (game == null)
        {
            saveManager = new GameDataSaveManager(this);
            game = saveManager.load();
        }

        structures = new StructureData();

        btnMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.putExtra(GAME, game);
                intent.putExtra(STRUCT, structures);
                startActivityForResult(intent, REQUEST_MAP);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra(GAME, game);
                startActivityForResult(intent, REQUEST_SETTINGS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode)
        {
            case REQUEST_SETTINGS: case REQUEST_MAP:
                game = (GameData)data.getSerializableExtra(GAME);
                break;
        }

        saveManager.save(game);
    }
}
