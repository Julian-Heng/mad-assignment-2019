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
    // Identifiers for intents
    public static final String GAME = "game";
    public static final String STRUCT = "structures";

    // Request code
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

        // Find views
        btnMap = (Button)findViewById(R.id.btnMap);
        btnSettings = (Button)findViewById(R.id.btnSettings);

        // If game is null, load from database
        // Normally called on app startup
        if (game == null)
        {
            saveManager = new GameDataSaveManager(this);
            game = saveManager.load();
        }

        structures = new StructureData();

        // Call Map activity
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

        // Call Settings activity
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

        // Check if game is over, change behavior of buttons
        checkGameOver();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Don't take action if result is not ok
        if (resultCode != RESULT_OK)
            return;

        // Save game every time we exit from an activity
        saveManager.save((game = (GameData)data.getSerializableExtra(GAME)));
        checkGameOver();
    }

    /**
     * Change button behavior if game is over
     */
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
