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
 * Singleton design patter used to only have one instance of this class.
 * Last modified date : 05/03/2018
 */

public class InternalStorage
{

    private static InternalStorage instance;
    private Context context;
    //Lists
    private List<Book> bookList;
    private List<Film> filmList;
    private List<Series> seriesList;
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
        if(seriesList == null)
        {
            seriesList = new ArrayList<>();
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
    public void modifyBook(Book oldBook, Book newBook)
    {
        bookList.set(bookList.indexOf(findBook(oldBook.getName())),newBook);
        sortBookList();
    }
    public void removeBook(Book book)
    {
        bookList.remove(findBook(book.getName()));
        sortBookList();
    }
    public Book findBook(String book)
    {
        Book foundBook = null;
        for (Book b : bookList)
        {
            if(b.getName().equalsIgnoreCase(book))
            {
                foundBook = b;
            }
        }
        return foundBook;
    }

    //Film list operations
    public void addFilm(Film film)
    {
        if(filmList.contains(findFilm(film.getName())))
        {
            throw new IllegalArgumentException("Film already added");
        }
        else
        {
            filmList.add(film);
            sortFilmList();
        }
    }
    public Film findFilm(String film)
    {
        Film foundFilm = null;
        for (Film f : filmList)
        {
            if(f.getName().equalsIgnoreCase(film))
            {
                foundFilm = f;
            }
        }
        return foundFilm;
    }
    public void modifyFilm(Film oldFilm, Film newFilm)
    {
        filmList.set(filmList.indexOf(findFilm(oldFilm.getName())),newFilm);
        sortFilmList();
    }
    public void removeFilm(Film film)
    {
        filmList.remove(film);
        sortFilmList();
    }

    //Series list operations
    public void addSeries(Series series)
    {
        if(seriesList.contains(findSeries(series.getName())))
        {
            throw new IllegalArgumentException("Series already added");
        }
        else
        {
            seriesList.add(series);
            sortSeriesList();
        }
    }
    public Series findSeries(String series)
    {
        Series foundSeries = null;
        for (Series s : seriesList)
        {
            if(s.getName().equalsIgnoreCase(series))
            {
                foundSeries = s;
            }
        }
        return foundSeries;
    }
    public void modifySeries(Series oldSeries, Series newSeries)
    {
        seriesList.set(seriesList.indexOf(findSeries(oldSeries.getName())),newSeries);
        sortSeriesList();
    }
    public void removeSeries(Series series)
    {
        seriesList.remove(series);
        sortSeriesList();
    }

    //Get the lists
    public List<Book> getBookList() {
        return bookList;
    }
    public List<Film> getFilmList() {
        return filmList;
    }
    public List<Series> getSeriesList() {
        return seriesList;
    }

    //Retrieves lists from internal storage
    private void initialiseLists()
    {
        try
        {
            bookList = (List<Book>) readObject(context,booksKey);
            filmList = (List<Film>) readObject(context,filmsKey);
            seriesList = (List<Series>) readObject(context,seriesKey);
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
            writeObject(context,seriesKey, seriesList);
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
        return ois.readObject();
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
    //Method to sort series list
    private void sortSeriesList()
    {
        Collections.sort(seriesList, new Comparator<Series>() {
            @Override
            public int compare(Series s1, Series s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });
    }
}
