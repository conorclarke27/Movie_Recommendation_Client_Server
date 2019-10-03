package Movies;

import DAOs.LocalCache;
import DAOs.MoviesDaoInterface;
import DAOs.MySqlMoviesDao;
import DAOs.MySqlUsersWatchedDao;
import DAOs.UsersWatchedInterface;
import DTOs.Movie;
import DTOs.ServerLog;
import Exceptions.DaoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 *
 * @author conor
 */
public class Server
{

    static LocalCache localCache = new LocalCache();

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        Server server = new Server();
        server.start();
    }

    /**
     *
     */
    public void start()
    {
        try
        {
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true)    // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection, 
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the client,
                t.start();                                                  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        }
        catch (IOException e)
        {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    /**
     *
     */
    public class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
    {

        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;

        /**
         *
         * @param clientSocket
         * @param clientNumber
         */
        public ClientHandler(Socket clientSocket, int clientNumber)
        {
            try
            {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing 

            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            String message;
            MoviesDaoInterface IMovieDao = new MySqlMoviesDao();
            UsersWatchedInterface IUserDao = new MySqlUsersWatchedDao();

            try
            {

                while ((message = socketReader.readLine()) != null)
                {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    check(message, socketWriter, IMovieDao, IUserDao, socketReader);

                    ServerLog serverLog = new ServerLog(message, clientNumber);
                }
                socket.close();

            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            catch (DaoException ex)
            {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }

        /**
         *
         * @param protocol
         * @param socketWriter
         * @param IMovieDao
         * @param IUserDao
         * @param socketReader
         * @throws DaoException
         * @throws IOException
         */
        public void check(String protocol, PrintWriter socketWriter, MoviesDaoInterface IMovieDao, UsersWatchedInterface IUserDao, BufferedReader socketReader) throws DaoException, IOException
        {

            JsonObject protocolJson = utilToJson(protocol);
            String command = protocolJson.getJsonString("command").getString();
            List<Movie> movies;
            JsonObject jSon;
            int number = 0;
            String input = "";

            if (localCache.getValues(protocol) != null)
            {
                socketWriter.println(builderToReturn(localCache.getValues(protocol)));
            }
            else
            {
                try
                {
                    switch (command)
                    {

                        case "1":
                            socketWriter.println(displayAllMovies(IMovieDao));
                            jSon = utilToJson(displayAllMovies(IMovieDao));
                            movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                            localCache.putInCache(protocol, movies);

                            break;

                        case "2":
                            input = protocolJson.getString("input");
                            number = parseString(input);
                            socketWriter.println(findMovieByID(number, IMovieDao));
                            localCache.putInCache(protocol, IMovieDao.findMovieByID(number));
                            break;

                        case "3":
                            input = protocolJson.getString("input");
                            socketWriter.println(findByTitle(input, IMovieDao));
                            localCache.putInCache(protocol, IMovieDao.findMovieByTitle(input));
                            break;

                        case "4":
                            input = protocolJson.getString("input");
                            socketWriter.println(findByDirector(input, IMovieDao));
                            localCache.putInCache(protocol, IMovieDao.findMovieByDirector(input));
                            break;

                        case "5":
                            input = protocolJson.getString("input");
                            socketWriter.println(findByGenre(input, IMovieDao));
                            localCache.putInCache(protocol, IMovieDao.findMovieByGenre(input));
                            break;

                        case "6":
                            JsonArray jSonInputAdd = protocolJson.getJsonArray("input");
                            socketWriter.println(addMovie(jSonInputAdd, IMovieDao));
                            localCache.clearCache();
                            break;

                        case "7":
                            JsonArray jSonInputUpdate = protocolJson.getJsonArray("input");
                            socketWriter.println(updateMovie(jSonInputUpdate, IMovieDao));
                            localCache.clearCache();
                            break;

                        case "8":
                            String jSonInputDelete = protocolJson.getString("input");
                            socketWriter.println(deleteMovie(jSonInputDelete, IMovieDao));
                            localCache.clearCache();
                            break;

                        case "9":
                            JsonArray jSonInputAddWatched = protocolJson.getJsonArray("input");
                            socketWriter.println(addUserWatchedMovie(jSonInputAddWatched, IMovieDao, IUserDao));
                            break;

                        case "10":
                            socketWriter.println(recommendRandomMovie(IMovieDao));
                            break;

                        case "11":
                            input = protocolJson.getString("input");
                            number = parseString(input);
                            socketWriter.println(recommendMoviesByGenreOfWatchedMovie(IMovieDao, IUserDao, number));
                            break;

                        case "12":
                            input = protocolJson.getString("input");
                            number = parseString(input);
                            socketWriter.println(recommendMoviesByDirectorOfWatchedMovie(IMovieDao, IUserDao, number));
                            break;

                    }

                }
                catch (DaoException e)
                {
                    e.printStackTrace();
                }
                catch (NumberFormatException e)
                {
                    socketWriter.println(errorMessage());
                }
                catch (NullPointerException e)
                {
                    socketWriter.println(errorMessage());
                }
            }
        }

        /**
         *
         * @param IMovieDao
         */
        private String displayAllMovies(MoviesDaoInterface IMovieDao) throws DaoException
        {
            List<Movie> movies = IMovieDao.findAllMovies();

            int success = 0;
            String toReturn = "";

            for (Movie movie : movies)
            {
                if (movies.size() > 0)
                {
                    success = 1;
                    toReturn += movie.toJson() + ",";
                }

            }
            toReturn = toReturn.replaceAll(",$", "");
            toReturn += "]}";

            return "{\"success\": " + success + ",\"movieList\":[" + toReturn;
        }

        private String findByTitle(String title, MoviesDaoInterface IMovieDao) throws DaoException
        {
            List<Movie> movies = IMovieDao.findMovieByTitle(title);
            String toReturn = builderToReturn(movies, title);

            return toReturn;
        }

        private String findByDirector(String director, MoviesDaoInterface IMovieDao) throws DaoException
        {
            List<Movie> movies = IMovieDao.findMovieByDirector(director);

            String toReturn = builderToReturn(movies, director);

            return toReturn;

        }

        private String findMovieByID(int id, MoviesDaoInterface IMovieDao) throws DaoException
        {
            List<Movie> movies = IMovieDao.findMovieByID(id);
            int success = 0;
            String toReturn = "";

            if (id >= 0 && !movies.isEmpty())
            {
                for (Movie movie : movies)
                {

                    success = 1;
                    toReturn += movie.toJson() + ",";

                }
                toReturn = toReturn.replaceAll(",$", "");

            }
            toReturn += "]}";
            return "{\"success\": " + success + ",\"movieList\":[" + toReturn;
        }

        private String findByGenre(String genre, MoviesDaoInterface IMovieDao) throws DaoException
        {
            List<Movie> movies = IMovieDao.findMovieByGenre(genre);
            String toReturn = builderToReturn(movies, genre);

            return toReturn;
        }

        private JsonObject addMovie(JsonArray mInsert, MoviesDaoInterface IMovieDao) throws DaoException
        {
            return IMovieDao.addNewMovies(mInsert);
        }

        /**
         *
         * @param mUpdate
         * @param IMovieDao
         * @return
         * @throws DaoException
         */
        public JsonObject updateMovie(JsonArray mUpdate, MoviesDaoInterface IMovieDao) throws DaoException
        {
            return IMovieDao.updateMovies(mUpdate);
        }

        /**
         *
         * @param title
         * @param IMovieDao
         * @return
         * @throws DaoException
         */
        public JsonObject deleteMovie(String title, MoviesDaoInterface IMovieDao) throws DaoException
        {
            return IMovieDao.deleteMovieByTitle(title);
        }

        /**
         *
         * @param uAddWatched
         * @param IMovieDao
         * @param IUserDao
         * @return
         * @throws DaoException
         */
        public JsonObject addUserWatchedMovie(JsonArray uAddWatched, MoviesDaoInterface IMovieDao, UsersWatchedInterface IUserDao) throws DaoException
        {
            int movieid = Integer.parseInt(uAddWatched.get(0).asJsonObject().getString("movieID"));
            List<Movie> movies = IMovieDao.findMovieByID(movieid);

            return IUserDao.addWatchedMovie(uAddWatched);

        }

        /**
         *
         * @param IMovieDao
         * @return
         * @throws DaoException
         */
        public String recommendRandomMovie(MoviesDaoInterface IMovieDao) throws DaoException
        {
            List<Movie> movies = IMovieDao.recommendRandomMovie();

            int success = 0;
            String toReturn = "";

            for (Movie movie : movies)
            {
                if (movies.size() > 0)
                {
                    success = 1;
                    toReturn += movie.toJson() + ",";
                }
            }

            toReturn = toReturn.replaceAll(",$", "");
            toReturn += "]}";

            return "{\"success\": " + success + ",\"movieList\":[" + toReturn;
        }

        /**
         *
         * @param IMovieDao
         * @param IUserDao
         * @param userid
         * @return
         * @throws DaoException
         */
        public String recommendMoviesByGenreOfWatchedMovie(MoviesDaoInterface IMovieDao, UsersWatchedInterface IUserDao, int userid) throws DaoException
        {
            List<Movie> movies = IMovieDao.recommendMoviesByGenreOfWatchedMovie(IMovieDao, IUserDao, userid);

            int success = 0;
            String toReturn = "";

            for (Movie movie : movies)
            {
                if (movies.size() > 0)
                {
                    success = 1;
                    toReturn += movie.toJson() + ",";
                }

            }

            toReturn = toReturn.replaceAll(",$", "");
            toReturn += "]}";

            return "{\"success\": " + success + ",\"movieList\":[" + toReturn;
        }

        /**
         *
         * @param IMovieDao
         * @param IUserDao
         * @param userid
         * @return
         * @throws DaoException
         */
        public String recommendMoviesByDirectorOfWatchedMovie(MoviesDaoInterface IMovieDao, UsersWatchedInterface IUserDao, int userid) throws DaoException
        {
            List<Movie> movies = IMovieDao.recommendMoviesByDirectorOfWatchedMovie(IMovieDao, IUserDao, userid);

            int success = 0;
            String toReturn = "";

            for (Movie movie : movies)
            {
                if (movies.size() > 0)
                {
                    success = 1;
                    toReturn += movie.toJson() + ",";
                }

            }

            toReturn = toReturn.replaceAll(",$", "");
            toReturn += "]}";

            return "{\"success\": " + success + ",\"movieList\":[" + toReturn;
        }

        /**
         *
         * @param input
         * @return
         */
        public int parseString(String input)
        {
            int number = Integer.parseInt(input);
            return number;
        }

        /**
         *
         * @param movies
         * @param data
         * @return
         */
        public String builderToReturn(List<Movie> movies, String data)
        {
            int success = 0;
            String toReturn = "";
            if (!"".equals(data) && !movies.isEmpty())
            {
                for (Movie movie : movies)
                {
                    success = 1;
                    toReturn += movie.toJson() + ",";
                }
                toReturn = toReturn.replaceAll(",$", "");

            }
            toReturn += "]}";

            return "{\"success\": " + success + ",\"movieList\":[" + toReturn;
        }

        /**
         *
         * @param movies
         * @return
         */
        public String builderToReturn(List<Movie> movies)
        {
            int success = 0;
            String toReturn = "";
            if (!movies.isEmpty())
            {
                for (Movie movie : movies)
                {
                    success = 1;
                    toReturn += movie.toJson() + ",";
                }
                toReturn = toReturn.replaceAll(",$", "");

            }
            toReturn += "]}";

            return "{\"success\": " + success + ",\"movieList\":[" + toReturn;
        }

        private List<Movie> utilChangeJsonArray(JsonArray movies)
        {
            List<Movie> toReturn = new ArrayList<>();
            Movie movie;

            for (JsonValue m : movies)
            {
                movie = new Movie(m.asJsonObject());
                toReturn.add(movie);
            }

            return toReturn;
        }

        /**
         *
         * @param data
         * @return
         */
        public JsonObject utilToJson(String data)
        {
            StringReader r = new StringReader(data);
            JsonReader reader = Json.createReader(r);
            JsonObject jSon = reader.readObject();

            return jSon;
        }

        public JsonObject errorMessage()
        {

            String data = "{\"error\":\"Error: Please check your input and try again\"}";
            StringReader r = new StringReader(data);
            JsonReader reader = Json.createReader(r);
            JsonObject jSon = reader.readObject();

            return jSon;
        }
    }

}
