package curtin.edu.citysim.Core.Model.Game;

import java.io.Serializable;

import curtin.edu.citysim.Core.Model.Structures.Structure;

/**
 * MapElement class to represent each grid element
 * Author: Julian Heng (19473701)
 */
public class MapElement implements Serializable
{
    private Structure struct = null;
    private SerialBitmap img;
    private int drawId;
    private String ownerName;

    public void setStruct(Structure struct) { this.struct = struct; }
    public void setImg(SerialBitmap img) { this.img = img; }
    public void setDrawId(int drawId) { this.drawId = drawId; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public Structure getStruct() { return struct; }
    public SerialBitmap getImg() { return img; }
    public int getDrawId() { return drawId; }
    public String getOwnerName() { return ownerName; }

    public String toString()
    {
        String out = "{\n";

        out += String.format("    \"struct\": \"%s\",\n", struct != null ? struct.toString() : "");
        out += String.format("    \"drawID\": \"%s\",\n", "" + drawId);
        out += String.format("    \"ownerName\": \"%s\"\n}", ownerName);

        return out;
    }
}
