package curtin.edu.citysim.Core.Model;

import java.io.Serializable;
import java.util.Random;

import curtin.edu.citysim.R;

public class MapData implements Serializable
{
    private int width;
    private int height;

    private MapElement[][] map;

    public MapData(int width, int height)
    {
        this.height = height;
        this.width = width;
        generateGrid();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public MapElement getElement(int i, int j) { return map[i][j]; }

    public String toString()
    {
        String out = "{\n    ";

        for (MapElement[] i : map)
            for (MapElement j : i)
                out += j.toString().replaceAll("\n", "\n    ") + ",\n    ";

        return out.replaceAll(",\n    $", "\n}");
    }

    private void generateGrid()
    {
        Random rng = new Random();
        map = new MapElement[width][height];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                int drawId;
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
}
