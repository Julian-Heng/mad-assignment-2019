package curtin.edu.citysim.Core.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

import curtin.edu.citysim.Core.Exception.GameDataException;
import curtin.edu.citysim.Core.Model.Game.GameData;
import curtin.edu.citysim.Core.Model.Game.GameDataSchema.GameDataTable;
import curtin.edu.citysim.Core.Model.Game.MapData;
import curtin.edu.citysim.Core.Model.Game.Settings;

/**
 * Loads and saves a GameData object to a database
 */
public class GameDataSaveManager
{
    private static final String LOG_TAG = "SAVEMANAGER";
    private SQLiteDatabase db;

    /**
     * Database Helper that extends SQLiteOpenHelper
     * Creates a database if it doesn't exist
     */
    private class GameDataDbHelper extends SQLiteOpenHelper
    {
        private static final int VERSION = 1;
        private static final String DATABASE_NAME = "citysim_save.db";
        private static final String SQL_CREATE =
            "CREATE TABLE %s (%s TEXT, %s BLOB, %s BLOB, %s INTEGER, %s INTEGER, %s REAL, %s INTEGER)";

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
                GameDataTable.Cols.MONEY,
                GameDataTable.Cols.POPULATION,
                GameDataTable.Cols.EMPLOYMENTRATE,
                GameDataTable.Cols.GAME_TIME));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }

    /**
     * Cursor object for querying database
     */
    private class GameDataCursor extends CursorWrapper
    {
        private static final String LOG_TAG = "CURSOR";

        public GameDataCursor(Cursor cursor) { super(cursor); }

        /**
         * @return A GameData object that can be null if database contains an invalid entry
         */
        public GameData getGameData()
        {
            GameData game = null;
            try
            {
                String ID = getString(getColumnIndex(GameDataTable.Cols.ID));
                Settings settings = (Settings) Tools.convertBytesToObj(getBlob(getColumnIndex(GameDataTable.Cols.SETTINGS)));
                MapData map = (MapData) Tools.convertBytesToObj(getBlob(getColumnIndex(GameDataTable.Cols.MAP)));
                int money = getInt(getColumnIndex(GameDataTable.Cols.MONEY));
                int gameTime = getInt(getColumnIndex(GameDataTable.Cols.GAME_TIME));

                game = new GameData();
                game.setID(ID);
                game.setSettings(settings);
                game.setMap(map);
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

    public GameDataSaveManager(Context context)
    {
        Log.d(LOG_TAG, "GameDataSaveManager() called");
        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();
    }

    /**
     * @return Returns a GameData object from the database
     */
    public GameData load()
    {
        Log.d(LOG_TAG, "load() called");
        GameData game = null;

        GameDataCursor c = queryAll();

        try
        {
            c.moveToFirst();

            // Only supports getting one entry from the database at the moment
            if (c.getCount() > 0)
            {
                game = c.getGameData();
                Log.d(LOG_TAG, "load(): Successfully found entry in database");
            }
            else
            {
                // No entry in database, create new save
                Log.d(LOG_TAG, "load(): No entry in database, creating new save");
                game = new GameData(new Settings());

                // Save to database
                save(game);
            }
        }
        catch (GameDataException e)
        {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        catch (Exception e)
        {
            Log.d(LOG_TAG, "Exception caught, recreating save file");

            // Exception, create new save
            try
            {
                game = new GameData(new Settings());
                save(game);
            }
            catch (GameDataException ex)
            {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            }
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

    /**
     * @param game GameData object to save to the database
     */
    public void save(GameData game)
    {
        Log.d(LOG_TAG, "save() called");

        // Get all entries in database
        GameDataCursor c = queryAll();

        try
        {
            Log.d(LOG_TAG, "save(): Saving to database:");
            for (String i : game.toString().split("\n"))
                Log.d(LOG_TAG, "save(): " + i);

            // Construct ContentValue to be inserted to the database
            ContentValues cv = new ContentValues();

            cv.put(GameDataTable.Cols.ID, game.getID());
            cv.put(GameDataTable.Cols.SETTINGS, Tools.convertObjToBytes(game.getSettings()));
            cv.put(GameDataTable.Cols.MAP, Tools.convertObjToBytes(game.getMap()));
            cv.put(GameDataTable.Cols.MONEY, game.getMoney());
            cv.put(GameDataTable.Cols.POPULATION, game.getPopulation());
            cv.put(GameDataTable.Cols.EMPLOYMENTRATE, game.getEmploymentRate());
            cv.put(GameDataTable.Cols.GAME_TIME, game.getGameTime());

            // Check if database is empty to insert, or update
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

    /**
     * @return cursor with a catch all query
     */
    private GameDataCursor queryAll()
    {
        Log.d(LOG_TAG, "queryAll() called");
        return new GameDataCursor(db.query(GameDataTable.NAME,
                                  null, null, null,
                                  null, null, null));
    }
}
