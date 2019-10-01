package curtin.edu.citysim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import curtin.edu.citysim.GameDataSchema.GameDataTable;

public class GameDataDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "citysim_save.db";
    private static final String SQL_CREATE =
        "CREATE TABLE %s (%s TEXT, %s BLOB, %s BLOB, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER)";

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
                                             GameDataTable.Cols.GAME_TIME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
