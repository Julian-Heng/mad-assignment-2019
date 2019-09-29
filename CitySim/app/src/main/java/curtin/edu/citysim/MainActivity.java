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
            Log.d("GAMEDATA", "GameData toString():\n" + game.toString());
        }
        catch (GameDataException e)
        {

        }
    }
}
