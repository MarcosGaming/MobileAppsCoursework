package set08114.marcos.yourorganizer;

import java.io.Serializable;

/**
 * Created by Marcos on 13/02/2018.
 */

public class Book implements Serializable {

    private String name;    //Name of the book
    private int chapter;    //Chapter of the book
    private int page;       //Page of the book
    private String status;  //Status of the book

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
