package set08114.marcos.yourorganizer;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by Marcos on 14/02/2018.
 * Singleton Pattern Used
 */

public class InternalStorage {

    private InternalStorage instance;
    private List<Book> bookList;
    private List<Film> filmList;
    private List<Serie> serieList;

    //Singleton Constructor
    private InternalStorage(){}
    //Accessor that only allows to get the singleton instance
    public static InternalStorage getInstance
    {
        if(instance == null)
        {
            instance = new InternalStorage();

        }
    }

    private static void initialiseLists()
    {
        List<Book> books = readObject(,"books");
    }

    private static void writeObject(Context context, String key, Object object)
        throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }
    private static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }
}
