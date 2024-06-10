package compiler;

import java.util.ArrayList;

public class Commands {
	
	final static Command[] commands = {
			/*
			 * MOVE
			 */
			new Command("MVI", 2, "00ddd110") {
				@Override
				public ArrayList<String> run(String[] args, int line) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr.replace("ddd", Compiler.getRegisterByTitle(args[0], line).ddd));
					
					String num = NumConverter.decToBinary(args[1]);
					if(num == null) throw new CompileException(line, "Invalid number value.");
					r.add(num);
					
					return r;
				}
			}, new Command("MOV", 2, "01dddsss") {
				@Override
				public ArrayList<String> run(String[] args, int line) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(
						this.binaryRepr
							.replace("ddd", Compiler.getRegisterByTitle(args[0], line).ddd)
							.replace("sss", Compiler.getRegisterByTitle(args[1], line).ddd)
					);
					
					return r;
				}
			},
			/*
			 * INCREASE DECREASE
			 */
			new Command("INR", 1, "00ddd100") {
				@Override
				public ArrayList<String> run(String[] args, int line) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr.replace("ddd", Compiler.getRegisterByTitle(args[0], line).ddd));
					
					return r;
				}
			},
			new Command("DCR", 1, "00ddd101") {
				@Override
				public ArrayList<String> run(String[] args, int line) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr.replace("ddd", Compiler.getRegisterByTitle(args[0], line).ddd));
					
					return r;
				}
			},
			/*
			 * Arithmetik
			 */
			new Command("CMP", 1, "10111sss") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr.replace("sss", Compiler.getRegisterByTitle(args[0], line).ddd));
					
					return r;
				}
			},
			new Command("DAD", 1, "") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					String binary;
					
					switch(args[0]) {
						case "B":
							binary = "00001001";
							break;
						case "D":
							binary = "00011001";
							break;
						case "H":
							binary = "00101001";
							break;
						case "SP":
							binary = "00111001";
							break;
						default:
							throw new CompileException(line, "Unknown argument '" + args[0] + "' for command '" + this.title + "'.");
					}
					
					r.add(binary);
					
					return r;
				}
			},
			/*
			 * JUMP
			 */
			new Command("JMP", 1, "11000011") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					r.add("L" + args[0]);
					
					return r;
				}
			},
			new Command("JZ", 1, "11001010") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					r.add("L" + args[0]);
					
					return r;
				}
			},
			new Command("JNZ", 1, "11000010") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					r.add("L" + args[0]);
					
					return r;
				}
			},
			/*
			 * INPUT OUTPUT
			 */
			new Command("IN", 1, "11011011") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					r.add(NumConverter.decToBinary(args[0]));
					
					return r;
				}
			},
			new Command("OUT", 1, "11010011") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					r.add(NumConverter.decToBinary(args[0]));
					
					return r;
				}
			},
			/*
			 * OTHERS
			 */
			new Command("HLT", 0, "01110110") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					return r;
				}
			}
	};

}
