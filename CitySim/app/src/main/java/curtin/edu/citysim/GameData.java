package curtin.edu.citysim;

public class GameData
{
    private static GameData instance = null;

    private Settings settings = null;
    private MapElement[][] map = null;
    private int money = 0;
    private int gameTime = 0;

    public void setSettings(Settings settings) { this.settings = settings; }
    public void setMap(MapElement[][] map) { this.map = map; }
    public void setMoney(int money) { this.money = money; }
    public void setGameTime(int gameTime) { this.gameTime = gameTime; }

    public Settings getSettings() { return settings; }
    public MapElement[][] getMap() { return map; }
    public int getMoney() { return money; }
    public int getGameTime() { return gameTime; }

    public GameData(Settings settings) throws GameDataException
    {
        if (settings == null)
            throw new GameDataException("Settings is not set");

        setSettings(settings);

        int width = settings.getIntSetting("mapWidth", 50);
        int height = settings.getIntSetting("mapHeight", 100);

        map = new MapElement[width][height];

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                map[i][j] = new MapElement();

        money = settings.getIntSetting("initialMoney", 1000);
        gameTime = 0;
    }

    public String toString()
    {
        String out = "{\n";
        out += "    \"settings\": " + settings.toString().replaceAll("\n", "\n    ") + ",\n";
        out += "    \"map\": {\n        ";

        for (MapElement[] i : map)
            for (MapElement j : i)
                out += j.toString().replaceAll("\n", "\n        ") + ",\n        ";

        out = out.replaceAll(",\n\\s{8}$", "\n    },\n");
        out += "    \"money\": " + Integer.toString(money) + ",\n";
        out += "    \"gameTime\": " + Integer.toString(gameTime) + "\n}";
        return out;
    }
}
