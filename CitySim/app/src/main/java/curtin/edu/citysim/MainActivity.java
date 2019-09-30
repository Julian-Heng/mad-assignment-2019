package curtin.edu.citysim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameData game = GameData.getInstance();
        game.setSettings(new Settings());

        try
        {
            game.regenerateGame();
            String[] log = game.toString().split("\n");
            for (String i : log)
                Log.d("GAMEDATA", "GameData: " + i);
        }
        catch (GameDataException e)
        {

        }
    }
}
