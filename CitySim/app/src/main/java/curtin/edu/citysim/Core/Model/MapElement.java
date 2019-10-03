package curtin.edu.citysim.Core.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class MapElement implements Serializable
{
    private Structure struct = null;
    private int drawId;
    private String ownerName;

    public void setStruct(Structure struct) { this.struct = struct; }
    public void setDrawId(int drawId) { this.drawId = drawId; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public Structure getStruct() { return struct; }
    public int getDrawId() { return drawId; }
    public String getOwnerName() { return ownerName; }

    public String toString()
    {
        String out = "{\n";

        out += String.format("    \"struct\": \"%s\",\n", struct != null ? struct.toString() : "");
        out += String.format("    \"drawID\": \"%s\",\n", "" + drawId);
        out += String.format("    \"ownerName\": \"%s\"\n}", ownerName);

        return out;
        //return "{\n    \"struct\": \"\",\n    \"drawId\": \"\",\n    \"ownerName\": \"\"\n}";
    }
}
