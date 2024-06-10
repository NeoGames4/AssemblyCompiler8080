package compiler;

import java.util.ArrayList;

public class Command {
	
	public final String title;
	public final int argsCount;
	public final String binaryRepr;
	
	public Command(String title, int argsCount, String binaryRepr) {
		this.title = title;
		this.argsCount = argsCount;
		this.binaryRepr = binaryRepr;
	}
	
	public ArrayList<String> run(String[] args, int line) throws CompileException {
		return new ArrayList<>();
	}
	
	public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
		return run(args, line);
	}

}
