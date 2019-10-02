package curtin.edu.citysim.Core.Model;

import java.io.Serializable;

public abstract class Structure implements Serializable
{
    private int imageID;

    public Structure(int imageID){ this.imageID = imageID; }
    public void setImageID(int imageID) { this.imageID = imageID; }
    public int getImageID() { return imageID; }
}
