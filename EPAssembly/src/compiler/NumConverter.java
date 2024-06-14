package compiler;

/**
 * Converts number in String representation to different bases.
 * @author Mika Thein
 */
public class NumConverter {
	
	/**
	 * Converts a binary number to a hex number.
	 * @param binary the number in binary representation
	 * @param length the String length the hex number must have
	 * @return the hex representation of the given number with length {@code length}
	 * @see #decToBinary(String, int)
	 * @see #hexToBinary(String, int)
	 */
	public static String binaryToHex(String binary, int length) {
		try {
			String r = padZerosLeft(Integer.toHexString(Integer.parseInt(binary, 2)), length).toUpperCase();
			if(r.length() != length) {
				throw new NumberException("Number overflow. Cannot convert binary " + binary + " to hex with length " + length + ".");
			} return r;
		} catch(NumberFormatException e) {}
		return null;
	}
	
	/**
	 * Converts a dec number to a binary number.
	 * @param dec the number in decimal representation
	 * @param length the String length the binary number must have
	 * @return the binary representation of the given number with length {@code length}
	 * @see #binaryToHex(String, int)
	 * @see #hexToBinary(String, int)
	 */
	public static String decToBinary(String dec, int length) {
		try {
			String r = padZerosLeft(Integer.toBinaryString(Integer.parseInt(dec)), length);
			if(r.length() != length) {
				throw new NumberException("Number overflow. Cannot convert dec " + dec + " to binary with length " + length + ".");
			} return r;
		} catch(NumberFormatException e) {}
		return null;
	}
	
	/**
	 * Converts a hex number to a binary number.
	 * @param hex the number in hex representation
	 * @param length the String length the binary number must have
	 * @return the binary representation of the given number with length {@code length}
	 * @see #binaryToHex(String, int)
	 * @see #decToBinary(String, int)
	 */
	public static String hexToBinary(String hex, int length) {
		try {
			String r = padZerosLeft(Integer.toBinaryString(Integer.parseInt(hex, 16)), length).toUpperCase();
			if(r.length() != length) {
				throw new NumberException("Number overflow. Cannot convert hex " + hex + " to binary with length " + length + ".");
			} return r;
		} catch(NumberFormatException e) {}
		return null;
	}
	
	/**
	 * Pads zeros to the left until the String {@code num} has a length of {@code length}.
	 * @param num the String
	 * @param length the length
	 * @return a string of length {@code num} (or longer, if {@code num.length()} is already greater than {@code length})
	 */
	public static String padZerosLeft(String num, int length) {
		while(num.length() < length) num = "0" + num;
		return num;
	}

}
