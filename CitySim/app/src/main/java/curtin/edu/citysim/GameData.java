package curtin.edu.citysim;

public class GameData
{
    private GameData instance = null;
    private Settings settings;
    private MapElement[][] map;
    private int money;
    private int gameTime;

    private GameData()
    {

    }

    public GameData getInstance() { return (instance = (instance != null ? instance : new GameData())); }

    public void setSettings(Settings settings) { this.settings = settings; }
    public void setMap(MapElement[][] map) { this.map = map; }
    public void setMoney(int money) { this.money = money; }
    public void setGameTime(int gameTime) { this.gameTime = gameTime; }

    public Settings getSettings() { return settings; }
    public MapElement[][] getMap() { return map; }
    public int getMoney() { return money; }
    public int getGameTime() { return gameTime; }
}
