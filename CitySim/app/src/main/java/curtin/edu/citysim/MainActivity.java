package curtin.edu.citysim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import curtin.edu.citysim.Activities.MapActivity;
import curtin.edu.citysim.Activities.SettingsActivity;
import curtin.edu.citysim.Core.Controller.GameDataSaveManager;
import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.Settings;

public class MainActivity extends AppCompatActivity
{
    public static final String GAME = "game";
    public static final String SETTINGS = "settings";

    private static final int REQUEST_SETTINGS = 1;
    private static final int REQUEST_MAP = 2;

    private Button btnMap;
    private Button btnSettings;

    private GameDataSaveManager saveManager;
    private GameData game = null;
    private Settings settings = null;

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
            settings = game.getSettings();
        }

        btnMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra(SETTINGS, settings);
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
            case REQUEST_SETTINGS:
                settings = (Settings)data.getSerializableExtra(SETTINGS);
                game.setSettings(settings);
                break;

            case REQUEST_MAP:
                game = (GameData)data.getSerializableExtra(GAME);
                break;
        }

        saveManager.save(game);
    }
}
