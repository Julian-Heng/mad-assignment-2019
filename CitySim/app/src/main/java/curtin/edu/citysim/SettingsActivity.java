package curtin.edu.citysim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity
{
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = (Settings)getIntent().getSerializableExtra("settings");

        ((EditText)findViewById(R.id.txtEditWidth)).setText(Integer.toString(settings.getIntSetting("mapWidth", 50)));
        ((EditText)findViewById(R.id.txtEditHeight)).setText(Integer.toString(settings.getIntSetting("mapHeight", 10)));
        ((EditText)findViewById(R.id.txtEditMoney)).setText(Integer.toString(settings.getIntSetting("initialMoney", 1000)));
    }

    @Override
    public void onBackPressed()
    {
        try
        {
            settings.setIntSetting("mapWidth", Integer.parseInt(((EditText) findViewById(R.id.txtEditWidth)).getText().toString()));
        }
        catch (NumberFormatException e) {}

        try
        {
            settings.setIntSetting("mapHeight", Integer.parseInt(((EditText) findViewById(R.id.txtEditHeight)).getText().toString()));
        }
        catch (NumberFormatException e) {}

        try
        {
            settings.setIntSetting("initialMoney", Integer.parseInt(((EditText) findViewById(R.id.txtEditMoney)).getText().toString()));
        }
        catch (NumberFormatException e) {}

    Intent intent = new Intent();
        intent.putExtra(MainActivity.SETTINGS, settings);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
