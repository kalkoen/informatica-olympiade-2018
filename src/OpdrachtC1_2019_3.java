

import java.util.*;


public class OpdrachtC1_2019_3
{

	public static void main(String[] args)
	{
		Fitting3 r = new Fitting3();
		r.execute();
	}
}


class Fitting3 {

	int[] values;
	int goal;
	long strategies = 1;
	
	int posWhen;
	
	public void execute() {

		Scanner scanIn = new Scanner(System.in);

		int size = scanIn.nextInt();
		int[] tempValues = new int[size];
		for(int i = 0; i < size; i++) {
			tempValues[i] = scanIn.nextInt();
		}

		Arrays.sort(tempValues);
		values = new int[size-1];
		System.arraycopy(tempValues, 1, values, 0, values.length);
		goal = scanIn.nextInt();
		
		for(int i = 1; i < 100; i++) {
			strategies = 1;
			goal = i;
			fit(0, goal);
			System.out.println(strategies);
		}
		

		//System.out.println(strategies);
		
		scanIn.close();
		
	}
	
	public void fit(int val, int maxVal) {
		if(val == goal){
			return;
		}

		int end = Math.min(maxVal, goal-val);
		for (int i = 0; i < values.length; i++) {
			if(values[i] > end) {
				break;
			}
			strategies++;
			fit(val+values[i], values[i]);
		}
	}
}

