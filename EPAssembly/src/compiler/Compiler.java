package compiler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main component of this project. Contains the algorithm to compile the assembly code into binary or hex.<br>
 * The capacity of known instructions is defined in the {@link compiler.Instructions} class.
 * @author Mika Thein
 * @see #Compiler(String[])
 * @see #compileToBinary()
 * @see #compileToHex()
 */
public class Compiler {
	
	/**
	 * A list of all available assembly instructions as defined in the {@link compiler.Instructions} class. 
	 * @see compiler.Instructions#instructions
	 */
	final static Instruction[] instructions = Instructions.instructions;
	
	/**
	 * Stores the title of each register and the individual binary representation "ddd" or "sss", as well as the addresses associated with the title.<br>
	 * <b>Please note:</b> One {@link compiler.Register} can describe multiple, independent registers with the same title.
	 */
	final static Register[] registers = {
			new Register("A",	"111", NumConverter.hexToBinary("00", 8),	null),
			new Register("B",	"000", NumConverter.hexToBinary("01", 8),	null),
			new Register("C",	"001", NumConverter.hexToBinary("02", 8),	null),
			new Register("D",	"010", null,								null),
			new Register("E",	"011", null,								null),
			new Register("H",	"100", null,								null),
			new Register("L",	"101", null,								null),
			new Register("X",	null,  null,								NumConverter.hexToBinary("00", 8)),
			new Register("R",	null,  null, 								NumConverter.hexToBinary("01", 8)),
			new Register("DAC",	null,  null,								NumConverter.hexToBinary("04", 8)),
			new Register("ADC",	null,  NumConverter.hexToBinary("05", 8),	NumConverter.hexToBinary("05", 8))
	};
	
	/**
	 * Whether additional information should be printed.<br>
	 * Writes the original code line next to the hex translation into the destination file if true. (Separated by an '@' character.)
	 */
	public static boolean DEBUG = false;
	
	/**
	 * Stores the original assembly source code.
	 * @see #labels
	 */
	public final String[] code;
	
	/**
	 * Stores all labels found in the source codes with their line number.
	 * @see #code
	 */
	private HashMap<String, Integer> labels = new HashMap<>();
	
	/**
	 * Initializes a new compiler instance that can be used to translate Intel 8080 assembly source code into hex or binary representation.
	 * @param code
	 */
	public Compiler(String[] code) {
		this.code = code;
	}
	
	/**
	 * Accepts a label title and returns the address of the line where it has been declared
	 * @param label the label title
	 * @return the line number where the label was declared
	 * @throws CompileException if the label has not been declared
	 * @see #labels
	 */
	public int getLabelAddress(String label) throws CompileException {
		if(labels.containsKey(label)) {
			return labels.get(label);
		} throw new CompileException(-1, "Unknown label '" + label + "'.");
	}
	
	/**
	 * Searches all known instructions for the given instruction title and the amount of arguments.
	 * @param title the title of the instruction
	 * @param argsCount the amount of arguments the instruction accepts
	 * @param line the current line number
	 * @return the {@link compiler.Instruction} if the instruction exists, {@code null} otherwise
	 * @see #instructions
	 */
	public static Instruction findInstructionByTitle(String title, int argsCount, int line) {
		for(Instruction c : instructions) {
			if(c.title.toUpperCase().equals(title.toUpperCase()) && c.argsCount == argsCount) return c;
		} return null;
	}
	
	/**
	 * Runs {@link #findInstructionByTitle(String, int, int)}, but throws an exception if the instruction could not be found.
	 * @param title the title of the instruction
	 * @param argsCount the amount of arguments the instruction accepts
	 * @param line the current line number
	 * @return the {@link compiler.Instruction} if the instruction exists
	 * @throws CompileException if the instruction does not exist
	 * @see #instructions
	 */
	public static Instruction getInstructionByTitle(String title, int argsCount, int line) throws CompileException {
		Instruction c = findInstructionByTitle(title, argsCount, line);
		if(c != null) return c;
		throw new CompileException(line, "Unknown instruction '" + title + "' with " + argsCount + " arguments.");
	}
	
	/**
	 * Searches all known registers for the given register title.
	 * @param title the title of the register
	 * @param line the current line number
	 * @return the {@link compiler.Register} if the register exists, {@code null} otherwise
	 * @see #registers
	 */
	public static Register findRegisterByTitle(String title, int line) {
		for(Register r : registers) {
			if(r.title.toUpperCase().equals(title.toUpperCase())) return r;
		} return null;
	}
	
	/**
	 * Runs {@link #findRegisterByTitle(String, int)}, but throws an exception if the register could not be found.
	 * @param title the title of the register
	 * @param line the current line number
	 * @return the {@link compiler.Register} if the register exists
	 * @throws CompileException if the register does not exist
	 * @see #registers
	 */
	public static Register getRegisterByTitle(String title, int line) throws CompileException {
		Register r = findRegisterByTitle(title, line);
		if(r != null) return r;
		throw new CompileException(line, "Unknown register '" + title + "'.");
	}
	
	/**
	 * Compiles the source code into binary.
	 * @return the binary representation of the source code
	 * @throws CompileException if something goes wrong
	 * @see #compileToHex()
	 */
	public String[] compileToBinary() throws CompileException { // TODO tag same line support
		ArrayList<String> binaries = new ArrayList<>();
		
		System.out.println("Starting compilation process to binary...");
		
		for(int i = 0; i<code.length; i++) {
			final String line = code[i].split(";")[0].trim();	// Separate comments
			if(line.isEmpty()) continue;						// Ignore empty lines
			
			final String[] components = line.split(" ", 2);		// Seeks for the instruction title and the arguments
			final String[] args = components.length > 1 ? components[1].split(",") : new String[0];		// Seeks for the instruction arguments
			
			for(int j = 0; j<args.length; j++) {				// Trims the arguments
				args[j] = args[j].trim();
			}
			
			if(DEBUG) System.out.println(i + " " + line);
			
			if(line.endsWith(":")) {							// Detects labels
				labels.put(line.substring(0, line.length()-1), binaries.size());
			} else {											// Detects instructions
				int preSize = binaries.size();
				Instruction c = getInstructionByTitle(components[0], args.length, i);
				binaries.addAll(c.run(args, i, this));
				if(binaries.size() > preSize) binaries.set(preSize, binaries.get(preSize) + (DEBUG ? "@" + line + " l" + i : ""));
			}
		}
		
		String[] cBinaries = new String[binaries.size()];		// Converts the binaries ArrayList into a String array
		for(int i = 0; i<binaries.size(); i++) {
			cBinaries[i] = binaries.get(i);
		}
		
		System.out.println("Replacing labels...");
		
		for(int i = 0; i<cBinaries.length; i++) {				// Replaces labels
			if(cBinaries[i].startsWith("L")) {
				if(cBinaries[i].equals("L_FOLLOWUP")) {
					throw new CompileException(i, "Lonely label follow-up. This exception should never appear. Please seek contact on this project's GitHub repository.");
				}
				if(cBinaries.length <= i+1 || !cBinaries[i+1].equals("L_FOLLOWUP")) {
					throw new CompileException(i, "Missing label follow-up. This exception should never appear. Please seek contact on this project's GitHub repository.");
				}
				
				String binary = NumConverter.decToBinary(getLabelAddress(cBinaries[i].substring(1)) + "", 16);
				cBinaries[i] = binary.substring(8);
				cBinaries[++i] = binary.substring(0, 8);
			}
		}
		
		return cBinaries;
	}
	
	/**
	 * Compiles the source code into binary, then hex.
	 * @return the hex representation of the source code
	 * @throws CompileException if something goes wrong
	 * @see #compileToBinary()
	 */
	public String[] compileToHex() throws CompileException {
		String[] c = compileToBinary();
		
		System.out.println("Converting to Hex...");
		
		for(int i = 0; i<c.length; i++) {
			String[] comp = c[i].split("@", 2);
			c[i] = NumConverter.binaryToHex(comp[0], 2);
			if(comp.length > 1) c[i] += "@" + comp[1];
		}
		
		return c;
	}

}
