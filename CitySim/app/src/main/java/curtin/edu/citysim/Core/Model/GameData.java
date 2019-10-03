package curtin.edu.citysim.Core.Model;

import curtin.edu.citysim.Core.Exception.GameDataException;
import curtin.edu.citysim.R;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.Random;

public class GameData implements Serializable
{
    private String UUID;

    private Settings settings = null;
    private MapData map = null;

    private int numResidential = 0;
    private int numCommercial = 0;

    private int money = 0;
    private int salary = 0;
    int population = 0;
    double employmentRate = 0.0;
    private int gameTime = 0;

    public GameData() {}

    public GameData(Settings settings) throws GameDataException
    {
        if (settings == null)
            throw new GameDataException("Settings is not set");

        ContentValues cv = new ContentValues();
        Random rng = new Random();

        UUID = java.util.UUID.randomUUID().toString();
        setSettings(settings);

        int width = settings.getIntSetting("mapWidth", 50);
        int height = settings.getIntSetting("mapHeight", 100);

        /*
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
         */
        map = new MapData(width, height);

        money = settings.getIntSetting("initialMoney", 1000);
        gameTime = 0;
    }

    public void setID(String UUID) { this.UUID = UUID; }
    public void setSettings(Settings settings) { this.settings = settings; }
    public void setMap(MapData map) { this.map = map; }

    public void setNumResidential(int numResidential) { this.numResidential = numResidential; }
    public void setNumCommercial(int numCommercial) { this.numCommercial = numCommercial; }

    public void setMoney(int money) { this.money = money; }
    public void setPopulation(int population) { this.population = population; }
    public void setEmploymentRate(double employmentRate) { this.employmentRate = employmentRate; }
    public void setGameTime(int gameTime) { this.gameTime = gameTime; }

    public String getID() { return UUID; }
    public Settings getSettings() { return settings; }
    public MapData getMap() { return map; }

    public int getNumResidential() { return numResidential; }
    public int getNumCommercial() { return numCommercial; }

    public int getMoney() { return money; }
    public int getSalary() { return salary; }
    public int getPopulation() { return population; }
    public double getEmploymentRate() { return employmentRate; }
    public int getGameTime() { return gameTime; }

    public void step()
    {
        int familySize = settings.getIntSetting("familySettings");
        int shopSize = settings.getIntSetting("shopSize");
        int individualSalary = settings.getIntSetting("salary");
        double taxRate = settings.getDoubleSetting("taxRate");
        int serviceCost = settings.getIntSetting("serviceCost");

        population = familySize * numResidential;
        employmentRate = Math.min(1.0, numCommercial * shopSize / population);

        salary = (int)(population * (employmentRate * individualSalary * taxRate - serviceCost));
        money += salary;
    }

    public boolean isGameOver() { return money < 0; }

    public String toString()
    {
        String out = "{\n";
        out += "    \"settings\": " + settings.toString().replaceAll("\n", "\n    ") + ",\n";
        /*
        out += "    \"map\": {\n        ";

        for (MapElement[] i : map)
            for (MapElement j : i)
                out += j.toString().replaceAll("\n", "\n        ") + ",\n        ";
         */
        out += "    \"map\": " + map.toString().replaceAll("\n", "\n    ") + ",\n";

        out = out.replaceAll(",\n\\s{8}$", "\n    },\n");
        out += "    \"money\": " + Integer.toString(money) + ",\n";
        out += "    \"salary\": " + Integer.toString(salary) + ",\n";
        out += "    \"population\": " + Integer.toString(population) + ",\n";
        out += "    \"employmentRate\": " + Double.toString(employmentRate) + ",\n";
        out += "    \"gameTime\": " + Integer.toString(gameTime) + "\n}";
        return out;
    }
}
