

import java.util.*;


public class OpdrachtB3
{

	public static void main(String[] args)
	{
		Bar bar = new Bar();
		bar.execute();
	}
}

class Bar {

	int[] values;
	List<SaleStrategy> strategies;
	
	
	public void execute() {
		
		int length = 25;
		values = new int[length];
		initializeValues();
		
		strategies = new ArrayList<>();
		
		exploreStrategies();
		
//		strategies.sort(new Comparator<SaleStrategy>() {
//
//			@Override
//			public int compare(SaleStrategy o1, SaleStrategy o2) {
//				return -(o1.calculateNetProfit() - o2.calculateNetProfit());
//			}
//		});
		
		int maxProfit = 0;
		
		
//		for (int i = 0; i < 10; i++) {
//			System.out.println(strategies.get(i));
//		}
		for(SaleStrategy s : strategies) {
			int netProfit = s.calculateNetProfit();
			if(netProfit > maxProfit) {
				maxProfit = netProfit;
			}
			System.out.println(s.toString());
		}
		System.out.println(strategies.size());
		// 57
		System.out.println("Max profit: " + maxProfit);
		
	}
	
	public void exploreStrategies() {
		exploreStrategies(new ArrayList<>(), 0);
	}
	
	public void exploreStrategies(List<Integer> lengths, int totalLength) {
		if(totalLength > 25) {
			System.out.println("Not supposed to happen");
			return;
		}
		if(totalLength == 25) {
			strategies.add(new SaleStrategy(lengths));
			return;
		}
		int remainingLength = 25 - totalLength;
		int previousNumber = 25;
		if(!lengths.isEmpty()) {
			previousNumber = lengths.get(lengths.size()-1);
		}
		int end = Math.min(remainingLength, previousNumber);
		for(int i = 1; i <= end; i++) {
			List<Integer> newLengths = new ArrayList<>(lengths);
			newLengths.add(i);
			exploreStrategies(newLengths, totalLength + i);
		}
	}
	
	
	public void initializeValues() {
		values[0] =  21;
		values[1] = 39;
		values[2] = 41;
		values[3] = 61;
		values[4] = 89;
		values[5] = 97;
		
		values[6] = 108;
		values[7] = 111;
		values[8] = 140;
		values[9] = 159;
		values[10] = 169;
		values[11] = 170;
		
		values[12] = 192;
		values[13] = 208;
		values[14] = 219;
		values[15] = 234;
		values[16] = 244;
		values[17] = 263;
		
		values[18] = 280;
		values[19] = 285;
		values[20] = 313;
		values[21] = 314;
		values[22] = 324;
		values[23] = 342;
		values[24] = 350;
		
		
	}
	
	public int getValue(int length) {
		// starts at 0, not 1
		return values[length-1];
	}
	
	class SaleStrategy {
		
		List<Integer> lengths;
		
		public SaleStrategy() {
			lengths = new ArrayList<Integer>();
		}
		
		public SaleStrategy(List<Integer> lengths) {
			this.lengths = lengths;
		}
		
		public int calculateNetProfit() {
			return calculateRevenue() - calculateCosts();
		}
		
		public int calculateRevenue() {
			int revenue = 0;
			for(int length : lengths) {
				revenue += getValue(length);
			}
			return revenue;
		}
		
		public int calculateCosts() {
			return (lengths.size() - 1) * 5;
		}
		
		@Override
		public String toString() {
			String s = "";
			
//			s += "-------------------\n";
//			for(int length : lengths) {
//				s += length + "cm : +" + getValue(length) + "\n";
//			}
//			s+= "parts: " + lengths.size() +  "  revenue: " + calculateRevenue() + "  cost: " + calculateCosts() + "\nnet profit: " + calculateNetProfit();
			
			s+= "Net Profit:";
			s+=calculateNetProfit();
			s+= "\t";
			int total = 0;
			for(int length : lengths) {
				s += length + "+";
				total+= length;
			}
			s = s.substring(0, s.length()-1);
			s += "=" + total;
			return s;
		}
		
	}

}

