package compiler;

public class CompileException extends Exception {
	
	private static final long serialVersionUID = 1L;
	public final int line;
	public final String message;
	
	public CompileException(int line, String message) {
		this.line = line;
		this.message = message;
	}
	
	@Override
	public void printStackTrace() {
		System.err.println((line >= 0 ? ("Line " + line + ": ") : "") + message);
	}

}
