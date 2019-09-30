package curtin.edu.citysim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    public static final String GAME = "game";
    public static final String SETTINGS = "settings";

    private static final int REQUEST_SETTINGS = 1;
    private static final int REQUEST_MAP = 2;

    private Button btnMap;
    private Button btnSettings;

    private GameData game = null;
    private Settings settings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (game == null)
        {
            try
            {
                game = new GameData(settings == null ? new Settings() : settings);
                settings = game.getSettings();
            }
            catch (GameDataException e) {}
        }

        btnMap = (Button)findViewById(R.id.btnMap);
        btnSettings = (Button)findViewById(R.id.btnSettings);

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
                break;

            case REQUEST_MAP:
                game = (GameData)data.getSerializableExtra(GAME);
                settings = (Settings)data.getSerializableExtra(SETTINGS);
                break;
        }
    }
}
