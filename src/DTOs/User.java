package DTOs;

//import java.sql.Timestamp;


/**
 *
 * @author conor
 */
public class User
{
    private int userId;
    private int movieWatchedId;
//    private Timestamp timestamp;

//    public User(int userId, int movieWatchedId, Timestamp timestamp)
//    {
//        this.userId = userId;
//        this.movieWatchedId = movieWatchedId;
//        this.timestamp = timestamp;
//    }

    /**
     *
     * @param userId
     * @param movieWatchedId
     */

    public User(int userId, int movieWatchedId)
    {
        this.userId = userId;
        this.movieWatchedId = movieWatchedId;
    }

    /**
     *
     */
    public User()
    {
    }

    /**
     *
     * @return
     */
    public int getUserId()
    {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public int getMovieWatchedId()
    {
        return movieWatchedId;
    }

    /**
     *
     * @param movieWatchedId
     */
    public void setMovieWatchedId(int movieWatchedId)
    {
        this.movieWatchedId = movieWatchedId;
    }

//    public Timestamp getTimestamp()
//    {
//        return timestamp;
//    }
//
//    public void setTimestamp(Timestamp timestamp)
//    {
//        this.timestamp = timestamp;
//    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 37 * hash + this.userId;
        hash = 37 * hash + this.movieWatchedId;
//        hash = 37 * hash + Objects.hashCode(this.timestamp);
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
        final User other = (User) obj;
        if (this.userId != other.userId)
        {
            return false;
        }
        if (this.movieWatchedId != other.movieWatchedId)
        {
            return false;
        }
//        if (!Objects.equals(this.timestamp, other.timestamp))
//        {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Users{" + "userId=" + userId + ", movieWatchedId=" + movieWatchedId + /*", timestamp=" + timestamp + */'}';
    }
    
}