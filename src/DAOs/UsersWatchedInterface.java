package DAOs;

import DTOs.Movie;
import DTOs.User;
import Exceptions.DaoException;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author conor
 */
public interface UsersWatchedInterface
{

    /**
     *
     * @param id
     * @return
     * @throws DaoException
     */
    public List<User> findUserById(int id) throws DaoException;
    
    /**
     *
     * @param movieId
     * @return
     * @throws DaoException
     */
    public List<User> findUsersByMovieId(int movieId) throws DaoException;
    
    /**
     *
     * @param user
     * @param IMovieDao
     * @return
     * @throws DaoException
     */
    public List<Movie> findMoviesUsersHaveWatched(int user, MoviesDaoInterface IMovieDao) throws DaoException;
    
    /**
     *
     * @param args
     * @return
     * @throws DaoException
     */
    public JsonObject addWatchedMovie(JsonArray args) throws DaoException;
}