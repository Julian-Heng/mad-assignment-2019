package curtin.edu.citysim;

import curtin.edu.citysim.GameDataSchema.GameDataTable;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.io.IOException;

public class GameDataCursor extends CursorWrapper
{
    public GameDataCursor(Cursor cursor) { super(cursor); }

    public GameData getGameData()
    {
        try
        {
            String ID = getString(getColumnIndex(GameDataTable.Cols.ID));
            Settings settings = (Settings)Utils.bytesToObj(getBlob(getColumnIndex(GameDataTable.Cols.SETTINGS)));
            MapElement[][] map = (MapElement[][])Utils.bytesToObj(getBlob(getColumnIndex(GameDataTable.Cols.MAP)));
            int numResidential = getInt(getColumnIndex(GameDataTable.Cols.NUM_RESIDENTIAL));
            int numCommercial = getInt(getColumnIndex(GameDataTable.Cols.NUM_COMMERCIAL));
            int money = getInt(getColumnIndex(GameDataTable.Cols.MONEY));
            int gameTime = getInt(getColumnIndex(GameDataTable.Cols.GAME_TIME));

            GameData game = new GameData();
            game.setID(ID);
            game.setSettings(settings);
            game.setMap(map);
            game.setNumResidential(numResidential);
            game.setNumCommercial(numCommercial);
            game.setMoney(money);
            game.setGameTime(gameTime);

            return game;
        }
        catch (Exception e)
        {
            Log.e("GAMEDATA", "exception", e);
        }

        return null;
    }
}
