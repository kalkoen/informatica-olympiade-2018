import java.util.Scanner;

public class OpdrA4{
	
	public static void main(String[] args) {
		Bitcoin bitcoin = new Bitcoin();
		bitcoin.execute();
	}
	
}

class Bitcoin {
	
	int[] values;
	
	public void execute() {
		Scanner scanIn = new Scanner(System.in);
		
		int days = scanIn.nextInt();
		values = new int[days];
		for(int i = 0; i < days; i++) {
			values[i] = scanIn.nextInt();
		}
	
		int maxProfit = getMaxProfit(values);
		
		System.out.println(maxProfit);
		
		scanIn.close();
	}
	
	public int getMaxProfit(int[] values) {
		int maxProfit = 0;
		
		for(int i = 0; i < values.length-1; i++) {
			int value = values[i];
			int nextValue = values[i+1];
			if(nextValue - value > 0) {
				maxProfit += nextValue - value;
			}
		}
		
		return maxProfit;
	}
	
}