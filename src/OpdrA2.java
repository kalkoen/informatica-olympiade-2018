import java.util.Scanner;

public class OpdrA2{
	
	public static void main(String[] args) {
		Caesar caesar = new Caesar();
		caesar.execute();
	}
	
}

class Caesar {
	
	public void execute() {
		Scanner scanIn = new Scanner(System.in);
		
		String word = scanIn.nextLine();
		char[] chars = word.toCharArray();
		int n = scanIn.nextInt();
		
		String encryptedWord = "";
		
		for(int i = 0; i < chars.length; i++) {
			encryptedWord += getEncryptedChar(chars[i], n);
		}
		
		System.out.println(encryptedWord);
		

		scanIn.close();
	}
	
	public char getEncryptedChar(char c, int n) {
		// Subtract 65 (first capital), loop back to 0 if greater than 25, add 65 again to get correct capital.
		return (char) (65 + (c - 65 + n)%26);
	}
	
}