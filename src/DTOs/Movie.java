package DTOs;

import java.io.StringReader;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author conor
 */
public class Movie
{

    private int id;
    private String title;
    private String genre;
    private String director;
    private String runtime;
    private String rating;
    private String format;
    private String year;
    private int copies;
    private String barcode;
    private String user_rating;

    /**
     *
     */
    public Movie()
    {
        this.id = 0;
        this.title = "";
        this.genre = "";
        this.director = "";
        this.runtime = "";
        this.rating = "";
        this.format = "";
        this.year = "";
        this.copies = 0;
        this.barcode = "";
        this.user_rating = "";
    }

    /**
     *
     * @param json
     */
    public Movie(JsonObject json)
    {
        this.id = json.getInt("id");
        this.title = json.getString("title");
        this.genre = json.getJsonArray("genre").toString().replace("\"", "").replace("[", "").replace("]", "");
        this.director = json.getJsonArray("director").toString().replace("\"", "").replace("[", "").replace("]", "");
        this.runtime = json.getString("runtime");
        this.rating = json.getString("rating");
        this.format = json.getString("format");
        this.year = json.getString("year");
        this.copies = json.getInt("copies");
        this.barcode = json.getString("barcode");
        this.user_rating = json.getString("user_rating");
    }

    /**
     *
     * @param title
     * @param genre
     * @param director
     */
    public Movie(String title, String genre, String director)
    {
        this.title = title;
        this.genre = genre;
        this.director = director;
    }

    /**
     *
     * @param id
     * @param title
     * @param genre
     * @param director
     * @param runtime
     * @param rating
     * @param format
     * @param year
     * @param copies
     * @param barcode
     * @param user_rating
     */
    public Movie(int id, String title, String genre, String director, String runtime, String rating, String format, String year, int copies, String barcode, String user_rating)
    {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.runtime = runtime;
        this.rating = rating;
        this.format = format;
        this.year = year;
        this.copies = copies;
        this.barcode = barcode;
        this.user_rating = user_rating;
    }

    /**
     *
     * @return
     */
    public int getId()
    {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getTitle()
    {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     *
     * @param genre
     */
    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    /**
     *
     * @return
     */
    public String getDirector()
    {
        return director;
    }

    /**
     *
     * @param director
     */
    public void setDirector(String director)
    {
        this.director = director;
    }

    /**
     *
     * @return
     */
    public String getRuntime()
    {
        return runtime;
    }

    /**
     *
     * @param runtime
     */
    public void setRuntime(String runtime)
    {
        this.runtime = runtime;
    }

    /**
     *
     * @return
     */
    public String getRating()
    {
        return rating;
    }

    /**
     *
     * @param rating
     */
    public void setRating(String rating)
    {
        this.rating = rating;
    }

    /**
     *
     * @return
     */
    public String getFormat()
    {
        return format;
    }

    /**
     *
     * @param format
     */
    public void setFormat(String format)
    {
        this.format = format;
    }

    /**
     *
     * @return
     */
    public String getYear()
    {
        return year;
    }

    /**
     *
     * @param year
     */
    public void setYear(String year)
    {
        this.year = year;
    }

    /**
     *
     * @return
     */
    public int getCopies()
    {
        return copies;
    }

    /**
     *
     * @param copies
     */
    public void setCopies(int copies)
    {
        this.copies = copies;
    }

    /**
     *
     * @return
     */
    public String getBarcode()
    {
        return barcode;
    }

    /**
     *
     * @param barcode
     */
    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    /**
     *
     * @return
     */
    public String getUser_rating()
    {
        return user_rating;
    }

    /**
     *
     * @param user_rating
     */
    public void setUser_rating(String user_rating)
    {
        this.user_rating = user_rating;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.title);
        hash = 41 * hash + Objects.hashCode(this.genre);
        hash = 41 * hash + Objects.hashCode(this.director);
        hash = 41 * hash + Objects.hashCode(this.runtime);
        hash = 41 * hash + Objects.hashCode(this.rating);
        hash = 41 * hash + Objects.hashCode(this.format);
        hash = 41 * hash + Objects.hashCode(this.year);
        hash = 41 * hash + this.copies;
        hash = 41 * hash + Objects.hashCode(this.barcode);
        hash = 41 * hash + Objects.hashCode(this.user_rating);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Movie other = (Movie) obj;
        if (this.id != other.id)
        {
            return false;
        }
        if (!Objects.equals(this.title, other.title))
        {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre))
        {
            return false;
        }
        if (!Objects.equals(this.director, other.director))
        {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public JsonObject toJson()
    {

        String i = "{"
                + "\"id\":" + this.id
                + ",\"title\":\"" + this.title + "\""
                + ",\"genre\":[\"" + this.genre.replace(",", "\",\"") + "\"]"
                + ",\"director\":[\"" + this.director.replace(",", "\",\"") + "\"]"
                + ",\"runtime\":\"" + this.runtime + "\""
                + ",\"rating\":\"" + this.rating + "\""
                + ",\"format\":\"" + this.format + "\""
                + ",\"year\":\"" + this.year + "\""
                + ",\"copies\":" + this.copies
                + ",\"barcode\":\"" + this.barcode + "\""
                + ",\"user_rating\":\"" + this.user_rating + "\""
                + "}";
        StringReader r = new StringReader(i.replaceAll("null", "N/A"));
        JsonReader reader = Json.createReader(r);
        JsonObject jSon = reader.readObject();

        return jSon;
    }

    @Override
    public String toString()
    {
        return "Movies"
                + "{"
                + "id=" + id
                + ", title=" + title
                + ", genre=" + genre
                + ", director=" + director
                + ", runtime=" + runtime
                + ", rating=" + rating
                + ", format=" + format
                + ", year=" + year
                + ", copies=" + copies
                + ", barcode=" + barcode
                + ", user_rating=" + user_rating
                + "}";

    }
}
