package set08114.marcos.yourorganizer;

import java.io.Serializable;

/**
 * Created by Marcos on 13/02/2018.
 * Class that defines the objects of type Series.
 * Last modified date : 09/03/2018.
 */

public class Series implements Serializable {

    private String name;    //Name of the series
    private String season;  //Season of the series
    private String episode; //Episode of the series
    private String status;  //Status of the series

    //Getter and Setter for the name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //Getter and setter for the season
    String getSeason() {
        return season;
    }
    void setSeason(String season) {
        this.season = season;
    }
    //Getter and setter for the episode
    String getEpisode() {
        return episode;
    }
    void setEpisode(String episode) {
        this.episode = episode;
    }
    //Getter and setter for the status
    String getStatus() {
        return status;
    }
    void setStatus(String status) {
        this.status = status;
    }
}
