package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Compiler {
	
	final static Command[] commands = Commands.commands;
	
	final static Register[] registers = {
			new Register("A", "111"),
			new Register("B", "000"),
			new Register("C", "001"),
			new Register("D", "010"),
			new Register("E", "011"),
			new Register("H", "100"),
			new Register("L", "101")
	};
	
	public static final boolean DEBUG = true;
	
	public final String[] code;
	
	private HashMap<String, Integer> labels = new HashMap<>();
	
	public Compiler(String[] code) {
		this.code = code;
	}
	
	public int getLabelAddress(String label) throws CompileException {
		if(labels.containsKey(label)) {
			return labels.get(label);
		} throw new CompileException(-1, "Unknown label '" + label + "'.");
	}
	
	public static Command findCommandByTitle(String title, int argsCount, int line) {
		for(Command c : commands) {
			if(c.title.toUpperCase().equals(title.toUpperCase()) && c.argsCount == argsCount) return c;
		} return null;
	}
	
	public static Command getCommandByTitle(String title, int argsCount, int line) throws CompileException {
		Command c = findCommandByTitle(title, argsCount, line);
		if(c != null) return c;
		throw new CompileException(line, "Unknown command '" + title + "' with " + argsCount + " arguments.");
	}
	
	public static Register findRegisterByTitle(String title, int line) {
		for(Register r : registers) {
			if(r.title.toUpperCase().equals(title.toUpperCase())) return r;
		} return null;
	}
	
	public static Register getRegisterByTitle(String title, int line) throws CompileException {
		Register r = findRegisterByTitle(title, line);
		if(r != null) return r;
		throw new CompileException(line, "Unknown register '" + title + "'.");
	}
	
	public String[] compileToBinary() throws CompileException { // TODO tag same line support, does not support multiple spaces within line
		ArrayList<String> binaries = new ArrayList<>();
		
		for(int i = 0; i<code.length; i++) {
			final String line = code[i].split(";")[0].trim();
			if(line.isEmpty()) continue;
			if(line.contains("  ")) {
				throw new CompileException(i, "Multiple spaces next to each other are only allowed at the beginning or at the end of a line.");
			}
			
			final String[] components = line.split(" ", 2);
			final String[] args = components.length > 1 ? components[1].split(",") : new String[0];
			
			System.out.println(i + " " + line);
			
			if(line.endsWith(":")) {
				labels.put(line.substring(0, line.length()-1), binaries.size());
			} else {
				int preSize = binaries.size();
				Command c = getCommandByTitle(components[0], args.length, i);
				binaries.addAll(c.run(args, i, this));
				if(binaries.size() > preSize) binaries.set(preSize, binaries.get(preSize) + "@" + line + " l" + i);
			}
		}
		
		String[] cBinaries = new String[binaries.size()];
		for(int i = 0; i<binaries.size(); i++) {
			cBinaries[i] = binaries.get(i);
		}
		
		for(int i = 0; i<cBinaries.length; i++) {
			if(cBinaries[i].startsWith("L")) {
				cBinaries[i] = NumConverter.decToBinary(getLabelAddress(cBinaries[i].substring(1)) + "");
			}
		}
		return cBinaries;
	}
	
	public String[] compileToHex() throws CompileException {
		String[] c = compileToBinary();
		
		for(int i = 0; i<c.length; i++) {
			String[] comp = c[i].split("@", 2);
			c[i] = NumConverter.binaryToHex(comp[0]);
			if(comp.length > 1) c[i] += "@" + comp[1];
		}
		
		return c;
	}

}
