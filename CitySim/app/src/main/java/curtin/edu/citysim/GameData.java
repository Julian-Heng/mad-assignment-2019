package curtin.edu.citysim;

import curtin.edu.citysim.GameDataSchema.GameDataTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

public class GameData implements Serializable
{
    private SQLiteDatabase db;

    private String UUID;

    private Settings settings = null;
    private MapElement[][] map = null;

    private int numResidential = 0;
    private int numCommercial = 0;

    private int money = 0;
    private int gameTime = 0;

    public GameData() {}

    public GameData(Settings settings) throws GameDataException
    {
        if (settings == null)
            throw new GameDataException("Settings is not set");

        ContentValues cv = new ContentValues();

        UUID = java.util.UUID.randomUUID().toString();
        setSettings(settings);

        int width = settings.getIntSetting("mapWidth", 50);
        int height = settings.getIntSetting("mapHeight", 100);

        map = new MapElement[width][height];

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                map[i][j] = new MapElement();

        money = settings.getIntSetting("initialMoney", 1000);
        gameTime = 0;

        if (db != null)
        {
            try
            {
                cv.put(GameDataTable.Cols.ID, getID());
                cv.put(GameDataTable.Cols.SETTINGS, Utils.objToBytes(getSettings()));
                cv.put(GameDataTable.Cols.MAP, Utils.objToBytes(getMap()));
                cv.put(GameDataTable.Cols.NUM_RESIDENTIAL, getNumResidential());
                cv.put(GameDataTable.Cols.NUM_COMMERCIAL, getNumCommercial());
                cv.put(GameDataTable.Cols.MONEY, getMoney());
                cv.put(GameDataTable.Cols.GAME_TIME, getGameTime());

                db.insert(GameDataTable.NAME, null, cv);
            }
            catch (IOException e)
            {
                Log.e("GAMEDATA", "IOException", e);
            }
        }
    }

    public void load(Context context)
    {
        GameData game = null;
        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();
        Log.d("GAMEDATA", db.toString());

        GameDataCursor c = new GameDataCursor(
            db.query(
                GameDataTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
        );

        try
        {
            c.moveToFirst();
            if (c.getCount() > 0)
            {
                game = c.getGameData();
                Log.d("GAMEDATA", "Successfully found entry in db");
            }
        }
        finally
        {
            c.close();
        }

        if (game == null)
        {
            try
            {
                Log.d("GAMEDATA", "No DB, creating new one");
                game = new GameData(new Settings());
                game.setDb(db);
                game.save();
            }
            catch (GameDataException e) {}
        }

        setID(game.getID());
        setSettings(game.getSettings());
        setMap(game.getMap());
        setNumResidential(game.getNumResidential());
        setNumCommercial(game.getNumCommercial());
        setMoney(game.getMoney());
        setGameTime(game.getGameTime());

        Log.d("GAMEDATA", "Loading from db: ");
        for (String i : this.toString().split("\n"))
        {
            Log.d("GAMEDATA", i);
        }
    }

    public void save()
    {
        int count = new GameDataCursor(
            db.query(
                GameDataTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
        ).getCount();

        try
        {
            Log.d("GAMEDATA", "Saving to db: ");
            for (String i : this.toString().split("\n"))
            {
                Log.d("GAMEDATA", i);
            }

            ContentValues cv = new ContentValues();

            cv.put(GameDataTable.Cols.ID, UUID);
            cv.put(GameDataTable.Cols.SETTINGS, Utils.objToBytes(settings));
            cv.put(GameDataTable.Cols.MAP, Utils.objToBytes(map));
            cv.put(GameDataTable.Cols.NUM_RESIDENTIAL, numResidential);
            cv.put(GameDataTable.Cols.NUM_COMMERCIAL, numCommercial);
            cv.put(GameDataTable.Cols.MONEY, money);
            cv.put(GameDataTable.Cols.GAME_TIME, gameTime);

            if (count > 0)
            {
                String[] where = {String.valueOf(getID())};
                db.update(GameDataTable.NAME, cv, String.format("%s = ?", GameDataTable.Cols.ID), where);
            }
            else
            {
                db.insert(GameDataTable.NAME, null, cv);
            }
        }
        catch (IOException e)
        {
            Log.e("GAMEDATA", "exception", e);
        }
    }

    public void setID(String UUID) { this.UUID = UUID; }
    public void setSettings(Settings settings) { this.settings = settings; }
    public void setMap(MapElement[][] map) { this.map = map; }

    public void setNumResidential(int numResidential) { this.numResidential = numResidential; }
    public void setNumCommercial(int numCommercial) { this.numCommercial = numCommercial; }

    public void setMoney(int money) { this.money = money; }
    public void setGameTime(int gameTime) { this.gameTime = gameTime; }

    public void setDb(SQLiteDatabase db) { this.db = db; }

    public String getID() { return UUID; }
    public Settings getSettings() { return settings; }
    public MapElement[][] getMap() { return map; }

    public int getNumResidential() { return numResidential; }
    public int getNumCommercial() { return numCommercial; }

    public int getMoney() { return money; }
    public int getGameTime() { return gameTime; }

    public void step()
    {
        int familySize = settings.getIntSetting("familySettings");
        int shopSize = settings.getIntSetting("shopSize");
        int salary = settings.getIntSetting("salary");
        double taxRate = settings.getDoubleSetting("taxRate");
        int serviceCost = settings.getIntSetting("serviceCost");

        int population = familySize * numResidential;
        double employmentRate = Math.min(1.0, numCommercial * shopSize / population);
        money += population * (employmentRate * salary * taxRate - serviceCost);
    }

    public boolean isGameOver() { return money < 0; }

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
