package curtin.edu.citysim.Core.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility class for converting objects to bytes and vice versa
 * Author: Julian Heng (19473701)
 */
public class Tools
{
    /**
     * Converts an object to a byte array
     *
     * @param o Input object that implements serializable
     * @return byte array of the object
     * @throws IOException
     */
    public static byte[] convertObjToBytes(Object o) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(o);
        return out.toByteArray();
    }

    /**
     * Converts a byte array to an object
     *
     * @param d Input byte array of the object
     * @return Converted object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object convertBytesToObj(byte[] d) throws IOException,
                                                            ClassNotFoundException
    {
        return (new ObjectInputStream(new ByteArrayInputStream(d))).readObject();
    }
}
