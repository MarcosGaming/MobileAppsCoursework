package set08114.marcos.yourorganizer;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Marcos on 14/02/2018.
 * Singleton Pattern Used
 */

public class InternalStorage
{

    private static InternalStorage instance;
    private static Context context;
    //Lists
    private List<Book> bookList;
    private List<Film> filmList;
    private List<Serie> serieList;
    //Storage keys
    private String booksKey = "bookList";
    private String filmsKey = "filmList";
    private String seriesKey = "seriesList";

    //Singleton Constructor
    private InternalStorage(Context context)
    {
        this.context = context;
        this.initialiseLists();
        //If there is no record of the lists create new lists
        if(bookList == null)
        {
            bookList = new ArrayList<>();
        }
        if(filmList == null)
        {
            filmList = new ArrayList<>();
        }
        if(serieList == null)
        {
            serieList = new ArrayList<>();
        }
    }
    //Accessor that only allows to get the singleton instance
    public static InternalStorage getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new InternalStorage(context.getApplicationContext());
        }
        return instance;
    }

    //Book list operations
    public void addBook(Book book)
    {
        if(bookList.contains(findBook(book.getName())))
        {
            throw new IllegalArgumentException("Book already added");
        }
        else
        {
            bookList.add(book);
            sortBookList();
        }

    }
    public void removeBook(Book book)
    {
        bookList.remove(book);
        sortBookList();
    }
    public Book findBook(String book)
    {
        Book foundBook = null;
        for (Book b : bookList)
        {
            if(b.getName().equals(book))
            {
                foundBook = b;
            }
        }
        return foundBook;
    }
    //Film list operations
    public void addFilm(Film film)
    {
        filmList.add(film);
        sortFilmList();
    }
    public void removeFilm(Film film)
    {
        filmList.remove(film);
        sortFilmList();
    }
    //Serie list operations
    public void addSerie(Serie serie)
    {
        serieList.add(serie);
        sortSerieList();
    }
    public void removeSerie(Serie serie)
    {
        serieList.remove(serie);
        sortSerieList();
    }
    public List<Book> getBookList() {
        return bookList;
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    public List<Serie> getSerieList() {
        return serieList;
    }

    //Retrieves lists from internal storage
    private void initialiseLists()
    {
        try
        {
            bookList = (List<Book>) readObject(context,booksKey);
            filmList = (List<Film>) readObject(context,filmsKey);
            serieList = (List<Serie>) readObject(context,seriesKey);
        }
        catch(IOException e)
        {
            Log.e(TAG, e.getMessage());
        }
        catch(ClassNotFoundException e)
        {
            Log.e(TAG, e.getMessage());
        }
    }
    //Store lists in internal storage
    public void storeLists()
    {
        try
        {
            writeObject(context,booksKey,bookList);
            writeObject(context,filmsKey,filmList);
            writeObject(context,seriesKey,serieList);
        }
        catch(IOException e)
        {
            Log.e(TAG, e.getMessage());
        }
    }
    //Write objects to internal storage
    private static void writeObject(Context context, String key, Object object)
        throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }
    //Retrieve objects from internal storage
    private static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }

    //Method to sort book lists
    private void sortBookList()
    {
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getName().compareTo(b2.getName());
            }
        });
    }
    //Method to sort film list
    private void sortFilmList()
    {
        Collections.sort(filmList, new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
    }
    //Method to sort serie list
    private void sortSerieList()
    {
        Collections.sort(serieList, new Comparator<Serie>() {
            @Override
            public int compare(Serie s1, Serie s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });
    }
}
