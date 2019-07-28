

import java.util.*;


public class OpdrachtC1_2019
{

	public static void main(String[] args)
	{
		Fitting r = new Fitting();
		r.execute();
	}
}

class Fitting {

	int[] values;
	int goal;
	int strategies = 0;
	
	public void execute() {

		Scanner scanIn = new Scanner(System.in);

		int size = scanIn.nextInt();
		values = new int[size];
		for(int i = 0; i < size; i++) {
			values[i] = scanIn.nextInt();
		}

		Arrays.sort(values);
		goal = scanIn.nextInt();
		fit(0, goal);
		System.out.println(strategies);
		
		scanIn.close();
		
	}
	
	public void fit(int val, int maxVal) {
		if(val == goal){
			strategies ++;
			return;
		}
		
		int end = Math.min(maxVal, goal-val);
		if(end==1){
			strategies++;
			return;
		}
		for (int i = 0; i < values.length; i++) {
			if(values[i] > end) {
				break;
			}
			fit(val+values[i], values[i]);
		}
	}
}

