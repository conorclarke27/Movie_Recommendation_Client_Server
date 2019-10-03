package DAOs;

import DTOs.Movie;
import Exceptions.DaoException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author conor
 */
public class MySqlMoviesDao extends MySqlDao implements MoviesDaoInterface
{

    //Search by id

    /**
     *
     * @param id
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> findMovieByID(int id) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next())
            {
                int i = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");

                movie = new Movie(i, title, genre, director, runtime, rating, format, year, copies, barcode, user_rating);
                movies.add(movie);
            }

        }
        catch (SQLException e)
        {
            throw new DaoException("findMovieByID()" + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByID() " + e.getMessage());
            }
        }
        return movies;
    }

    //Gets all Movies

    /**
     *
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> findAllMovies() throws DaoException
    {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM movies";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");

                Movie m = new Movie(id, title, genre, director, runtime, rating, format, year, copies, barcode, user_rating);

                movies.add(m);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findAllMovies() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findAllMovies() " + e.getMessage());
            }
        }
        return movies;
    }

    //Search by title

    /**
     *
     * @param title
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> findMovieByTitle(String title) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM movies WHERE title LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + title + "%");

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String t = rs.getString("title");
                String genre = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");

                movie = new Movie(id, t, genre, director, runtime, rating, format, year, copies, barcode, user_rating);
                movies.add(movie);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMovieByTitle()" + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByTitle() " + e.getMessage());
            }
        }
        return movies;
    }

    //Search by tgenre

    /**
     *
     * @param genre
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> findMovieByGenre(String genre) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();

        try
        {
            con = this.getConnection();

            String[] genres = genre.split(",");
            String condition = "";

            for (int i = 1; i <= genres.length; i++)
            {
                condition += "genre LIKE ? AND ";
            }
            condition = condition.replaceAll(" AND $", "");

            String query = "SELECT * FROM movies WHERE " + condition;

            ps = con.prepareStatement(query);

            for (int i = 1; i <= genres.length; i++)
            {
                ps.setString(i, "%" + genres[i - 1] + "%");
            }

            rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String g = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");
                movie = new Movie(id, title, g, director, runtime, rating, format, year, copies, barcode, user_rating);
                movies.add(movie);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMovieByTitle()" + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByTitle() " + e.getMessage());
            }
        }
        return movies;
    }

    //Search by director

    /**
     *
     * @param director
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> findMovieByDirector(String director) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        try
        {
            con = this.getConnection();

            String[] directors = director.split(",");
            String condition = "";

            for (int i = 0; i < directors.length; i++)
            {
                condition += "director LIKE ? OR ";
            }
            condition = condition.replaceAll(" OR $", "");

            String query = "SELECT * FROM movies WHERE " + condition;

            ps = con.prepareStatement(query);

            for (int i = 1; i <= directors.length; i++)
            {
                ps.setString(i, "%" + directors[i - 1] + "%");
            }

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String dir = rs.getString("director");
                String runtime = rs.getString("runtime");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");

                movie = new Movie(id, title, genre, dir, runtime, rating, format, year, copies, barcode, user_rating);
                movies.add(movie);

            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findMovieByDirector() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector() " + e.getMessage());
            }
        }
        return movies;
    }

    //Add a new Movie

    /**
     *
     * @param args
     * @return
     * @throws DaoException
     */
    @Override
    public JsonObject addNewMovies(JsonArray args) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;
        String msgSuccess = "";
        String msg = "";
        String end = "";

        String title = args.get(0).asJsonObject().getString("title");
        String genre = args.get(0).asJsonObject().getString("genre");
        String director = args.get(0).asJsonObject().getString("director");

        Movie m = new Movie(title, genre, director);

        try
        {

            if (!m.getTitle().isEmpty() && !m.getGenre().isEmpty() && !m.getDirector().isEmpty())
            {

                con = this.getConnection();

                String query = "INSERT INTO `movies` "
                        + "(title"
                        + ", genre"
                        + ", director)"
                        + "VALUES"
                        + "(?, ?, ?)";
                ps = con.prepareStatement(query);
                ps.setString(1, m.getTitle());
                ps.setString(2, m.getGenre());
                ps.setString(3, m.getDirector());

                success = ps.executeUpdate() == 1;
            }
            if (success)
            {
                msgSuccess = "Successfully Inserted " + m.getTitle();
                msg = "Success";
            }
            else
            {
                msgSuccess = "Title: " + m.getTitle() + " was not found";
                msg = "Failure";
            }
            end = "{\"success\":\"" + msg + "\"" + ",\"message\":\"" + msgSuccess + "\"}";
        }
        catch (SQLException e)
        {
            throw new DaoException("addNewMovies()" + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findMovieByTitle() " + e.getMessage());
            }
        }

        JsonObject jSon = utilToJson(end);

        return jSon;
    }

    //Delete Movie by title

    /**
     *
     * @param title
     * @return
     * @throws DaoException
     */
    @Override
    public JsonObject deleteMovieByTitle(String title) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;
        String msgSuccess = "";
        String msg = "";
        String end = "";
        try
        {

            if (!title.isEmpty())
            {
                con = this.getConnection();

                String query = "DELETE FROM movies WHERE TITLE = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, title);

                success = ps.executeUpdate() == 1;
            }

            if (success)
            {
                msgSuccess = "Successfully Deleted " + title;
                msg = "Success";
            }
            else
            {
                msgSuccess = "Title: " + title + " was not found";
                msg = "Failure";
            }
            end = "{\"success\":\"" + msg + "\""
                    + ",\"message\":\"" + msgSuccess + "\"}";
        }
        catch (SQLException e)
        {
            throw new DaoException("deleteMovie()" + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("deleteMovie() " + e.getMessage());
            }
        }

        JsonObject jSon = utilToJson(end);

        return jSon;
    }

    //Update Movie

    /**
     *
     * @param args
     * @return
     * @throws DaoException
     */
    @Override
    public JsonObject updateMovies(JsonArray args) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;
        String msgSuccess = "";
        String msg = "";
        String end = "";

        String title = args.get(0).asJsonObject().getString("title");
        String genre = args.get(0).asJsonObject().getString("genre");
        String director = args.get(0).asJsonObject().getString("director");

        Movie m = new Movie(title, genre, director);
        try
        {

            if (!m.getTitle().isEmpty() && !m.getGenre().isEmpty() && !m.getDirector().isEmpty())
            {
                con = this.getConnection();

                String query = "UPDATE movies SET genre=?, director=? WHERE title=?";

                ps = con.prepareStatement(query);

                System.out.println(ps);

                ps.setString(1, m.getGenre());
                ps.setString(2, m.getDirector());
                ps.setString(3, m.getTitle());

                success = ps.executeUpdate() == 1;

            }

            if (success)
            {
                msgSuccess = "Successfully Updated " + m.getTitle();
                msg = "Success";
            }
            else
            {
                msgSuccess = "Title: " + m.getTitle() + " was not found";
                msg = "Failure";
            }
            end = "{\"success\":\"" + msg + "\"" + ",\"message\":\"" + msgSuccess + "\"}";
        }

        catch (SQLException e)
        {
            throw new DaoException("updateMovies()" + e.getMessage());
        }

        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("updateMovies() " + e.getMessage());
            }
        }

        JsonObject jSon = utilToJson(end);

        return jSon;
    }

    /**
     *
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> recommendRandomMovie() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        Movie m = new Movie();

        try
        {
            con = this.getConnection();
            Random rand = new Random();

            String query = "SELECT * FROM movies";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String g = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");
                movie = new Movie(id, title, g, director, runtime, rating, format, year, copies, barcode, user_rating);
                movies.add(movie);
            }
            
            int randomIndex = rand.nextInt(movies.size());
            m = movies.get(randomIndex);
            movies.clear();
            movies.add(m);
            
        }
        catch (SQLException e)
        {
            throw new DaoException("recommendRandomMovie()" + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("recommendRandomMovie() " + e.getMessage());
            }
        }
        
        return movies;
    }

    /**
     *
     * @param IMovieDao
     * @param IUserDao
     * @param userid
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> recommendMoviesByGenreOfWatchedMovie(MoviesDaoInterface IMovieDao, UsersWatchedInterface IUserDao, int userid) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        List<Movie> userMovies = new ArrayList<>();
        Movie movie = new Movie();

        userMovies = IUserDao.findMoviesUsersHaveWatched(userid, IMovieDao);
        String genre = "";
        for (Movie mov : userMovies)
        {
            genre += mov.getGenre();
        }

        try
        {
            con = this.getConnection();

            String[] genres = genre.split(",");
            String condition = "";

            for (int i = 1; i <= genres.length; i++)
            {
                condition += "genre LIKE ? OR ";
            }
            condition = condition.replaceAll(" OR $", "");

            String query = "SELECT * FROM movies WHERE " + condition + "LIMIT 5";

            ps = con.prepareStatement(query);

            for (int i = 1; i <= genres.length; i++)
            {
                ps.setString(i, "%" + genres[i - 1] + "%");
            }

            rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String g = rs.getString("genre");
                String director = rs.getString("director");
                String runtime = rs.getString("runtime");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");
                movie = new Movie(id, title, g, director, runtime, rating, format, year, copies, barcode, user_rating);
                movies.add(movie);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("recommendRandomMovieByGenre()" + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("recommendRandomMovieByGenre() " + e.getMessage());
            }
        }
        return movies;
    }

    /**
     *
     * @param IMovieDao
     * @param IUserDao
     * @param userid
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> recommendMoviesByDirectorOfWatchedMovie(MoviesDaoInterface IMovieDao, UsersWatchedInterface IUserDao, int userid) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        List<Movie> userMovies = new ArrayList<>();
        Movie movie = new Movie();

        userMovies = IUserDao.findMoviesUsersHaveWatched(userid, IMovieDao);
        String director = "";
        for (Movie mov : userMovies)
        {
            director += mov.getDirector();
        }

        try
        {
            con = this.getConnection();

            String[] directors = director.split(",");
            String condition = "";

            for (int i = 0; i < directors.length; i++)
            {
                condition += "director LIKE ? OR ";
            }
            condition = condition.replaceAll(" OR $", "");

            String query = "SELECT * FROM movies WHERE " + condition + "LIMIT 5";

            ps = con.prepareStatement(query);

            for (int i = 1; i <= directors.length; i++)
            {
                ps.setString(i, "%" + directors[i - 1] + "%");
            }

            rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String g = rs.getString("genre");
                String d = rs.getString("director");
                String runtime = rs.getString("runtime");
                String rating = rs.getString("rating");
                String format = rs.getString("format");
                String year = rs.getString("year");
                int copies = rs.getInt("copies");
                String barcode = rs.getString("barcode");
                String user_rating = rs.getString("user_rating");
                movie = new Movie(id, title, g, d, runtime, rating, format, year, copies, barcode, user_rating);
                movies.add(movie);
            }

        }
        catch (SQLException e)
        {
            throw new DaoException("recommendRandomMovieByDirector()" + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("recommendRandomMovieByDirector() " + e.getMessage());
            }
        }
        return movies;
    }

    private JsonObject utilToJson(String data)
    {
        StringReader r = new StringReader(data);
        JsonReader reader = Json.createReader(r);
        JsonObject jSon = reader.readObject();

        return jSon;
    }
}
