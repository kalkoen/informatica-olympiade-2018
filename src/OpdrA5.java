import java.util.Scanner;

public class OpdrA5{
	
	public static void main(String[] args) {
		Blocks blocks = new Blocks();
		blocks.execute();
	}
	
}

class Blocks {
	
	int size;
	int[] front;
	int[] side;
	
	public void execute() {
		Scanner scanIn = new Scanner(System.in);
		
		size = scanIn.nextInt();
		front = new int[size];
		for(int i = 0; i < size; i++) {
			front[i] = scanIn.nextInt();
		}
		side = new int[size];
		for(int i = 0; i < size; i++) {
			side[i] = scanIn.nextInt();
		}
		
		//System.out.println(Arrays.toString(side));
		
		int[][] grid = getMinimumGrid(size, front, side);
		
		//printGrid(grid);
		
		int minBlocks = totalGrid(grid);
		//System.out.println(minBlocks);
		
		fillGrid(grid);
		
		//printGrid(grid);
		
		int maxBlocks = totalGrid(grid);
		int m = maxBlocks - minBlocks;
		System.out.println(minBlocks + " " + m);
		
		scanIn.close();
		
	}
	
	public void printGrid(int[][] grid) {
		for(int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(grid[j][size-1-i] + " ");
			}
			System.out.println();
		}
	}
	
	public int[][] getMinimumGrid(int size, int[] front, int[] side) {
		int[][] grid = new int[size][size];
		boolean[] frontSatisfied = new boolean[size];
		boolean[] sideSatisfied = new boolean[size];
		
		// Start at with the highest ones
		int desiredHeight = getMax(front);
//		System.out.println(desiredHeight);
		
		while(desiredHeight > 0) {
		
			for(int i = 0; i < size; i++) {
				int height = front[i];
				if(height != desiredHeight) {continue;}
			
				int sidePos = satisfy(size, height, side, sideSatisfied);
				grid[i][sidePos] = height;
				frontSatisfied[i] = true;
				
				if(getMaxAlternative(grid, sidePos) == height) {
					sideSatisfied[sidePos] = true;
				}
			}
			
			for(int i = 0; i < size; i++) {
				int height = side[i];
				if(height != desiredHeight) {continue;}
				if(sideSatisfied[i]) {continue;}
			
				int frontPos = satisfy(size, height, front, frontSatisfied);
				grid[frontPos][i] = height;
				sideSatisfied[i] = true;
			}
			
			desiredHeight--;
			
			
		}
		
//		System.out.println(Arrays.toString(frontSatisfied));
//		System.out.println(Arrays.toString(sideSatisfied));
		
		return grid;
	}
	
	public int satisfy(int size, int height, int[] opposingView, boolean[] opposingViewSatisfied) {
			
			// First check for dissatisfied of same height
			for (int j = 0; j < size; j++) {
				int oHeight = opposingView[j];
				if(oHeight != height) {continue;}
				if(opposingViewSatisfied[j]) {continue;}
				return j;
			}
			
			// Then check for others with same height or higher
			for (int j = 0; j < size; j++) {
				int oHeight = opposingView[j];
				if(oHeight < height) {continue;}
				return j;
			}
			
			return -1;
	}
	
	public void fillGrid(int[][] grid) {
		// horizontal / vertical as opposed to debug messaging in execute() :')
		for (int i = 0; i < grid.length; i++) {
			int[] horizontal = grid[i];
			int maxHorizontal = getMax(horizontal);
			for (int j = 0; j < horizontal.length; j++) {
				int maxVertical = getMaxAlternative(grid, j);
				// Keep grid pos or change it to the lowest other value possible
				grid[i][j] = Math.max(grid[i][j],  Math.min(maxHorizontal, maxVertical) );
			}
		}
	}
	
	public int getMax(int[] array) {
		int max =  0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
	
	public int getMaxAlternative(int[][] grid, int j) {
		int max =  0;
		for(int i = 0; i < grid.length; i++) {
			if(grid[i][j] > max) {
				max = grid[i][j];
			}
		}
		return max;
	}
	
	public int totalGrid(int[][] grid) {
		int total = 0;
		for (int i = 0; i < grid.length; i++) {
			int length = grid[i].length;
			for (int j = 0; j < length; j++) {
				total += grid[i][j];
			}
		}
		return total;
	}
	
}