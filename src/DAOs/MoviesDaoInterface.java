package DAOs;

import DTOs.Movie;
import Exceptions.DaoException;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author conor
 */
public interface MoviesDaoInterface
{

    /**
     *
     * @param id
     * @return
     * @throws DaoException
     */
    public List<Movie> findMovieByID(int id) throws DaoException;

    /**
     *
     * @return
     * @throws DaoException
     */
    public List<Movie> findAllMovies() throws DaoException;

    /**
     *
     * @param title
     * @return
     * @throws DaoException
     */
    public List<Movie> findMovieByTitle(String title) throws DaoException;

    /**
     *
     * @param genre
     * @return
     * @throws DaoException
     */
    public List<Movie> findMovieByGenre(String genre) throws DaoException;

    /**
     *
     * @param director
     * @return
     * @throws DaoException
     */
    public List<Movie> findMovieByDirector(String director) throws DaoException;

    /**
     *
     * @param args
     * @return
     * @throws DaoException
     */
    public JsonObject addNewMovies(JsonArray args) throws DaoException;

    /**
     *
     * @param title
     * @return
     * @throws DaoException
     */
    public JsonObject deleteMovieByTitle(String title) throws DaoException;

    /**
     *
     * @param args
     * @return
     * @throws DaoException
     */
    public JsonObject updateMovies(JsonArray args) throws DaoException;

    /**
     *
     * @return
     * @throws DaoException
     */
    public List<Movie> recommendRandomMovie() throws DaoException;

    /**
     *
     * @param IMovieDao
     * @param IUserDao
     * @param userid
     * @return
     * @throws DaoException
     */
    public List<Movie> recommendMoviesByGenreOfWatchedMovie(MoviesDaoInterface IMovieDao, UsersWatchedInterface IUserDao, int userid) throws DaoException;

    /**
     *
     * @param IMovieDao
     * @param IUserDao
     * @param userid
     * @return
     * @throws DaoException
     */
    public List<Movie> recommendMoviesByDirectorOfWatchedMovie(MoviesDaoInterface IMovieDao, UsersWatchedInterface IUserDao, int userid) throws DaoException;
}
