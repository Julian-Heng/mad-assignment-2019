package curtin.edu.citysim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button btnDetails;
    private Button btnMap;
    private Button btnSettings;

    private GameData game;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        try
        {
            GameData game = new GameData(new Settings());
            String[] log = game.toString().split("\n");

            for (String i : log)
                Log.d("GAMEDATA", "GameData: " + i);
        }
        catch (GameDataException e)
        {

        }
         */

        btnDetails = (Button)findViewById(R.id.btnDetails);
        btnMap = (Button)findViewById(R.id.btnMap);
        btnSettings = (Button)findViewById(R.id.btnSettings);

        btnDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, DetailsActivity.class));
            }
        });

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
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }
}
