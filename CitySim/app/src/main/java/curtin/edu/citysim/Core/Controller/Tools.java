package curtin.edu.citysim.Core.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Tools
{
    public static byte[] convertObjToBytes(Object o) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(o);
        return out.toByteArray();
    }

    public static Object convertBytesToObj(byte[] d) throws IOException,
                                                            ClassNotFoundException
    {
        return (new ObjectInputStream(new ByteArrayInputStream(d))).readObject();
    }
}
