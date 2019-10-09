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
import curtin.edu.citysim.Core.Model.Game.GameData;
import curtin.edu.citysim.Core.Model.Structures.StructureData;

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
                if (! game.isGameOver())
                {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    intent.putExtra(GAME, game);
                    intent.putExtra(STRUCT, structures);
                    startActivityForResult(intent, REQUEST_MAP);
                }
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

        checkGameOver();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        saveManager.save((game = (GameData)data.getSerializableExtra(GAME)));
        checkGameOver();
    }

    private void checkGameOver()
    {
        if (game.isGameOver())
        {
            btnMap.setEnabled(false);
            btnMap.setText("Game Over");
        }
        else
        {
            btnMap.setEnabled(true);
            btnMap.setText("Map");
        }
    }
}
