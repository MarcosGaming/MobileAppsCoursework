package set08114.marcos.yourorganizer;

/**
 * Created by Marcos on 13/02/2018.
 */

public class Serie {

    private String name;    //Name of the serie
    private int season;     //Season of the serie
    private int episode;    //Episode of the serie
    private String status;  //Status of the serie

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
