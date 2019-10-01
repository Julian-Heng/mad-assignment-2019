package curtin.edu.citysim.Core.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class MapElement implements Serializable
{
    private Structure struct;
    private Bitmap img;
    private String ownerName;

    public void setStruct(Structure struct) { this.struct = struct; }
    public void setImg(Bitmap img) { this.img = img; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public Structure getStruct() { return struct; }
    public Bitmap getImg() { return img; }
    public String getOwnerName() { return ownerName; }

    public String toString()
    {
        /*
        String out = "{\n";

        out += String.format("    \"struct\": \"%s\",\n", struct.toString());
        out += String.format("    \"img\": \"%s\",\n", img.toString());
        out += String.format("    \"ownerName\": \"%s\"\n}", ownerName);

        return out;
        */
        return "{\n    \"struct\": \"\",\n    \"img\": \"\",\n    \"ownerName\": \"\"\n}";
    }
}
