package curtin.edu.citysim.Core.Model.Game;

/**
 * Stores schema information of the GameData object
 */
public class GameDataSchema
{
    public static class GameDataTable
    {
        // Table name
        public static final String NAME = "game";

        public static class Cols
        {
            // Table columns
            public static final String ID = "game_id";
            public static final String SETTINGS = "settings";
            public static final String MAP = "map";
            public static final String MONEY = "money";
            public static final String POPULATION = "population";
            public static final String EMPLOYMENTRATE = "employment_rate";
            public static final String GAME_TIME = "game_time";
        }
    }
}
