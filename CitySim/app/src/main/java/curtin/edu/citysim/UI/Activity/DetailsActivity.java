package curtin.edu.citysim.UI.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import curtin.edu.citysim.Core.Model.Structures.Commercial;
import curtin.edu.citysim.Core.Model.Game.MapElement;
import curtin.edu.citysim.Core.Model.Structures.Residential;
import curtin.edu.citysim.Core.Model.Structures.Road;
import curtin.edu.citysim.Core.Model.Game.SerialBitmap;
import curtin.edu.citysim.Core.Model.Structures.Structure;
import curtin.edu.citysim.R;
import curtin.edu.citysim.UI.Fragment.MapFragment;

public class DetailsActivity extends AppCompatActivity
{
    private static final int REQUEST_THUMBNAIL = 1;

    private int col;
    private int row;
    private MapElement element;
    private Structure struct;

    private TextView txtRow;
    private TextView txtColumn;
    private TextView txtStructType;
    private TextView txtLabelName;

    private EditText txtEditName;
    private ImageView imgThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String structType;
        Intent intent = getIntent();

        col = intent.getIntExtra(MapFragment.COLUMN_POSITION, -1);
        row = intent.getIntExtra(MapFragment.ROW_POSITION, -1);
        element = (MapElement)intent.getSerializableExtra(MapFragment.MAP_ELEMENT);
        struct = element.getStruct();

        structType = struct instanceof Residential ? "Residential" :
                     struct instanceof Commercial ? "Commercial" :
                     struct instanceof Road ? "Road" : "Unknown";

        txtRow = (TextView)findViewById(R.id.txtRow);
        txtColumn = (TextView)findViewById(R.id.txtColumn);
        txtStructType = (TextView)findViewById(R.id.txtStructType);
        txtLabelName = (TextView)findViewById(R.id.txtLabelName);
        txtEditName = (EditText)findViewById(R.id.txtEditName);
        imgThumb = (ImageView)findViewById(R.id.imgThumb);

        txtRow.setText("Row position: " + Integer.toString(row));
        txtColumn.setText("Column position: " + Integer.toString(col));
        txtStructType.setText("Structure type: " + structType);
        txtLabelName.setText("Structure name:");
        txtEditName.setText(element.getOwnerName().isEmpty() ? structType : element.getOwnerName());

        imgThumb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_THUMBNAIL);
            }
        });

        if (element.getImg() != null)
            imgThumb.setImageBitmap(element.getImg().getImg());
    }

    @Override
    public void onBackPressed()
    {
        Log.d("DETAILS", "Pressed back");
        element.setOwnerName(txtEditName.getText().toString());

        Intent intent = new Intent();
        intent.putExtra(MapFragment.ROW_POSITION, row);
        intent.putExtra(MapFragment.COLUMN_POSITION, col);
        intent.putExtra(MapFragment.MAP_ELEMENT, element);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_THUMBNAIL)
        {
            Bitmap img = (Bitmap)data.getExtras().get("data");
            imgThumb.setImageBitmap(img);
            element.setImg(new SerialBitmap(img));
        }
    }
}
