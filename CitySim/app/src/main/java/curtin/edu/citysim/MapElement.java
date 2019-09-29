package curtin.edu.citysim;

import android.graphics.Bitmap;

public class MapElement
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
}
