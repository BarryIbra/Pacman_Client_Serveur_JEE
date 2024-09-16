package DAO;


public class DAOException extends RuntimeException {
	public class DaoException extends Exception {
	    public DaoException(String message) {
	        super(message);
	    }
	}
}