package set08114.marcos.yourorganizer;

import java.util.Date;

/**
 * Created by Marcos on 13/02/2018.
 */

public class Film {

    private String name;        //Name of the film
    private Date releaseDate;   //Release date of the film
    private String status;      //The status of the film

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
