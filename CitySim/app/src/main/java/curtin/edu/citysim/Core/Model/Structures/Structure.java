package curtin.edu.citysim.Core.Model.Structures;

import java.io.Serializable;

/**
 * Structure class to store the drawable image id
 */
public abstract class Structure implements Serializable
{
    private int imageID;

    public Structure(int imageID) { this.imageID = imageID; }
    public void setImageID(int imageID) { this.imageID = imageID; }
    public int getImageID() { return imageID; }
}
