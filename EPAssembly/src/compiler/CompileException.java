package compiler;

/**
 * An exception that occurs when something goes wrong within the compilation process of the assembly {@link compiler.Compiler}.
 * @author Mika Thein
 */
public class CompileException extends Exception {
	
	private static final long serialVersionUID = 1L;
	/**
	 * The line number of the source code line that caused the error. May be negative to indicate an unknown line.
	 * @see #message
	 */
	public final int line;
	/**
	 * A description of the error.
	 * @see #line
	 */
	public final String message;
	
	/**
	 * Thrown whenever something goes wrong within the compilation process of the assembly {@link compiler.Compiler}.
	 * @param line the {@link #line} number
	 * @param message a description {@link #message}
	 */
	public CompileException(int line, String message) {
		this.line = line;
		this.message = message;
	}
	
	@Override
	public void printStackTrace() {
		System.err.println((line >= 0 ? ("Line " + (line+1) + ": ") : "") + message);
	}

}
