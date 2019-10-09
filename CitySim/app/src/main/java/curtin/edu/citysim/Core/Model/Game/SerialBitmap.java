package curtin.edu.citysim.Core.Model.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SerialBitmap implements Serializable
{
    private Bitmap img;

    public SerialBitmap(Bitmap img) { this.img = img; }
    public void setImg(Bitmap img) { this.img = img; }
    public Bitmap getImg() { return img; }

    // From https://stackoverflow.com/a/6003277
    // Accessed on 9/10/19
    private void writeObject(java.io.ObjectOutputStream out) throws IOException
    {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, bs);
        byte bytes[] = bs.toByteArray();
        out.write(bytes, 0, bytes.length);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException,
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
