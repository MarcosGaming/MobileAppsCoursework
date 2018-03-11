package set08114.marcos.yourorganizer;

import java.io.Serializable;

/**
 * Created by Marcos on 13/02/2018.
 * Class that defines the objects of type Film.
 * Last modified date: 09/03/2018.
 */

public class Film implements Serializable {

    private String name;        //Name of the film
    private String releaseDate; //Release date of the film
    private String status;      //The status of the film

    //Getter and setter for the name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //Getter and setter for the release date
    String getReleaseDate() {
        return releaseDate;
    }
    void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    //Getter and setter for the status
    String getStatus() {
        return status;
    }
    void setStatus(String status) {
        this.status = status;
    }
}
