package compiler;

/**
 * An exception that occurs whenever a number cannot be converted using {@link compiler.NumConverter}.
 * @author Mika Thein
 */
public class NumberException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * A description of the error.
	 */
	public final String message;
	
	/**
	 * Might be thrown when something wrong within the conversion process of the {@link compiler.NumConverter}.
	 * @param message
	 */
	public NumberException(String message) {
		this.message = message;
	}
	
	@Override
	public void printStackTrace() {
		System.err.println(message);
	}

}
