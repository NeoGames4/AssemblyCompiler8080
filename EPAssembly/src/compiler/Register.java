package compiler;

/**
 * Represents a register of the used chip with Intel 8080 architecture.
 * @author Mika Thein
 */
public class Register {
	
	/**
	 * The title of the register.
	 * @see #ddd
	 */
	public final String title;
	/**
	 * The binary representation of the register associated with the title.
	 * @see #title
	 */
	public final String ddd;
	
	/**
	 * Initializes a new register.
	 * @param title the {@link #title} of the register
	 * @param ddd the binary representation of the register ({@link #ddd})
	 */
	public Register(String title, String ddd) {
		this.title = title;
		this.ddd = ddd;
	}

}
