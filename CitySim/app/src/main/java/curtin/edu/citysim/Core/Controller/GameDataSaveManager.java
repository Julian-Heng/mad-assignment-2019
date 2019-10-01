package curtin.edu.citysim.Core.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import curtin.edu.citysim.Core.Model.Settings;

public class GameDataSaveManager
{
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
