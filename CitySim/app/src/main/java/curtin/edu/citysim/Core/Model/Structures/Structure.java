package curtin.edu.citysim.Core.Model.Structures;

import java.io.Serializable;

/**
 * Structure class to store the drawable image id
 * Author: Julian Heng (19473701)
 */
public abstract class Structure implements Serializable
{
    private int imageID;

    public Structure(int imageID) { this.imageID = imageID; }
    public void setImageID(int imageID) { this.imageID = imageID; }
    public int getImageID() { return imageID; }
}
