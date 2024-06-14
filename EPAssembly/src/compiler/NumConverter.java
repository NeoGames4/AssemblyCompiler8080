package compiler;

public class NumConverter {
	
	public static String binaryToHex(String binary, int length) {
		try {
			String r = padZerosLeft(Integer.toHexString(Integer.parseInt(binary, 2)), length).toUpperCase();
			if(r.length() != length) {
				throw new NumberException("Number overflow. Cannot convert binary " + binary + " to hex with length " + length + ".");
			} return r;
		} catch(NumberFormatException e) {}
		return null;
	}
	
	public static String decToBinary(String dec, int length) {
		try {
			String r = padZerosLeft(Integer.toBinaryString(Integer.parseInt(dec)), length);
			if(r.length() != length) {
				throw new NumberException("Number overflow. Cannot convert dec " + dec + " to binary with length " + length + ".");
			} return r;
		} catch(NumberFormatException e) {}
		return null;
	}
	
	public static String padZerosLeft(String num, int length) {
		while(num.length() < length) num = "0" + num;
		return num;
	}

}
