package set08114.marcos.yourorganizer;

import java.io.Serializable;

/**
 * Created by Marcos on 13/02/2018.
 * Class that defines the objects of type book.
 * Last modified date : 05/03/2018.
 */

public class Book implements Serializable {

    private String name;    //Name of the book
    private String chapter; //Chapter of the book
    private String page;    //Page of the book
    private String status;  //Status of the book

    //Getter and setter for the name
    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }
    //Getter and setter for the chapter (package-private)
    String getChapter() { return chapter; }
    void setChapter(String chapter) { this.chapter = chapter; }
    //Getter and setter for the page (package-private)
    String getPage() { return page; }
    void setPage(String page) { this.page = page; }
    //Getter and setter for the status (package-private)
    String getStatus() {
        return status;
    }
    void setStatus(String status) {
        this.status = status;
    }
}
