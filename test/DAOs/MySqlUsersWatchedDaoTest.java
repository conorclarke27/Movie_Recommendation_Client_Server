/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DTOs.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author conor
 */
public class MySqlUsersWatchedDaoTest
{
    
    /**
     *
     */
    public MySqlUsersWatchedDaoTest()
    {
    }

    /**
     * Test of findUserById method, of class MySqlUsersWatchedDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindUserById() throws Exception
    {
        System.out.println("findUserById");
        int user = 4;
        MySqlUsersWatchedDao instance = new MySqlUsersWatchedDao();
        User u = new User(4, 123);
        List<User> expResult = new ArrayList<>();
        expResult.add(u);
        List<User> result = instance.findUserById(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of findUsersByMovieId method, of class MySqlUsersWatchedDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindUsersByMovieId() throws Exception
    {
        System.out.println("findUsersByMovieId");
        int movieId = 762;
        MySqlUsersWatchedDao instance = new MySqlUsersWatchedDao();
        User u = new User(3, 762);
        List<User> expResult = new ArrayList<>();
        expResult.add(u);
        List<User> result = instance.findUsersByMovieId(movieId);
        assertEquals(expResult, result);
    }
    
}
