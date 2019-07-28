import java.util.Scanner;

public class OpdrA3{
	
	public static void main(String[] args) {
		Ants ants = new Ants();
		ants.execute();
	}
	
}

class Ants {
	
	char[] ants;
	boolean[] goingRight;
	int seconds;
	
	public void execute() {
		Scanner scanIn = new Scanner(System.in);
		
		int numLeft = scanIn.nextInt();
		int numRight = scanIn.nextInt();
		scanIn.nextLine();
		String leftString = scanIn.nextLine();
		String rightString = scanIn.nextLine();
		seconds = scanIn.nextInt();
		
		char[] leftChars = leftString.toCharArray();
		char[] rightChars = rightString.toCharArray();
		
		int totalAnts = numLeft + numRight;
		ants = new char[totalAnts];
		goingRight = new boolean[totalAnts];
		
		for(int i = 0; i < numLeft; i++) {
			ants[i] = leftChars[numLeft-i-1];
			goingRight[i] = true;
		}
		for(int i = 0; i < numRight; i++) {
			ants[numLeft + i] = rightChars[i];
			goingRight[numLeft+i] = false;
		}
		
		for(int i = 0; i < seconds; i++) {
			stepAlternative();	
		}
		
		System.out.println(new String(ants));
		
		scanIn.close();
	}
	
	public void step() {
		char[] newAnts = new char[ants.length];
		boolean[] newGoingRight = new boolean[ants.length];
		for(int i = 0; i < ants.length-1; i++) {
			if(goingRight[i] && !goingRight[i+1]) {
				newAnts[i] = ants[i+1];
				newAnts[i+1] = ants[i];
				newGoingRight[i] = goingRight[i+1];
				newGoingRight[i+1] = goingRight[i];
				i++;
			} else {
				newAnts[i] = ants[i];
				newAnts[i+1] = ants[i+1];
				newGoingRight[i] = goingRight[i];
				newGoingRight[i+1] = goingRight[i+1];
			}
		}
		if(newAnts[ants.length-1] == 0) {
			newAnts[ants.length-1] = ants[ants.length-1];
		}
		ants = newAnts;
		goingRight = newGoingRight;
	}
	
	public void stepAlternative() {
		char[] newAnts = new char[ants.length];
		System.arraycopy(ants, 0, newAnts, 0, ants.length);
		boolean[] newGoingRight = new boolean[ants.length];
		System.arraycopy(goingRight, 0, newGoingRight, 0, ants.length);
		for(int i = 0; i < ants.length-1; i++) {
			if(goingRight[i] && !goingRight[i+1]) {
				flip(i, i+1, newAnts, newGoingRight);
			}
		}
		ants = newAnts;
		goingRight = newGoingRight;
	}
	
	public void flip(int i, int j, char[] ants, boolean[] goingRight) {
		char jChar = ants[j];
		ants[j] = ants[i];
		ants[i] = jChar;
		boolean jBoolean = goingRight[j];
		goingRight[j] = goingRight[i];
		goingRight[i] = jBoolean;
	}
	
}