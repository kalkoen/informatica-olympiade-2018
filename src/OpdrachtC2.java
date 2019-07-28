import java.util.Scanner;

public class OpdrachtC2 {

	public static void main(String[] args) {
		Puzzle p = new Puzzle();
		p.execute();

	}
	
	
}


class Puzzle {

	Shape[] shapes;
	
	int[][] grid;
	
	int i = 0;
	
	public void execute() {
	
		Scanner in = new Scanner(System.in);
		int zeroSpot = in.nextInt();
		in.close();
		
//		long startNano = System.nanoTime();
		
		// ------------------------
		
		initializeShapes();
		
		grid = new int[7][7];
		for (int i = 0; i < grid.length; i++) {
			int n = grid[i].length;
			for (int j = 0; j < n; j++) {
				grid[i][j] = -1;
			}
		}
		
		

		zeroSpot--;
		grid[zeroSpot % 7][(int) Math.floor(zeroSpot/7)] = 0;
		
		while(true) {
			if(tryFill()) {
				break;
			}
		}
		
		printGrid();
		
		System.out.println(i);

		// ------------------
		
//		long durationNano = System.nanoTime()-startNano;
//		double seconds = durationNano / (double) (1000000000);
		//System.out.println("Finished, took " + seconds + " seconds");
	}
	
	public void printGrid() {
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				System.out.print(grid[x][y] + "");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	int tShape = 1;
	int tVariation = 0;
	int tX = 0;
	int tY = 0;
	boolean ignorePosCheck = false;
	
	public boolean tryFill() {
		i++;
		
		Shape s = shapes[tShape];
		
		if(tryVariationAtPosition(s, tVariation, tX, tY) && !ignorePosCheck) {
			addVariation(s, tVariation, tX, tY);
			
			s.currentVariation = tVariation;
			s.currentX = tX;
			s.currentY = tY;

			if(tShape >= 9) {
				return true;
			}

			tShape++;
			tVariation = 0;
			tX = 0;
			tY = 0;
			
			return false;
		} else {
			ignorePosCheck = false;
			boolean[][] variation = s.variations[tVariation];
			int width = variation.length;
			int height = variation[0].length;
			
			tX++;
			
			if(tX <= 7-width) {
				return false;
			}
			
			tX = 0;
			tY++;
			
			if(tY <= 7-height) {
				return false;
			}
		
			tY = 0;
			tVariation++;
			
			if(tVariation < 4) {
				return false;
			}

			if(tShape <= 1) {
				printGrid();
				System.out.println("Wtf");
				return true;
			}
			
			tShape --;

			s = shapes[tShape];
			
			removeVariation(s, s.currentVariation, s.currentX, s.currentY);
			tVariation = s.currentVariation;
			tX = s.currentX;
			tY = s.currentY;
			
			s.currentVariation = 0;
			s.currentX = 0;
			s.currentY = 0;
			
			ignorePosCheck = true;
			
			return false;
		}
	}
	
	public boolean tryVariationAtPosition(Shape s, int index, int x, int y) {
		boolean[][] variation = s.variations[index];
		for (int i = 0; i < variation.length; i++) {
			int n = variation[i].length;
			for (int j = 0; j < n; j++) {
				boolean b = variation[i][j];
				if(b==false) {
					continue;
				}
				if(grid[x+i][y+j] != -1) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void addVariation(Shape s, int index, int x, int y) {
		boolean[][] variation = s.variations[index];
		for (int i = 0; i < variation.length; i++) {
			int n = variation[i].length;
			for (int j = 0; j < n; j++) {
				boolean b = variation[i][j];
				if(b == true) {
					grid[x + i][y + j] = s.index;
				}
			}
		}
	}
	public void removeVariation(Shape s, int index, int x, int y) {
		boolean[][] variation = s.variations[index];
		for (int i = 0; i < variation.length; i++) {
			int n = variation[i].length;
			for (int j = 0; j < n; j++) {
				boolean b = variation[i][j];
				if(b == true) {
					grid[x + i][y + j] = -1;
				}
			}
		}
	}
	
	public void initializeShapes() {
		shapes = new Shape[10];
		
		shapes[0] = new Shape(0, new boolean[][] {{true}});
		
		shapes[1] = new Shape(2, new boolean[][] 
			{
			{false, true, true, false},
			{true, true, true, false},
			{false, true, true, true}
			});
		
		shapes[2] = new Shape(5, new boolean[][] 
				{
				{true, true, false},
				{true, true, true},
				{true, true, false}
				});
		
		shapes[3] = new Shape(6, new boolean[][] 
				{
				{true, true, true},
				{false, true, true},
				{false,false, true}
				});
		
		shapes[4] = new Shape(7, new boolean[][] 
				{
				{true, true, false},
				{false, true, true},
				{false, false, true}
				});
		
		shapes[5] = new Shape(3, new boolean[][] 
				{
				{false, true},
				{true, true},
				{true, true},
				{false, true}
				});
		
		shapes[6] = new Shape(4, new boolean[][] 
				{
				{true, true, true, true},
				{false, false, true, false},
				});
		
		shapes[7] = new Shape(1, new boolean[][] 
				{
				{true, true, true, true, true},
				});
		
		shapes[8] = new Shape(8, new boolean[][] 
				{
				{true, false},
				{true, true},
				{true, false},
				});
		
		shapes[9] = new Shape(9, new boolean[][] 
				{
				{true, true},
				});
	}
	
	
	
	class Shape {
		
		int index;
		// grid, where a 'true' value means it's filled in
		boolean[][][] variations;
		
		int currentVariation = 0;
		int currentX = 0;
		int currentY = 0;
		
		public Shape(int index, boolean[][] baseShape) {
			this.index = index;
			
			variations = new boolean[4][][];
			// Flip x and yi
			baseShape = transposeArray(baseShape, baseShape[0].length);
			variations[0] = baseShape;
			variations[1] = rotate90(variations[0]);
			variations[2] = rotate90(variations[1]);
			variations[3] = rotate90(variations[2]);
		}
		
		boolean[][] rotate90(boolean[][] array) {
			int n = array[0].length;
			return reverseArray(transposeArray(array, n));
		}
		
		boolean[][] transposeArray(boolean[][] array, int n) {
			boolean[][] transposed = new boolean[n][];
			
			// 1 1
			// 0 0
			// 1 0
			
			// 1 0 1
			// 1 0 0
			
			
			
			for (int i = 0; i < transposed.length; i++) {
				transposed[i] = new boolean[array.length];
				for (int j = 0; j < array.length; j++) {
					transposed[i][j] = array[j][i];
				}
			}
			return transposed;
		}
		
		boolean[][] reverseArray(boolean[][] array) {
			boolean[][] reversed = new boolean[array.length][];
			for (int i = 0; i < reversed.length; i++) {
				int n = array[i].length;
				reversed[i] = new boolean[n];
				for (int j = 0; j < n; j++) {
					reversed[i][j] = array[i][n-1-j];
				}
			}
			return reversed;
		}
		
	}
	
	
	
}



