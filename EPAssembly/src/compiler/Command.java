package compiler;

import java.util.ArrayList;

/**
 * Represents an assembly command.<br>
 * Usually defined in the {@link compiler.Commands} class.
 * @author Mika Thein
 */
public class Command {
	
	/**
	 * The title of the command. For example {@code MVI} (for move immediate).
	 */
	public final String title;
	/**
	 * The exact amount of arguments the command requires.
	 */
	public final int argsCount;
	/**
	 * The binary representation of the command. May be used in {@link #run(String[], int)}, no restrictions apply.<br>
	 * (This variable is not strictly required to contain a binary string–or anything at all–as the binary representation can be defined within said {@code run} method.
	 * Since many commands consist of a simple binary string, it is recommended to store it in this variable for easy maintenance.)
	 */
	public final String binaryRepr;
	
	/**
	 * Initializes a class to store information about a command.<br>
	 * {@link #run(String[], int)} must be overwritten to define any function. For examples, please visit the Java source code of the {@link compiler.Commands} class.
	 * @param title the command {@link #title}
	 * @param argsCount the amount of arguments required ({@link #argsCount})
	 * @param binaryRepr the binary representation ({@link #binaryRepr})
	 * @see #run(String[], int, Compiler)
	 */
	public Command(String title, int argsCount, String binaryRepr) {
		this.title = title;
		this.argsCount = argsCount;
		this.binaryRepr = binaryRepr;
	}
	
	/**
	 * Defines how the command should be represented if called, by returning 8 bit binary representations of this command.<br>
	 * This method is called from the compiler whenever this command is mentioned in the source code. Does nothing by default.
	 * @param args the given arguments of length {@link #argsCount}
	 * @param line the line number from where the command is called (useful to throw a {@link compiler.CompileException})
	 * @param compiler the {@link compiler.Compiler} instance from where this command is called
	 * @return the 8 bit binary strings representing this command
	 * @throws CompileException if something goes wrong
	 */
	public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
		return new ArrayList<>();
	}

}
