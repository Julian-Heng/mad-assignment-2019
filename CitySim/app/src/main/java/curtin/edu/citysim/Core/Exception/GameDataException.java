package curtin.edu.citysim.Core.Exception;

/**
 * Exception class for GameData
 * Author: Julian Heng (19473701)
 */
public class GameDataException extends Exception
{
    private static String msg;
    private static final long serialVersionUID = 42L;
    public GameDataException(String msg) { msg = this.msg; }
    @Override public String getMessage() { return msg; }
}
