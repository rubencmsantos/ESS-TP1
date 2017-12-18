package Exceptions;

public class UserAlreadyException extends Exception {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor for objects of class PassInvalException
     */
    public UserAlreadyException() {
        super();
    }

    public UserAlreadyException(String m) {
       super(m);
    }
}
