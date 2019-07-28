

import java.util.*;


public class OpdrachtB4
{

	public static void main(String[] args)
	{
		Rectangles r = new Rectangles();
		r.execute();
	}
}

class Rectangles {

	List<FixedAreaRectangle> fixedRectangles;

	FixedAreaRectangle[][] grid;

	List<ColorSolution> possibleColorGrids;

	public void execute() {

		grid = new FixedAreaRectangle[7][7];

		fixedRectangles = new ArrayList<FixedAreaRectangle>();
		addFixedRectangles();
		// big ones first
		fixedRectangles.sort(new Comparator<FixedAreaRectangle>() {
			@Override
			public int compare(FixedAreaRectangle o1, FixedAreaRectangle o2) {
				return o2.area - o1.area;
			}
		});

		tFar = 0;
		tVariationIndex = 0;
		tRectX = firstX(fixedRectangles.get(tFar), tVariationIndex);
		tRectY = firstY(fixedRectangles.get(tFar), tVariationIndex);

		while(!tryFill()) {

		}

		System.out.println("Final Grid");
		printGrid();

		//		fixedRectangles.sort(new Comparator<FixedAreaRectangle>() {
		//			@Override
		//			public int compare(FixedAreaRectangle o1, FixedAreaRectangle o2) {
		//				return o1.area - o2.area;
		//			}
		//		});

		int amountColors = 4;

		possibleColorGrids = new ArrayList<>();

		//		
		//		printColors();
		//		
		//		fixedRectangles.get(0).currentColor = 1;
		//		
		//		printColors();
		//		
		//		System.out.println(canRectangleBeColored(fixedRectangles.get(1), 2));
		//		fixedRectangles.get(1).currentColor = 2;
		//		printColors();
		//		System.out.println(canRectangleBeColored(fixedRectangles.get(1), 2));
		//		
		//		


		System.out.println("Trying coloring with " + amountColors + " colors");

		yFar = 0;
		yColor = 0;
		while(!tryColor(amountColors)) {

		}

		for(ColorSolution sol : possibleColorGrids) {
			sol.print();
			
			sol.countColors();
			sol.calculateMostCommonColor();
		}
		
		
		ColorSolution bestSolution = null;
		
		for(ColorSolution sol : possibleColorGrids) {
			if(bestSolution == null) {
				bestSolution = sol;
			} else if(sol.amountSameColored > bestSolution.amountSameColored) {
				bestSolution = sol;
			}
		}
		
		System.out.println("Best Solution: ");
		bestSolution.print();
		System.out.println("Most common color: " + bestSolution.bestColor + " , sameColored: " + bestSolution.amountSameColored);
		
		

		System.out.println("Color solutions: " + possibleColorGrids.size());

	}

	int tFar;
	int tVariationIndex;
	int tRectX;
	int tRectY;

	boolean ignorePosCheck = false;
	boolean ignoreColCheck = false;

	int yFar;
	int yColor;

	public boolean tryColor(int amountColors) {

		FixedAreaRectangle far = fixedRectangles.get(yFar);

		//	System.out.println(canRectangleBeColored(far, yColor));

		if(!ignoreColCheck && canRectangleBeColored(far, yColor)) {
			//		System.out.println("Can be colored");
			far.currentColor = yColor;


			if(yFar >= fixedRectangles.size() - 1) {

				int[][] gridCopy = new int[grid.length][];
				for(int x = 0; x < grid.length; x++) {
					int n = grid[x].length;
					gridCopy[x] = new int[n];
					for (int y = 0; y < n; y++) {
						gridCopy[x][y] = grid[x][y].currentColor;
					}
				}
				possibleColorGrids.add(new ColorSolution(gridCopy, amountColors));

				ignoreColCheck = true;

				return false;

			}

			yFar++;
			yColor = 0;

			return false;
		} else {
			ignoreColCheck = false;

			yColor ++;
			if(yColor < amountColors) {
				return false;
			}

			//	System.out.println(yFar);

			if(yFar <= 0) {
				System.out.println("Too little colors!");
				return true;
			}


			//printColors();

			yFar --;
			far = fixedRectangles.get(yFar);
			yColor = far.currentColor;

			// if no other place is found, it will make sure no colors are left behind after rolling back
			far.currentColor = -1;
			ignoreColCheck = true;

			//printColors();

			return false;

		}

	}

	public boolean canRectangleBeColored(FixedAreaRectangle far, int color) {
		//	System.out.println("Trying " + far.area + " with color " + color);
		int rectX = far.currentRectX;
		int rectY = far.currentRectY;
		int[] variation = far.possibleRectangles[far.currentVariationIndex];
		int rectWidth = variation[0];
		int rectHeight = variation[1];
		//		for(int x = rectX; x < rectX + rectWidth; x++) {
		//			for(int y = rectY; y < rectY + rectHeight; y++) {
		//				if(
		//						(x == rectX && y == rectY) ||
		//						(x == rectX && y == rectY + rectHeight - 1) ||
		//						(x == rectX + rectWidth -1 && y == rectY) ||
		//						(x == rectX + rectWidth -1 && y == rectY + rectHeight - 1)
		//				) {
		//					continue;
		//				}
		//				if(grid[x][y])
		//			}
		//		}


		int yTop =  rectY-1;
		int yBottom = rectY + rectHeight;
		if(yTop >= 0) {
			for(int x = rectX; x < rectX+rectWidth; x++) {
				if(grid[x][yTop].currentColor == color) {
					//System.out.println(x + ", " + startY + " not possible");
					return false;
				}
			}
		}
		if(yBottom < 7) {
			for(int x = rectX; x < rectX+rectWidth; x++) {
				if(grid[x][yBottom].currentColor == color) {
					//System.out.println(x + ", " + (startY+height-1) + " not possible");
					return false;
				}
			}
		}

		int xLeft =  rectX-1;
		int xRight  = rectX + rectWidth;
		if(xLeft >= 0) {
			for(int y = rectY; y < rectY+rectHeight; y++) {
				if(grid[xLeft][y].currentColor == color) {
					//System.out.println(x + ", " + startY + " not possible");
					return false;
				}
			}
		}
		if(xRight < 7) {
			for(int y = rectY; y < rectY+rectHeight; y++) {
				if(grid[xRight][y].currentColor == color) {
					//System.out.println(x + ", " + (startY+height-1) + " not possible");
					return false;
				}
			}
		}

		return true;
	}


	public boolean tryFill() {

		FixedAreaRectangle far = fixedRectangles.get(tFar);

		if(!ignorePosCheck && doesRectangleVariationFit(far, tVariationIndex, tRectX, tRectY)) {


			addRectangleVariation(far, tVariationIndex, tRectX, tRectY);

			printGrid();

			far.currentVariationIndex = tVariationIndex;
			far.currentRectX = tRectX;
			far.currentRectY = tRectY;

			if(tFar >= fixedRectangles.size()) {
				return true;
			}

			tFar++;

			if(tFar >= fixedRectangles.size()) {
				return true;
			}

			far = fixedRectangles.get(tFar);

			tVariationIndex = 0;
			tRectX = firstX(far, tVariationIndex);
			tRectY = firstY(far, tVariationIndex);

			//	System.out.println("New: " +  fixedRectangles.get(tFar).area + " " + tVariationIndex + " " + tRectX + " " + tRectY);

			return false;

		} else {
			ignorePosCheck = false;

			int[] variation = far.possibleRectangles[tVariationIndex];
			int rectWidth = variation[0];
			int rectHeight = variation[1];

			tRectX ++;
			if(tRectX <= far.numberX && tRectX + rectWidth <= 7) {
				return false;
			}

			tRectX = firstX(far, tVariationIndex);

			tRectY ++;
			if(tRectY <= far.numberY && tRectY + rectHeight <= 7) {
				return false;
			}

			tVariationIndex ++;
			if(tVariationIndex < far.possibleRectangles.length) {
				tRectX = firstX(far, tVariationIndex);
				tRectY = firstY(far, tVariationIndex);
				return false;
			}

			if(tFar <= 0) {
				printGrid();
				System.out.println("Wtf");
				return true;
			}

			System.out.println("Rolling back from " + far.area + " to " + fixedRectangles.get(tFar-1).area);

			tFar --;

			far = fixedRectangles.get(tFar);
			removeRectangleVariation(far, far.currentVariationIndex, far.currentRectX, far.currentRectY);
			tVariationIndex = far.currentVariationIndex;
			tRectX = far.currentRectX;
			tRectY = far.currentRectY;

			//			far.currentVariationIndex = 0;
			//			far.currentRectX = 0;
			//			far.currentRectY = 0;

			ignorePosCheck = true;

			return false;
		}


	}

	public int firstX(FixedAreaRectangle far, int variationIndex) {
		int[] variation = far.possibleRectangles[tVariationIndex];
		int rectWidth = variation[0];
		return Math.max(0, far.numberX-rectWidth+1);
	}

	public int firstY(FixedAreaRectangle far, int variationIndex) {
		int[] variation = far.possibleRectangles[tVariationIndex];
		int rectHeight = variation[1];
		return Math.max(0, far.numberY-rectHeight+1);
	}

	public void printGrid() {
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				if(grid[x][y]  != null) {
					System.out.print(grid[x][y].area + " ");
				} else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printColors() {
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				if(grid[x][y].currentColor != -1) {
					System.out.print(grid[x][y].currentColor + " ");
				} else {
					System.out.print("x ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}



	public boolean doesRectangleVariationFit(FixedAreaRectangle far, int variationIndex, int rectX, int rectY) {
		System.out.println("Trying variation " + variationIndex + " of shape " + far.area + " at " + rectX +", " + rectY);
		int[] variation = far.possibleRectangles[variationIndex];
		int rectWidth = variation[0];
		int rectHeight = variation[1];

		for(int x = rectX; x < rectX + rectWidth; x++) {
			for (int y = rectY; y < rectY + rectHeight; y++) {
				if(grid[x][y] != null && grid[x][y] != far) {
					return false;
				}
			}
		}
		return true;
	}

	public void addRectangleVariation(FixedAreaRectangle far, int variationIndex, int rectX, int rectY) {
		System.out.println("Placing variation " + variationIndex + " of shape " + far.area + " at " + rectX +", " + rectY);

		int[] variation = far.possibleRectangles[variationIndex];
		int rectWidth = variation[0];
		int rectHeight = variation[1];

		for(int x = rectX; x < rectX + rectWidth; x++) {
			for (int y = rectY; y < rectY + rectHeight; y++) {
				grid[x][y] = far;
			}
		}
	}

	public void removeRectangleVariation(FixedAreaRectangle far, int variationIndex, int rectX, int rectY) {
		System.out.println("Removing  " + far.area + " at " + rectX + "," + rectY + " (variation  " + variationIndex + ")");

		int[] variation = far.possibleRectangles[variationIndex];
		int rectWidth = variation[0];
		int rectHeight = variation[1];

		//		System.out.println("Width " + rectWidth);
		//		System.out.println("Height " + rectHeight);
		//		System.out.println("Numberx " + far.numberX + " y " + far.numberY);

		for(int x = rectX; x < rectX + rectWidth; x++) {
			for (int y = rectY; y < rectY + rectHeight; y++) {
				//				System.out.println("Well well " + x + "," + y);
				if(!(x == far.numberX && y == far.numberY)) {
					grid[x][y] = null;
				}
			}
		}
	}

	public void addFixedRectangles() {
		fixedRectangles.add(new FixedAreaRectangle(5, 0, 2));

		fixedRectangles.add(new FixedAreaRectangle(5, 1, 2));

		fixedRectangles.add(new FixedAreaRectangle(0, 2, 3));
		fixedRectangles.add(new FixedAreaRectangle(2, 2, 6));
		fixedRectangles.add(new FixedAreaRectangle(3, 2, 8));
		fixedRectangles.add(new FixedAreaRectangle(5, 2, 2));
		fixedRectangles.add(new FixedAreaRectangle(6, 2, 4));

		fixedRectangles.add(new FixedAreaRectangle(1, 3, 2));

		fixedRectangles.add(new FixedAreaRectangle(5, 4, 2));

		fixedRectangles.add(new FixedAreaRectangle(1, 5, 8));

		fixedRectangles.add(new FixedAreaRectangle(0, 6, 4));
		fixedRectangles.add(new FixedAreaRectangle(5, 6, 6));

	}

	class FixedAreaRectangle {

		int area;
		int numberX;
		int numberY;

		int currentColor = -1;

		int currentVariationIndex;
		int currentRectX;
		int currentRectY;


		int[][] possibleRectangles;

		public FixedAreaRectangle(int numberX, int numberY, int area) {
			this.numberX = numberX;
			this.numberY= numberY;
			this.area = area;
			this.possibleRectangles = getPossibleRectangles(area);

			grid[numberX][numberY] = this;
		}

		@Override
		public String toString() {
			return "x: " + numberX + "  y: " + numberY + "  area: " + area; 
		}
	}

	class ColorSolution {
		int[][] colorGrid;
		int[] colorCount;
		int bestColor;
		int amountSameColored;

		public ColorSolution(int[][] colorGrid, int amountColors) {
			this.colorGrid = colorGrid;
			this.colorCount = new int[amountColors];
		}

		public void countColors() {
			for (int x = 0; x < 7; x++) {
				for (int y = 0; y < 7; y++) {
					int color = colorGrid[x][y];
					colorCount[color]++;
				}
			}
		}
		
		public void calculateMostCommonColor() {
			bestColor = -1;
			for (int i = 0; i < colorCount.length; i++) {
				if(bestColor == -1) {
					bestColor = i;
				} else {
					int currentCount = colorCount[bestColor];
					int count = colorCount[i];
					if(count > currentCount) {
						bestColor = i;
					}
				}
			}
			amountSameColored = colorCount[bestColor];
		}


		public void print() {
			for (int y = 0; y < 7; y++) {
				for (int x = 0; x < 7; x++) {
					if(colorGrid[x][y] != -1) {
						System.out.print(colorGrid[x][y] + " ");
					} else {
						System.out.print("x ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public int[][] getPossibleRectangles(int area) {
		switch(area) {

		case 2:
			return new int[][] {
				{1, 2},
				{2,1}
			};

		case 3:
			return new int[][] {
				{1, 3},
				{3, 1}
			};

		case 4:
			return new int[][] {
				{1, 4},
				{2, 2},
				{4, 1}
			};

		case 6:
			return new int[][] {
				{1, 6},
				{2, 3},
				{3, 2},
				{6, 1}
			};

		case 8:
			return new int[][] {
				// 1,8 and 8,1 will never fit
				{2, 4},
				{4, 2}
			};

		default:
			return null;

		}



	}

}

