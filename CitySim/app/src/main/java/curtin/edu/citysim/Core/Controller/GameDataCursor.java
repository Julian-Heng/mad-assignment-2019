package curtin.edu.citysim.Core.Controller;

import curtin.edu.citysim.Core.Model.GameDataSchema.GameDataTable;
import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.MapElement;
import curtin.edu.citysim.Core.Model.Settings;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

public class GameDataCursor extends CursorWrapper
{
    public GameDataCursor(Cursor cursor) { super(cursor); }

    public GameData getGameData()
    {
        GameData game = null;
        try
        {
            String ID = getString(getColumnIndex(GameDataTable.Cols.ID));
            Settings settings = (Settings) Tools.convertBytesToObj(getBlob(getColumnIndex(GameDataTable.Cols.SETTINGS)));
            MapElement[][] map = (MapElement[][]) Tools.convertBytesToObj(getBlob(getColumnIndex(GameDataTable.Cols.MAP)));
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
            Log.e("GAMEDATA", e.getMessage(), e);
        }

        return game;
    }
}
