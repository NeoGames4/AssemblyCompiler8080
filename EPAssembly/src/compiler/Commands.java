package compiler;

import java.util.ArrayList;

/**
 * Stores known assembly commands within the #{@link Commands} array.<br>
 * Usually retrieved from the {@link compiler.Compiler#commands} array.
 * @author Mika Thein
 * @see #commands
 * @see compiler.Command
 */
public class Commands {
	
	/**
	 * An {@link compiler.Command} array defining all commands the compiler knows. Currently containing the following command titles. (Please visit the source code or the GitHub readme for more information about required commands.)
	 * <ul>
	 * 	<li>MVI</li>
	 * 	<li>MOV</li>
	 * 	<li>INR</li>
	 * 	<li>DCR</li>
	 * 	<li>CMP</li>
	 * 	<li>ADI</li>
	 * 	<li>DAD</li>
	 * 	<li>ANA</li>
	 * 	<li>ORA</li>
	 * 	<li>XRA</li>
	 * 	<li>JMP</li>
	 * 	<li>JZ</li>
	 * 	<li>JNZ</li>
	 * 	<li>IN</li>
	 * 	<li>OUT</li>
	 * 	<li>HLT</li>
	 * <lu>
	 */
	final static Command[] commands = {
			/*
			 * MOVE
			 */
			new Command("MVI", 2, "00ddd110") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr.replace("ddd", register.ddd));
					
					String num = NumConverter.decToBinary(args[1], 8);
					if(num == null) throw new CompileException(line, "Invalid dec value (" + args[1] + ").");
					r.add(num);
					
					return r;
				}
			}, new Command("MOV", 2, "01dddsss") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					Register registerA = Compiler.getRegisterByTitle(args[0], line);
					if(registerA.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + registerA.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					Register registerB = Compiler.getRegisterByTitle(args[1], line);
					if(registerB.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + registerB.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(
						this.binaryRepr
							.replace("ddd", registerA.ddd)
							.replace("sss", registerB.ddd)
					);
					
					return r;
				}
			},
			/*
			 * INCREASE DECREASE
			 */
			new Command("INR", 1, "00ddd100") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr.replace("ddd", register.ddd));
					
					return r;
				}
			},
			new Command("DCR", 1, "00ddd101") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr.replace("ddd", register.ddd));
					
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
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr.replace("sss", register.ddd));
					
					return r;
				}
			},
			new Command("ADI", 1, "11000110") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					String num = NumConverter.decToBinary(args[0], 8);
					if(num == null) throw new CompileException(line, "Invalid dec value (" + args[0] + ").");
					r.add(num);
					
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
			 * LOGIC
			 */
			new Command("ANA", 1, "10100sss") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr.replace("sss", register.ddd));
					
					return r;
				}
			},
			new Command("ORA", 1, "10110sss") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr.replace("sss", register.ddd));
					
					return r;
				}
			},
			new Command("XRA", 1, "10101sss") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.ddd == null) {
						throw new CompileException(line, "Unknown binary representation for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr.replace("sss", register.ddd));
					
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
					r.add("L_FOLLOWUP");
					
					return r;
				}
			},
			new Command("JZ", 1, "11001010") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					r.add("L" + args[0]);
					r.add("L_FOLLOWUP");
					
					return r;
				}
			},
			new Command("JNZ", 1, "11000010") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					r.add(this.binaryRepr);
					
					r.add("L" + args[0]);
					r.add("L_FOLLOWUP");
					
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
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.inputAddress == null) {
						throw new CompileException(line, "Unknown input address for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr);
					r.add(register.inputAddress);
					
					return r;
				}
			},
			new Command("OUT", 1, "11010011") {
				@Override
				public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
					ArrayList<String> r = new ArrayList<>();
					
					Register register = Compiler.getRegisterByTitle(args[0], line);
					if(register.outputAddress == null) {
						throw new CompileException(line, "Unknown output address for register " + register.title + ". The register might not be designed for this usage. "
								+ "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
					}
					
					r.add(this.binaryRepr);
					r.add(register.outputAddress);
					
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
