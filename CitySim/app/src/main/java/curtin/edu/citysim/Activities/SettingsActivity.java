package curtin.edu.citysim.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import curtin.edu.citysim.Core.Exception.GameDataException;
import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.Settings;
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

        game = (GameData)getIntent().getSerializableExtra(MainActivity.GAME);
        settings = game.getSettings();

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
        try
        {
            settings.setIntSetting(
                "mapWidth",
                Integer.parseInt(txtEditWidth.getText().toString())
            );
        }
        catch (NumberFormatException e) {}

        try
        {
            settings.setIntSetting(
                "mapHeight",
                Integer.parseInt(txtEditHeight.getText().toString())
            );
        }
        catch (NumberFormatException e) {}

        try
        {
            settings.setIntSetting(
                "initialMoney",
                Integer.parseInt(txtEditMoney.getText().toString())
            );
        }
        catch (NumberFormatException e) {}

        Intent intent = new Intent();
        intent.putExtra(MainActivity.GAME, game);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void setTextBoxes()
    {
        txtEditWidth.setText(
            Integer.toString(settings.getIntSetting("mapWidth", 50))
        );

        txtEditHeight.setText(
            Integer.toString(settings.getIntSetting("mapHeight", 10))
        );

        txtEditMoney.setText(
            Integer.toString(settings.getIntSetting("initialMoney", 1000))
        );
    }
}
