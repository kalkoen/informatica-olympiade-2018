import java.util.Scanner;

public class OpdrA1{
	
	public static void main(String[] args) {
		Scanner scanIn = new Scanner(System.in);
		
		int n = scanIn.nextInt();
		int m = scanIn.nextInt();
		
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
		
		scanIn.close();
	}
	
}