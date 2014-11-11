package de.hsosnabrueck.hipsterbility.exceptions;

/**
 * Created by Albert on 07.11.2014.
 */
public class DataAccessException extends Exception {
    public DataAccessException(Exception e) {
        super(e);
    }

    public DataAccessException(String s) {
        super(s);
    }
}
