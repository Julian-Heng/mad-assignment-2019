package curtin.edu.citysim.Core.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import curtin.edu.citysim.Core.Exception.GameDataException;
import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.GameDataSchema;
import curtin.edu.citysim.Core.Model.GameDataSchema.GameDataTable;
import curtin.edu.citysim.Core.Model.MapData;
import curtin.edu.citysim.Core.Model.MapElement;
import curtin.edu.citysim.Core.Model.Settings;

public class GameDataSaveManager
{
    private class GameDataDbHelper extends SQLiteOpenHelper
    {
        private static final int VERSION = 1;
        private static final String DATABASE_NAME = "citysim_save.db";
        private static final String SQL_CREATE =
            "CREATE TABLE %s (%s TEXT, %s BLOB, %s BLOB, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s REAL, %s INTEGER)";

        public GameDataDbHelper(Context context)
        {
            super(context, DATABASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Log.d("GAMEDATADBHELPER", "onCreate() called");
            db.execSQL(String.format(SQL_CREATE, GameDataTable.NAME,
                GameDataTable.Cols.ID,
                GameDataTable.Cols.SETTINGS,
                GameDataTable.Cols.MAP,
                GameDataTable.Cols.NUM_RESIDENTIAL,
                GameDataTable.Cols.NUM_COMMERCIAL,
                GameDataTable.Cols.MONEY,
                GameDataTable.Cols.POPULATION,
                GameDataTable.Cols.EMPLOYMENTRATE,
                GameDataTable.Cols.GAME_TIME));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }

    private class GameDataCursor extends CursorWrapper
    {
        private static final String LOG_TAG = "CURSOR";

        public GameDataCursor(Cursor cursor) { super(cursor); }

        public GameData getGameData()
        {
            GameData game = null;
            try
            {
                String ID = getString(getColumnIndex(GameDataTable.Cols.ID));
                Settings settings = (Settings) Tools.convertBytesToObj(getBlob(getColumnIndex(GameDataTable.Cols.SETTINGS)));
                MapData map = (MapData) Tools.convertBytesToObj(getBlob(getColumnIndex(GameDataTable.Cols.MAP)));
                int numResidential = getInt(getColumnIndex(GameDataTable.Cols.NUM_RESIDENTIAL));
                int numCommercial = getInt(getColumnIndex(GameDataTable.Cols.NUM_COMMERCIAL));
                int money = getInt(getColumnIndex(GameDataTable.Cols.MONEY));
                int gameTime = getInt(getColumnIndex(GameDataTable.Cols.GAME_TIME));

                game = new GameData();
                game.setID(ID);
                game.setSettings(settings);
                game.setMap(map);
                game.setNumResidential(numResidential);
                game.setNumCommercial(numCommercial);
                game.setMoney(money);
                game.setGameTime(gameTime);
            }
            catch (Exception e)
            {
                Log.e(LOG_TAG, e.getMessage(), e);
            }

            return game;
        }
    }

    private static final String LOG_TAG = "SAVEMANAGER";
    private SQLiteDatabase db;

    public GameDataSaveManager(Context context)
    {
        Log.d(LOG_TAG, "GameDataSaveManager() called");
        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public GameData load()
    {
        Log.d(LOG_TAG, "load() called");
        GameData game = null;

        GameDataCursor c = queryAll();

        try
        {
            c.moveToFirst();
            if (c.getCount() > 0)
            {
                game = c.getGameData();
                Log.d(LOG_TAG, "load(): Successfully found entry in database");
            }
            else
            {
                Log.d(LOG_TAG, "load(): No entry in database, creating new save");
                game = new GameData(new Settings());
                save(game);
            }
        }
        catch (GameDataException e)
        {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        finally
        {
            c.close();
        }

        Log.d(LOG_TAG, "load(): Loaded from database:");
        for (String i : game.toString().split("\n"))
            Log.d(LOG_TAG, "load(): " + i);

        return game;
    }

    public void save(GameData game)
    {
        Log.d(LOG_TAG, "save() called");

        GameDataCursor c = queryAll();

        try
        {
            Log.d(LOG_TAG, "save(): Saving to database:");
            for (String i : game.toString().split("\n"))
                Log.d(LOG_TAG, "save(): " + i);

            ContentValues cv = new ContentValues();

            cv.put(GameDataTable.Cols.ID, game.getID());
            cv.put(GameDataTable.Cols.SETTINGS, Tools.convertObjToBytes(game.getSettings()));
            cv.put(GameDataTable.Cols.MAP, Tools.convertObjToBytes(game.getMap()));
            cv.put(GameDataTable.Cols.NUM_RESIDENTIAL, game.getNumResidential());
            cv.put(GameDataTable.Cols.NUM_COMMERCIAL, game.getNumCommercial());
            cv.put(GameDataTable.Cols.MONEY, game.getMoney());
            cv.put(GameDataTable.Cols.POPULATION, game.getPopulation());
            cv.put(GameDataTable.Cols.EMPLOYMENTRATE, game.getEmploymentRate());
            cv.put(GameDataTable.Cols.GAME_TIME, game.getGameTime());

            if (c.getCount() > 0)
            {
                db.update(GameDataTable.NAME, cv,
                          GameDataTable.Cols.ID + " = ?",
                          new String[] {String.valueOf(game.getID())});
            }
            else
                db.insert(GameDataTable.NAME, null, cv);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        finally
        {
            c.close();
        }
    }

    private GameDataCursor queryAll()
    {
        Log.d(LOG_TAG, "queryAll() called");
        return new GameDataCursor(db.query(GameDataTable.NAME,
                                  null, null, null,
                                  null, null, null));
    }
}
