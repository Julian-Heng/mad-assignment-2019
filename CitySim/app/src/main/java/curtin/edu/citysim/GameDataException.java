package curtin.edu.citysim;

public class GameDataException extends Exception
{
    private static String msg;
    private static final long serialVersionUID = 42L;
    public GameDataException(String msg) { msg = this.msg; }
    @Override public String getMessage() { return msg; }
}
