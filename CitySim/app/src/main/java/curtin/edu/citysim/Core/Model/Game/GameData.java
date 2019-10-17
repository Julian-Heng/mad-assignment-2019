package curtin.edu.citysim.Core.Model.Game;

import curtin.edu.citysim.Core.Exception.GameDataException;
import curtin.edu.citysim.Core.Model.Structures.Commercial;
import curtin.edu.citysim.Core.Model.Structures.Residential;
import curtin.edu.citysim.Core.Model.Structures.Road;
import curtin.edu.citysim.Core.Model.Structures.Structure;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.Random;

/**
 * A GameData class keeps track of the game, including game mechanics and game state
 */
public class GameData implements Serializable
{
    // Current action for selected struct
    public static final int DEFAULT = 0;
    public static final int DETAILS = 1;
    public static final int DEMOLISH = 2;

    // Unique ID for storing into the database
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

    /**
     * Constructor with settings
     *
     * @param settings Settings object to setup the GameData object
     * @throws GameDataException
     */
    public GameData(Settings settings) throws GameDataException
    {
        // Cannot proceed unless settings is not null
        if (settings == null)
            throw new GameDataException("Settings is not set");

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

        // Reflect changes in settings to game state
        if (map != null)
        {
            int width = settings.getIntSetting("mapWidth", 50);
            int height = settings.getIntSetting("mapHeight", 100);

            // If map dimensions change, recreate map
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

    /**
     * Build selected struct onto coordinates
     *
     * @param i row index
     * @param j column index
     */
    public void build(int i, int j)
    {
        if (selectedStruct instanceof Residential)
            build((Residential)selectedStruct, i, j);
        else if (selectedStruct instanceof Commercial)
            build((Commercial)selectedStruct, i, j);
        else if (selectedStruct instanceof Road)
            build((Road)selectedStruct, i, j);
    }

    /**
     * Build residential to coordinate
     *
     * @param newRes new residential struct
     * @param i row index
     * @param j column index
     */
    public void build(Residential newRes, int i, int j)
    {
        int cost = settings.getIntSetting("houseBuildingCost", 100);
        if (map.adjacentToRoad(i, j) && money >= cost)
        {
            money -= cost;
            map.build(newRes, i, j);
        }
    }

    /**
     * Build residential to coordinate
     *
     * @param newComm new commercial struct
     * @param i row index
     * @param j column index
     */
    public void build(Commercial newComm, int i, int j)
    {
        int cost = settings.getIntSetting("commBuildingCost", 500);
        if (map.adjacentToRoad(i, j) && money >= cost)
        {
            money -= cost;
            map.build(newComm, i, j);
        }
    }

    /**
     * Build residential to coordinate
     *
     * @param newRoad new road struct
     * @param i row index
     * @param j column index
     */
    public void build(Road newRoad, int i, int j)
    {
        int cost = settings.getIntSetting("roadBuildingCost", 20);
        if (money >= cost)
        {
            money -= cost;
            map.build(newRoad, i, j);
        }
    }

    /**
     * Move the game forwards one time unit
     */
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

    /**
     * Check game over condition
     * @return true or false
     */
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
