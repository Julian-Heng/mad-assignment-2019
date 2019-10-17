package curtin.edu.citysim.Core.Model.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * SerialBitmap class to encapsulate a Bitmap object and make it serializable
 */
public class SerialBitmap implements Serializable
{
    private Bitmap img;

    public SerialBitmap(Bitmap img) { this.img = img; }
    public void setImg(Bitmap img) { this.img = img; }
    public Bitmap getImg() { return img; }

    /**
     * Standalone method for writing bitmap object
     *
     * From https://stackoverflow.com/a/6003277
     * Accessed on 9/10/19
     *
     * @param out output stream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, bs);
        byte bytes[] = bs.toByteArray();
        out.write(bytes, 0, bytes.length);
    }

    /**
     * Standalone method for reading bitmap object
     *
     * From https://stackoverflow.com/a/6003277
     * Accessed on 9/10/19
     *
     * @param in input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException,
                                                         ClassNotFoundException
    {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1)
            bs.write(b);
        byte bytes[] = bs.toByteArray();
        img = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
