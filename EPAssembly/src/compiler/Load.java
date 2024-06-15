package compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Contains the main method of this compiler, which is responsible for reading the assembly code and writing it to the target file.
 * @author Mika Thein
 * @see #main(String[])
 */
public class Load {
	
	/**
	 * The main method. Reads the assembly code and translates it into hex using the {@link compiler.Compiler} class.<br>
	 * <ul>
	 * 	<li>The first argument specifies the path of the source file.</li>
	 * 	<li>The second argument specifies the path of the destination file.</li>
	 * 	<li>If the third argument is set to "binary", the compiler will output binary instead of hex. (Optional.)</li>
	 * 	<li>If the fourth argument is set to "debug", the {@link compiler.Compiler#DEBUG} mode will be set to true. (Optional.)</li>
	 * <lu>
	 * @param args requires at least two arguments: A source file path and a destination file path. (Absolute or relative to the jar location.)
	 */
	public static void main(String[] args) {
		if(args.length < 2) {
			throw new RuntimeException("Not enough arguments. Source and destination file paths required.\nFor example: folder/in.asm another_folder/out.txt.");
		}
		
		boolean binary = args.length > 2 && args[2].equals("binary");
		
		Compiler.DEBUG = args.length > 3 && args[3].equals("debug");
		
		try {
			File from = new File(args[0]);
			File to = new File(args[1]);
			
			System.out.println("Reading from " + from.getAbsolutePath() + "...");
			
			BufferedReader reader = new BufferedReader(new FileReader(from));
			ArrayList<String> lines = new ArrayList<>();
			
			String line;
			while((line = reader.readLine()) != null) {
				lines.add(line);
			} reader.close();
			
			String[] code = new String[lines.size()];
			for(int i = 0; i<code.length; i++) {
				code[i] = lines.get(i);
			}
			
			Compiler compiler = new Compiler(code);
			String[] result = !binary ? compiler.compileToHex() : compiler.compileToBinary();
			
			System.out.println("Writing to " + to.getAbsolutePath() + "...");
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(to));
			for(String r : result) {
				writer.append(r + "\n");
			} writer.close();
			
			System.out.println("Done!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
