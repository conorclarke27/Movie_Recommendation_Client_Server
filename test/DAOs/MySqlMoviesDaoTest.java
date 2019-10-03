/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.Movie;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author conor
 */
public class MySqlMoviesDaoTest
{
    
    /**
     *
     */
    public MySqlMoviesDaoTest()
    {
    }

    /**
     * Test of findMovieByID method, of class MySqlMoviesDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindMovieByID() throws Exception
    {
        System.out.println("findMovieByID");
        int id = 15;
        MySqlMoviesDao instance = new MySqlMoviesDao();
        Movie m = new Movie(15, "iron man 2", "Disney, marvel,Action, Adventure, Sci-Fi", "Jon Favreau", "124 min", "PG-13", "DVD", "2010", 3, "null", "null");
        List<Movie> expResult = new ArrayList<>();
        expResult.add(m);
        List<Movie> result = instance.findMovieByID(id);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of findMovieByTitle method, of class MySqlMoviesDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindMovieByTitle() throws Exception
    {
        System.out.println("findMovieByTitle");
        String title = "iron man 2";
        MySqlMoviesDao instance = new MySqlMoviesDao();
        Movie m = new Movie(15, "iron man 2", "Disney, marvel,Action, Adventure, Sci-Fi", "Jon Favreau", "124 min", "PG-13", "DVD", "2010", 3, "null", "null");
        List<Movie> expResult = new ArrayList<>();
        expResult.add(m);
        List<Movie> result = new ArrayList<>();
        result = instance.findMovieByTitle(title);
        assertEquals(expResult, result);
    }
    
//    /**
//     * Test of findMovieByGenre method, of class MySqlMoviesDao.
//     */

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindMovieByGenre() throws Exception
    {
        System.out.println("findMovieByGenre");
        String genre = "Marvel, Action, Adventure, Sci-Fi";
        MySqlMoviesDao instance = new MySqlMoviesDao();
        Movie m = new Movie(14, "iron man", "Marvel, Action, Adventure, Sci-Fi", "Jon Favreau", "126 min", "PG-13", "DVD", "2008", 3, "null", "null");
        List<Movie> expResult = new ArrayList<>();
        expResult.add(m);
        List<Movie> result = instance.findMovieByGenre(genre); 
        assertEquals(expResult, result);
    }
//
//    /**
//     * Test of findMovieByDirector method, of class MySqlMoviesDao.
//     */

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindMovieByDirector() throws Exception
    {
        System.out.println("findMovieByDirector");
        String director = "Gavin Hood";
        MySqlMoviesDao instance = new MySqlMoviesDao();
        Movie m = new Movie(24, "x men origins wolverine", "Action, Adventure, Sci-Fi", "Gavin Hood", "107 min", "PG-13", "DVD", "2009", 1, "null", "null");
        List<Movie> expResult = new ArrayList<>();
        expResult.add(m);
        List<Movie> result = instance.findMovieByDirector(director);
        assertEquals(expResult, result);
    }


    
}
