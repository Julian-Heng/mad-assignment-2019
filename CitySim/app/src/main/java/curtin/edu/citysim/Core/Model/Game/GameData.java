package curtin.edu.citysim.Core.Model.Game;

import curtin.edu.citysim.Core.Exception.GameDataException;
import curtin.edu.citysim.Core.Model.Structures.Commercial;
import curtin.edu.citysim.Core.Model.Structures.Residential;
import curtin.edu.citysim.Core.Model.Structures.Road;
import curtin.edu.citysim.Core.Model.Structures.Structure;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.Random;

public class GameData implements Serializable
{
    public static final int DEFAULT = 0;
    public static final int DETAILS = 1;
    public static final int DEMOLISH = 2;

    private String UUID;

    private Settings settings = null;
    private MapData map = null;

    private int money = 0;
    private int salary = 0;
    private int population = 0;
    private double employmentRate = 0.0;
    private int gameTime = 0;

    private Structure selectedStruct = null;
    private int mode;

    public GameData() {}

    public GameData(Settings settings) throws GameDataException
    {
        if (settings == null)
            throw new GameDataException("Settings is not set");

        ContentValues cv = new ContentValues();
        Random rng = new Random();

        UUID = java.util.UUID.randomUUID().toString();
        this.settings = settings;

        int width = settings.getIntSetting("mapWidth", 50);
        int height = settings.getIntSetting("mapHeight", 100);
        map = new MapData(width, height);

        money = settings.getIntSetting("initialMoney", 1000);
        gameTime = 0;
    }

    public void setID(String UUID) { this.UUID = UUID; }

    public void setSettings(Settings settings)
    {
        this.settings = settings;
        money = settings.getIntSetting("initialMoney", 1000);

        if (map != null)
        {
            int width = settings.getIntSetting("mapWidth", 50);
            int height = settings.getIntSetting("mapHeight", 100);

            if (width != map.getWidth() || height != map.getHeight())
                map = new MapData(width, height);
        }
    }

    public void setMap(MapData map) { this.map = map; }

    public void setMoney(int money) { this.money = money; }
    public void setPopulation(int population) { this.population = population; }
    public void setEmploymentRate(double employmentRate) { this.employmentRate = employmentRate; }
    public void setGameTime(int gameTime) { this.gameTime = gameTime; }

    public void setSelectedStruct(Structure selectedStruct) { this.selectedStruct = selectedStruct; }
    public void setMode(int mode) { this.mode = this.mode == mode ? DEFAULT : mode; }

    public String getID() { return UUID; }
    public Settings getSettings() { return settings; }
    public MapData getMap() { return map; }

    public int getMoney() { return money; }
    public int getSalary() { return salary; }
    public int getPopulation() { return population; }
    public double getEmploymentRate() { return employmentRate; }
    public int getGameTime() { return gameTime; }

    public Structure getSelectedStruct() { return selectedStruct; }
    public int getMode() { return mode; }

    public void build(int i, int j)
    {
        if (selectedStruct instanceof Residential)
            build((Residential)selectedStruct, i, j);
        else if (selectedStruct instanceof Commercial)
            build((Commercial)selectedStruct, i, j);
        else if (selectedStruct instanceof Road)
            build((Road)selectedStruct, i, j);
    }

    public void build(Residential newRes, int i, int j)
    {
        int cost = settings.getIntSetting("houseBuildingCost", 100);
        if (map.adjacentToRoad(i, j) && money >= cost)
        {
            money -= cost;
            map.build(newRes, i, j);
        }
    }

    public void build(Commercial newComm, int i, int j)
    {
        int cost = settings.getIntSetting("commBuildingCost", 500);
        if (map.adjacentToRoad(i, j) && money >= cost)
        {
            money -= cost;
            map.build(newComm, i, j);
        }
    }

    public void build(Road newRoad, int i, int j)
    {
        int cost = settings.getIntSetting("roadBuildingCost", 20);
        if (money >= cost)
        {
            money -= cost;
            map.build(newRoad, i, j);
        }
    }

    public void step()
    {
        if (! isGameOver())
        {
            int familySize = settings.getIntSetting("familySize");
            int shopSize = settings.getIntSetting("shopSize");
            int individualSalary = settings.getIntSetting("salary");
            double taxRate = settings.getDoubleSetting("taxRate");
            int serviceCost = settings.getIntSetting("serviceCost");

            population = familySize * map.getNumResidential();
            employmentRate = Math.min(1.0, map.getNumCommercial() * shopSize / (population > 0 ? population : 1));

            salary = (int) (population * (employmentRate * individualSalary * taxRate - serviceCost));
            money += salary;
            gameTime++;
        }
    }

    public boolean isGameOver() { return money < 0; }

    public String toString()
    {
        String out = "{\n";

        out += "    \"settings\": " + settings.toString().replaceAll("\n", "\n    ") + ",\n";
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
