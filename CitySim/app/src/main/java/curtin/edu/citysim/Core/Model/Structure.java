package curtin.edu.citysim.Core.Model;

public abstract class Structure
{
    private int imageID;

    public Structure(int imageID) { this.imageID = imageID; }
    public void setImageID(int imageID) { this.imageID = imageID; }
    public int getImageID() { return imageID; }
}
