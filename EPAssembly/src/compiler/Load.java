package compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Load {
	
	public static void main(String[] args) {
		if(args.length < 2) {
			throw new RuntimeException("Not enough arguments. Source and destination file paths required.\nFor example: ./in.asm ./out.txt.");
		}
		
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
			String[] result = compiler.compileToHex();
			
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
