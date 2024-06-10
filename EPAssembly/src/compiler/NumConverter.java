package compiler;

public class NumConverter {
	
	public static String binaryToHex(String binary) {
		try {
			return padZeros(Integer.toHexString(Integer.parseInt(binary, 2)), 2).toUpperCase();
		} catch(Exception e) {}
		return null;
	}
	
	public static String decToBinary(String dec) {
		try {
			return padZeros(Integer.toBinaryString(Integer.parseInt(dec)), 8);
		} catch(Exception e) {}
		return null;
	}
	
	public static String padZeros(String num, int length) {
		while(num.length() < length) num = "0" + num;
		return num;
	}

}
