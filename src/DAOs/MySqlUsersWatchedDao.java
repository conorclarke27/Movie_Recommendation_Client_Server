package DAOs;

import DTOs.Movie;
import DTOs.User;
import Exceptions.DaoException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/*
 *
 * @author conor
 */

/**
 *
 * @author conor
 */

public class MySqlUsersWatchedDao extends MySqlDao implements UsersWatchedInterface
{

    //get user id

    /**
     *
     * @param user
     * @return
     * @throws DaoException
     */
    @Override
    public List<User> findUserById(int user) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        User u = new User();

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM movieswatched WHERE userId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, user);

            rs = ps.executeQuery();

            if (rs.next())
            {
                int id = rs.getInt("userId");
                int watchedId = rs.getInt("movieid");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                u = new User(id, watchedId/*, timestamp*/);
                users.add(u);
            }

        } catch (SQLException e)
        {
            throw new DaoException("findUserById()" + e.getMessage());
        } finally
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
            } catch (SQLException e)
            {
                throw new DaoException("findUserById() " + e.getMessage());
            }
        }
        return users;
    }

    /**
     *
     * @param user
     * @param IMovieDao
     * @return
     * @throws DaoException
     */
    @Override
    public List<Movie> findMoviesUsersHaveWatched(int user, MoviesDaoInterface IMovieDao) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        Movie m = new Movie();
        User u = new User();

        // get movie id then return movies 
        try
        {
            con = this.getConnection();

            String query = "SELECT movieId FROM movieswatched WHERE userId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, user);

            rs = ps.executeQuery();

            if (rs.next())
            {
//                int id = rs.getInt("userId");
                int watchedId = rs.getInt("movieid");
//                Timestamp timestamp = rs.getTimestamp("timestamp");

//                u = new User(id, watchedId/*, timestamp*/);
//                users.add(u);
                movies.addAll(IMovieDao.findMovieByID(watchedId));
            }

        } catch (SQLException e)
        {
            throw new DaoException("findUserById()" + e.getMessage());
        } finally
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
            } catch (SQLException e)
            {
                throw new DaoException("findUserById() " + e.getMessage());
            }
        }
        return movies;
    }

    /**
     *
     * @param movieId
     * @return
     * @throws DaoException
     */
    @Override
    public List<User> findUsersByMovieId(int movieId) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        User u = new User();

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM movieswatched WHERE movieid = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, movieId);

            rs = ps.executeQuery();

            if (rs.next())
            {
                int id = rs.getInt("userId");
                int watchedId = rs.getInt("movieid");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                u = new User(id, watchedId/*, timestamp*/);
                users.add(u);
            }

        } catch (SQLException e)
        {
            throw new DaoException("findUsersByMovieId()" + e.getMessage());
        } finally
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
            } catch (SQLException e)
            {
                throw new DaoException("findUsersByMovieId() " + e.getMessage());
            }
        }
        return users;
    }

    /**
     *
     * @param args
     * @return
     * @throws DaoException
     */
    @Override
    public JsonObject addWatchedMovie(JsonArray args) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;
        String msgSuccess = "";
        String msg = "";
        String end = "";
        User user = new User();

        int userid = Integer.parseInt(args.get(0).asJsonObject().getString("userID"));
        int movieid = Integer.parseInt(args.get(0).asJsonObject().getString("movieID"));

        user = new User(userid, movieid);

        try
        {
            con = this.getConnection();

            String query = "INSERT INTO `movieswatched` (userid, movieid) VALUES (?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, userid);
            ps.setInt(2, movieid);

            success = ps.executeUpdate() == 1;

            if (success)
            {

                msgSuccess = "Successfully Inserted " + user.getMovieWatchedId() + " for User: " + user.getUserId();
                msg = "Success";
            }
            else
            {
                msgSuccess = "User: " + user.getUserId() + " was not found";
                msg = "Failure";
            }
            end = "{\"success\":\"" + msg + "\""
                    + ",\"message\":\"" + msgSuccess + "\"}";

        } catch (SQLException e)
        {
            throw new DaoException("findUsersByMovieId()" + e.getMessage());
        } finally
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
            } catch (SQLException e)
            {
                throw new DaoException("findUsersByMovieId() " + e.getMessage());
            }
        }
        StringReader r = new StringReader(end);
        JsonReader reader = Json.createReader(r);
        JsonObject jSon = reader.readObject();

        return jSon;
    }

}
