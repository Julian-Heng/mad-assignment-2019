package curtin.edu.citysim.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import curtin.edu.citysim.Core.Exception.GameDataException;
import curtin.edu.citysim.Core.Model.Game.GameData;
import curtin.edu.citysim.Core.Model.Game.Settings;
import curtin.edu.citysim.MainActivity;
import curtin.edu.citysim.R;

public class SettingsActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "SETTINGSACTIVITY";

    GameData game;
    Settings settings;

    EditText txtEditWidth;
    EditText txtEditHeight;
    EditText txtEditMoney;

    Button btnResetSettings;
    Button btnResetMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get data from activity
        game = (GameData)getIntent().getSerializableExtra(MainActivity.GAME);
        settings = game.getSettings();

        // Find views
        txtEditWidth = (EditText)findViewById(R.id.txtEditWidth);
        txtEditHeight = (EditText)findViewById(R.id.txtEditHeight);
        txtEditMoney = (EditText)findViewById(R.id.txtEditMoney);

        btnResetSettings = (Button)findViewById(R.id.btnResetSettings);
        btnResetMap = (Button)findViewById(R.id.btnResetMap);

        setTextBoxes();

        btnResetSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                settings = new Settings();
                game.setSettings(settings);
                setTextBoxes();
            }
        });

        btnResetMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    // Nasty hack
                    String oldID = game.getID();
                    game = new GameData(new Settings());
                    game.setID(oldID);

                    settings = game.getSettings();
                }
                catch (GameDataException e)
                {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }

                setTextBoxes();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        // Don't allow changing settings if game is already over
        if (! game.isGameOver())
        {
            int[] settingValues = new int[3];

            try
            {
                settingValues[0] = Integer.parseInt(txtEditWidth.getText().toString());
                settingValues[1] = Integer.parseInt(txtEditHeight.getText().toString());
                settingValues[2] = Integer.parseInt(txtEditMoney.getText().toString());

                settingValues[0] = settingValues[0] < 1 ? settings.getIntSetting("mapWidth") : settingValues[0];
                settingValues[1] = settingValues[1] < 1 ? settings.getIntSetting("mapHeight") : settingValues[1];

                settings.setSetting("mapWidth", settingValues[0]);
                settings.setSetting("mapHeight", settingValues[1]);
                settings.setSetting("initialMoney", settingValues[2]);

                game.setSettings(settings);
            }
            catch (NumberFormatException e)
            {
            }
        }

        // Return data back to calling activity
        Intent intent = new Intent();
        intent.putExtra(MainActivity.GAME, game);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void setTextBoxes()
    {
        // Update the text in the edit text box to reflect changes in the settings object
        txtEditWidth.setText(Integer.toString(settings.getIntSetting("mapWidth", 50)));
        txtEditHeight.setText(Integer.toString(settings.getIntSetting("mapHeight", 10)));
        txtEditMoney.setText(Integer.toString(settings.getIntSetting("initialMoney", 1000)));
    }
}
