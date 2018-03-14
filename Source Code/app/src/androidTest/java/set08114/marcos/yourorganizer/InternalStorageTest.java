package set08114.marcos.yourorganizer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created Date: 11/03/2018
 * Class to test the InternalStorage Class
 * Last modified date : 11/03/2018
 */
@RunWith(AndroidJUnit4.class)
public class InternalStorageTest {
    @Test
    public void useAppContext() throws Exception
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("set08114.marcos.yourorganizer", appContext.getPackageName());
    }
    @Test
    public void addBookTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Book book = new Book();
        book.setName("Old");
        //act
        storage.addBook(book);
        //assert
        assertEquals(storage.findBook(book.getName()),book);
    }
    @Test
    public void modifyBookTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Book oldBook = new Book();
        oldBook.setName("Old");
        Book newBook = new Book();
        newBook.setName("New");
        //act
        storage.modifyBook(oldBook,newBook);
        //assert
        assertEquals(storage.findBook(newBook.getName()),newBook);
    }
    @Test
    public void removeBookTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Book book = new Book();
        book.setName("old");
        //act
        storage.addBook(book);
        storage.removeBook(book);
        //assert
        assertEquals(storage.findBook(book.getName()),null);
    }
    @Test
    public void findBookTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Book book = new Book();
        book.setName("old");
        //act
        storage.getBookList().add(book);
        //assert
        assertEquals(storage.findBook(book.getName()),book);
    }
    @Test
    public void addFilmTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Film film = new Film();
        film.setName("Old");
        //act
        storage.addFilm(film);
        //assert
        assertEquals(storage.findFilm(film.getName()),film);
    }
    @Test
    public void modifyFilmTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Film oldFilm = new Film();
        oldFilm.setName("Old");
        Film newFilm = new Film();
        newFilm.setName("New");
        //act
        storage.modifyFilm(oldFilm,newFilm);
        //assert
        assertEquals(storage.findFilm(newFilm.getName()),newFilm);
    }
    @Test
    public void removeFilmTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Film film = new Film();
        film.setName("cold");
        //act
        storage.addFilm(film);
        storage.removeFilm(film);
        //assert
        assertEquals(storage.findFilm(film.getName()),null);
    }
    @Test
    public void findFilmTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Film film = new Film();
        film.setName("old");
        //act
        storage.getFilmList().add(film);
        //assert
        assertEquals(storage.findFilm(film.getName()),film);
    }
    @Test
    public void addSeriesTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Series series = new Series();
        series.setName("Old");
        //act
        storage.addSeries(series);
        //assert
        assertEquals(storage.findSeries(series.getName()),series);
    }
    @Test
    public void modifySeriesTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Series oldSeries = new Series();
        oldSeries.setName("Old");
        Series newSeries = new Series();
        newSeries.setName("New");
        //act
        storage.modifySeries(oldSeries,newSeries);
        //assert
        assertEquals(storage.findSeries(newSeries.getName()),newSeries);
    }
    @Test
    public void removeSeriesTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Series series = new Series();
        series.setName("cold");
        //act
        storage.addSeries(series);
        storage.removeSeries(series);
        //assert
        assertEquals(storage.findSeries(series.getName()),null);
    }
    @Test
    public void findSeriesTest()
    {
        //arrange
        Context context = InstrumentationRegistry.getTargetContext();
        InternalStorage storage = InternalStorage.getInstance(context);
        Series series = new Series();
        series.setName("old");
        //act
        storage.getSeriesList().add(series);
        //assert
        assertEquals(storage.findSeries(series.getName()),series);
    }

}
