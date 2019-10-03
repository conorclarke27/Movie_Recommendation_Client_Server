package Movies;

import DTOs.Movie;
import Exceptions.DaoException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 *
 * @author conor
 */
public class Client
{

    /**
     *
     * @param args
     * @throws DaoException
     */
    public static void main(String[] args) throws DaoException
    {
        Client client = new Client();
        client.start();
    }

    /**
     *
     * @throws DaoException
     */
    public void start() throws DaoException
    {
        try
        {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket, and open new socket

            Scanner sc = new Scanner(System.in);
            String protocol = commandBuild(sc);

            OutputStream os = socket.getOutputStream();

            PrintWriter socketWriter = new PrintWriter(os, true);// true=> auto flush buffers
            socketWriter.println(protocol);  // write command to socket

            Scanner socketReader = new Scanner(socket.getInputStream());

            JsonObject protocolJson = utilToJson(protocol);

            String command = protocolJson.getJsonString("command").getString();

            while (!command.equalsIgnoreCase("13"))
            {
                check(protocol, socketReader);

                protocol = commandBuild(sc);
                protocolJson = utilToJson(protocol);

                socketWriter.println(protocol);

                command = protocolJson.getJsonString("command").getString();
            }

            socketWriter.close();
            socketReader.close();
            socket.close();

        }
        catch (IOException e)
        {
            System.out.println("Client message: IOException: " + e);
        }
    }

    private static String menu(Scanner sc)
    {
        System.out.println("\n1) Display all movies"
                + "\n2) Search for movie by ID"
                + "\n3) Search for movie by title"
                + "\n4) Search for movie by director"
                + "\n5) Search for movie by Genre"
                + "\n6) Add a movie"
                + "\n7) Update movie by title"
                + "\n8) Delete by title"
                + "\n9) Watch Movie"
                + "\n10) Recommend random movie from users watched list"
                + "\n11) Recommend movie by genre of previous movie by movie ID"
                + "\n12) Recommend movie by director of previous movie by movie ID"
                + "\n13) EXIT");

        System.out.println("\nPlease enter Command:");
        String command = sc.nextLine();

        return command;
    }

    private static String commandBuild(Scanner sc)
    {
        String protocol = "";
        String input = "";
        String inputs = "";
        String command = menu(sc);

        switch (command)
        {

            case "1":
                System.out.println("\nPress any key to continue");
                sc.nextLine();

                break;

            case "2":
                System.out.println("\nPlease enter the ID:");
                input = sc.nextLine();

                break;

            case "3":
                System.out.println("\nPlease enter the title:");
                input = sc.nextLine();

                break;

            case "4":
                System.out.println("\nPlease enter the director:");
                input = sc.nextLine();

                break;

            case "5":
                System.out.println("\nPlease enter the genre:");
                input = sc.nextLine();

                break;

            case "6":
                System.out.println("\nPlease enter the title:");
                inputs = "{\"title\":\"" + sc.nextLine() + "\"";

                System.out.println("\nPlease enter the director:");
                inputs += ",\"director\": \"" + sc.nextLine() + "\"";

                System.out.println("\nPlease enter the genre:");
                inputs += ",\"genre\": \"" + sc.nextLine() + "\"}";

                input = inputs;

                break;

            case "7":
                System.out.println("\nPlease enter the title:");
                inputs = "{\"title\":\"" + sc.nextLine() + "\"";

                System.out.println("\nPlease enter the director:");
                inputs += ",\"director\": \"" + sc.nextLine() + "\"";

                System.out.println("\nPlease enter the genre:");
                inputs += ",\"genre\": \"" + sc.nextLine() + "\"}";

                input = inputs;

                break;

            case "8":
                System.out.println("\nPlease enter the title:");
                input = sc.nextLine();

                break;

            case "9":
                System.out.println("\nPlease enter user ID:");
                inputs = "{\"userID\":\"" + sc.nextLine() + "\"";

                System.out.println("\nPlease enter movie ID:");
                inputs += ",\"movieID\": \"" + sc.nextLine() + "\"}";

                input = inputs;
                break;

            case "10":
                System.out.println("\nPress any key to continue");
                sc.nextLine();

                break;

            case "11":
                System.out.println("\nPlease enter a user ID:");
                input = sc.nextLine();

                break;

            case "12":
                System.out.println("\nPlease enter a user ID:");
                input = sc.nextLine();

                break;

            case "13":
                System.out.println("\nPress any key to continue");
                sc.nextLine();

                break;

        }

        protocol = utilCheckStringInt(input, protocol, command);
        return protocol;
    }

    private static void check(String protocol, Scanner socketReader) throws DaoException
    {
        JsonObject protocolJson = utilToJson(protocol);
        String command = protocolJson.getJsonString("command").getString();
        JsonObject jSon = null;
        List<Movie> movies = new ArrayList<>();
        switch (command)
        {

            case "1":
                String displayMovies = socketReader.nextLine();
                jSon = utilToJson(displayMovies);
                movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                System.out.println(utilDisplayFormat(movies));

                break;

            case "2":
                String findMovieByID = socketReader.nextLine();
                if (!findMovieByID.contains("error"))
                {
                    jSon = utilToJson(findMovieByID);

                    movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                    if (!movies.isEmpty())
                    {
                        System.out.println(utilDisplayFormat(movies));
                    }
                    else
                    {
                        System.out.println("Movie with that ID not found");
                    }
                }
                else
                {
                    System.out.println(utilDisplayError(findMovieByID));
                }

                break;

            case "3":
                String findMovieByTitle = socketReader.nextLine();
                jSon = utilToJson(findMovieByTitle);
                movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                if (!movies.isEmpty())
                {
                    System.out.println(utilDisplayFormat(movies));
                }
                else
                {
                    System.out.println("Movie with that Title not found");
                }
                break;

            case "4":
                String findMovieByDirector = socketReader.nextLine();
                jSon = utilToJson(findMovieByDirector);
                movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                if (!movies.isEmpty())
                {
                    System.out.println(utilDisplayFormat(movies));
                }
                else
                {
                    System.out.println("Movie with that Director not found");
                }

                break;

            case "5":
                String findMovieByGenre = socketReader.nextLine();
                jSon = utilToJson(findMovieByGenre);
                movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                if (!movies.isEmpty())
                {
                    System.out.println(utilDisplayFormat(movies));
                }
                else
                {
                    System.out.println("Movie with that Genre not found");
                }

                break;

            case "6":
                String addMovie = socketReader.nextLine();
                System.out.println(utilDisplaySuccess(addMovie));

                break;

            case "7":
                String updateMovie = socketReader.nextLine();
                System.out.println(utilDisplaySuccess(updateMovie));

                break;

            case "8":
                String deleteMovie = socketReader.nextLine();
                System.out.println(utilDisplaySuccess(deleteMovie));

                break;

            case "9":
                String addMovieWatched = socketReader.nextLine();
                System.out.println(utilDisplaySuccess(addMovieWatched));

                break;

            case "10":
                String recommendRandomMovie = socketReader.nextLine();
                jSon = utilToJson(recommendRandomMovie);
                movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                if (!movies.isEmpty())
                {
                    System.out.println(utilDisplayFormat(movies));
                }
                else
                {
                    System.out.println("Movies were not found");
                }

                break;

            case "11":
                String recommendMoviesByGenreOfWatchedMovie = socketReader.nextLine();
                if (!recommendMoviesByGenreOfWatchedMovie.contains("error"))
                {
                    jSon = utilToJson(recommendMoviesByGenreOfWatchedMovie);
                    movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                    if (!movies.isEmpty())
                    {
                        System.out.println(utilDisplayFormat(movies));
                    }
                    else
                    {
                        System.out.println("Movies were not found");
                    }
                }
                else
                {
                    System.out.println(utilDisplayError(recommendMoviesByGenreOfWatchedMovie));
                }

                break;

            case "12":
                String recommendMovieByDirectorOfWatchedMovie = socketReader.nextLine();
                if (!recommendMovieByDirectorOfWatchedMovie.contains("error"))
                {
                    recommendMovieByDirectorOfWatchedMovie = recommendMovieByDirectorOfWatchedMovie.replace("},", "},\n");
                    jSon = utilToJson(recommendMovieByDirectorOfWatchedMovie);
                    movies = utilChangeJsonArray(jSon.getJsonArray("movieList"));
                    if (!movies.isEmpty())
                    {
                        System.out.println(utilDisplayFormat(movies));
                    }
                    else
                    {
                        System.out.println("Movies were not found");
                    }
                }
                else
                {
                    System.out.println(utilDisplayError(recommendMovieByDirectorOfWatchedMovie));
                }

                break;

            default:
                System.out.println("Sorry input incorrect");

                break;

        }

    }

    private static JsonObject utilToJson(String data)
    {
        StringReader r = new StringReader(data);
        JsonReader reader = Json.createReader(r);
        JsonObject jSon = reader.readObject();

        return jSon;
    }

    private static String utilCheckStringInt(String input, String protocol, String command)
    {
        if (command.equalsIgnoreCase("6") || command.equalsIgnoreCase("7") || command.equalsIgnoreCase("9"))
        {
            protocol = "{\"command\":\"" + command + "\",\"input\":[" + input + "]}";
        }
        else
        {
            protocol = "{\"command\":\"" + command + "\",\"input\":\"" + input + "\"}";
        }
        return protocol;
    }

    private static String utilDisplayFormat(List<Movie> movies)
    {
        String toReturn = "";

        for (Movie movie : movies)
        {
            toReturn += "\n\nMovie id: " + movie.toJson().getInt("id") + "\nMovie Title: " + movie.toJson().getString("title") + "\nMovie Director(s): " + movie.toJson().getJsonArray("director") + "\nMovie Genre(s): " + movie.toJson().getJsonArray("genre");

        }
        return toReturn.replace("\"", "").replace("[", "").replace("]", "");
    }

    private static List<Movie> utilChangeJsonArray(JsonArray movies)
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

    private static String utilDisplayError(String error)
    {
        JsonObject protocolJson = utilToJson(error);
        String errorMessage = "\n" + protocolJson.getJsonString("error").getString();

        return errorMessage;

    }

    private static String utilDisplaySuccess(String success)
    {
        JsonObject protocolJson = utilToJson(success);
        String successMessage = "\n" + protocolJson.getJsonString("message").getString();

        return successMessage;

    }

}
