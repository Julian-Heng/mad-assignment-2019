package curtin.edu.citysim.Core.Model.Game;

import java.io.Serializable;
import java.util.Random;

import curtin.edu.citysim.Core.Model.Structures.Commercial;
import curtin.edu.citysim.Core.Model.Structures.Residential;
import curtin.edu.citysim.Core.Model.Structures.Road;
import curtin.edu.citysim.Core.Model.Structures.Structure;
import curtin.edu.citysim.R;

/**
 * MapData class to represent the game map/grid
 */
public class MapData implements Serializable
{
    private MapElement[][] map;

    private int width;
    private int height;

    private int numResidential = 0;
    private int numCommercial = 0;
    private int numRoads = 0;

    /**
     * Construct a new MapData with width and height dimensions
     *
     * @param width
     * @param height
     */
    public MapData(int width, int height)
    {
        this.height = height;
        this.width = width;

        Random rng = new Random();
        map = new MapElement[height][width];

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                map[i][j] = new MapElement();
                map[i][j].setOwnerName("");

                // Randomize ground
                switch (rng.nextInt() % 4)
                {
                    case 1: map[i][j].setDrawId(R.drawable.ic_grass1); break;
                    case 2: map[i][j].setDrawId(R.drawable.ic_grass2); break;
                    case 3: map[i][j].setDrawId(R.drawable.ic_grass3); break;
                    default: map[i][j].setDrawId(R.drawable.ic_grass4); break;
                }
            }
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getNumResidential() { return numResidential; }
    public int getNumCommercial() { return numCommercial; }
    public int getNumRoad() { return numRoads; }
    public MapElement getElement(int i, int j) { return map[i][j]; }

    public void setElement(int i, int j, MapElement newElement) { map[i][j] = newElement; }

    /**
     * Check if selected grid is adjacent to a road struct
     *
     * @param i row index
     * @param j column index
     * @return true or false
     */
    public boolean adjacentToRoad(int i, int j)
    {
        return (getElement(Math.max(0, i - 1), j).getStruct() instanceof Road ||
                getElement(Math.min(height - 1, i + 1), j).getStruct() instanceof Road ||
                getElement(i, Math.max(0, j - 1)).getStruct() instanceof Road ||
                getElement(i, Math.min(width - 1, j + 1)).getStruct() instanceof Road);
    }

    public void build(Residential newRes, int i, int j)
    {
        numResidential++;
        map[i][j].setStruct(newRes);
    }

    public void build(Commercial newComm, int i, int j)
    {
        numCommercial++;
        map[i][j].setStruct(newComm);
    }

    public void build(Road newRoad, int i, int j)
    {
        numRoads++;
        map[i][j].setStruct(newRoad);
    }

    /**
     * Demolish a struct at grid
     *
     * @param i row index
     * @param j column index
     */
    public void demolish(int i, int j)
    {
        Structure temp = map[i][j].getStruct();
        map[i][j].setStruct(null);
        map[i][j].setImg(null);

        // Update number count
        if (temp instanceof Residential)
            numResidential--;
        else if (temp instanceof Commercial)
            numCommercial--;
        else if (temp instanceof  Road)
            numRoads--;
    }

    public String toString()
    {
        String out = "{\n    ";

        for (MapElement[] i : map)
            for (MapElement j : i)
                out += j.toString().replaceAll("\n", "\n    ") + ",\n    ";

        return out.replaceAll(",\n    $", "\n}");
    }
}
