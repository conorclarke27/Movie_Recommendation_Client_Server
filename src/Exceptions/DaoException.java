package Exceptions;

import java.sql.SQLException;

/**
 *
 * @author conor
 */
public class DaoException extends SQLException 
{

    /**
     *
     */
    public DaoException() {
    }

    /**
     *
     * @param aMessage
     */
    public DaoException(String aMessage) 
    {
        super(aMessage);
    }
}