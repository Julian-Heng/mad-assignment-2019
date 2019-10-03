package curtin.edu.citysim.Core.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import curtin.edu.citysim.R;

public class MapData implements Serializable
{
    private MapElement[][] map;

    private int width;
    private int height;

    private List<Residential> residentials = new ArrayList<>();
    private List<Commercial> commercials = new ArrayList<>();
    private List<Road> roads = new ArrayList<>();

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
    public int getNumResidential() { return residentials.size(); }
    public int getNumCommercial() { return commercials.size(); }
    public int getNumRoad() { return roads.size(); }

    public MapElement getElement(int i, int j) { return map[i][j]; }

    public boolean adjacentToRoad(int i, int j)
    {
        return (getElement(Math.max(0, i - 1), j).getStruct() instanceof Road ||
                getElement(Math.min(height - 1, i + 1), j).getStruct() instanceof Road ||
                getElement(i, Math.max(0, j - 1)).getStruct() instanceof Road ||
                getElement(i, Math.min(width - 1, j + 1)).getStruct() instanceof Road);
    }

    public void build(Residential newRes, int i, int j)
    {
        residentials.add(newRes);
        map[i][j].setStruct(newRes);
    }

    public void build(Commercial newComm, int i, int j)
    {
        commercials.add(newComm);
        map[i][j].setStruct(newComm);
    }

    public void build(Road newRoad, int i, int j)
    {
        roads.add(newRoad);
        map[i][j].setStruct(newRoad);
    }

    public void demolish(int i, int j)
    {
        Structure temp = map[i][j].getStruct();
        map[i][j].setStruct(null);

        residentials.remove(temp);
        commercials.remove(temp);
        roads.remove(temp);
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
