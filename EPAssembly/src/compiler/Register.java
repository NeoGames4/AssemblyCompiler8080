package compiler;

/**
 * Represents one or multiple registers with a specific title, as used by the chip with the Intel 8080 architecture.<br>
 * Usually stored in {@link compiler.Compiler#registers}.
 * @author Mika Thein
 */
public class Register {
	
	/**
	 * The title of the register.
	 * @see #ddd
	 * @see #inputAddress
	 * @see #outputAddress
	 */
	public final String title;
	/**
	 * The 3 bit binary representation of the register associated with the title.<br>
	 * May be {@code null} if not specified.
	 * @see #title
	 * @see #inputAddress
	 * @see #outputAddress
	 */
	public final String ddd;
	/**
	 * The 8 bit binary address of the input register associated with the title.<br>
	 * May be {@code null} if not specified.
	 * @see #title
	 * @see #outputAddress
	 * @see #ddd
	 */
	public final String inputAddress;
	/**
	 * The 8 bit binary address of the output register associated with the title.<br>
	 * May be {@code null} if not specified.
	 * @see #title
	 * @see #inputAddress
	 * @see #ddd
	 */
	public final String outputAddress;
	
	/**
	 * Initializes a new register.
	 * <b>Please note:</b> This instance can describe multiple, independent registers with the same title.
	 * @param title the {@link #title} of the register
	 * @param ddd the 3 bit binary representation of the register ({@link #ddd})
	 * @param inputAddress the 8 bit binary address of the input register associated with the title ({@link #inputAddress})
	 * @param outputAddress the 8 bit binary address of the output register associated with the title ({@link #outputAddress})
	 */
	public Register(String title, String ddd, String inputAddress, String outputAddress) {
		this.title = title;
		this.ddd = ddd;
		this.inputAddress = inputAddress;
		this.outputAddress = outputAddress;
	}

}
