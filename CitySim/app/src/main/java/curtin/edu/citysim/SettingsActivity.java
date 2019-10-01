package curtin.edu.citysim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity
{
    Settings settings;

    EditText txtEditWidth;
    EditText txtEditHeight;
    EditText txtEditMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = (Settings)getIntent().getSerializableExtra("settings");

        txtEditWidth = (EditText)findViewById(R.id.txtEditWidth);
        txtEditHeight = (EditText)findViewById(R.id.txtEditHeight);
        txtEditMoney = (EditText)findViewById(R.id.txtEditMoney);

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
        intent.putExtra(MainActivity.SETTINGS, settings);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
