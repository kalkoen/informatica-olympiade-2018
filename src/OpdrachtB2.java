

import java.util.*;


public class OpdrachtB2
{

	public static void main(String[] args)
	{
		RoundPath path = new RoundPath();
		path.execute();
	}
}

class RoundPath {

	boolean[][] grid;
	int gridWidth = 24;
	int gridHeight = 25;
	
	List<Path> paths;
	
	public void execute() {

		grid = new boolean[gridWidth][gridHeight];
		paths = new ArrayList<Path>();
			
		initializeHouses();
		
//		printGrid();
		
		explorePossiblePaths();
		
		Path optimalPath = getOptimalPath();
		
		paths.sort(new Comparator<Path>() {

			@Override
			public int compare(Path o1, Path o2) {
				return o2.totalDistance - o1.totalDistance;
			}
		});
		for(Path p : paths) {
			System.out.println(p.x + ":" + p.y + ":" + p.width + ":" + p.height + ": " + p.totalDistance + (p.width == 1 && p.height ==1 ? "USELESS" : "") );
		}
		
		int n = grid[0].length;
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < grid.length; x++) {
				int d = distancePointToPath(x, y, optimalPath);
				System.out.print((grid[x][y] ? d : "0") + " ");
			}
			System.out.println();
		}
		System.out.println();
		

		System.out.println("Optimal path: x:" + optimalPath.x + " y: " + optimalPath.y + " width:" + optimalPath.width + " height: " + optimalPath.height + " tD:" + optimalPath.totalDistance);

	}
	
	public Path getOptimalPath() {
		Path optimalPath = null;
		for(Path p : paths) {
			if(optimalPath == null) {
				optimalPath = p;
			} else if(p.totalDistance < optimalPath.totalDistance) {
				optimalPath = p;
			}
		}
		return optimalPath;
	}
	
	public void explorePossiblePaths() {
		for (int x = 0; x < grid.length; x++) {
			int n = grid[x].length;
			for (int y = 0; y < n; y++) {
				for (int width = 1; width < gridWidth-x; width++) {
					for (int height = 1; height < gridHeight-y; height++) {
						Path p = new Path(x, y, width, height);
						if(isPathPossible(p)) {
							p.totalDistance = calculateTotalDistance(p);
							paths.add(p);
						}
					}
				}
			}
		}
	}
	
	public int calculateTotalDistance(Path p) {
		int totalDistance = 0;
		for (int x = 0; x < grid.length; x++) {
			int n = grid[x].length;
			for (int y = 0; y < n; y++) {
				if(grid[x][y]) {
					totalDistance += distancePointToPath(x, y, p);
				}
			}
		}
		return totalDistance;
	}
	
	
	public int distancePointToPath(int x, int y, Path p) {
		
		int rectX = p.x;
		int rectY = p.y;
		int rectWidth = p.width;
		int rectHeight = p.height;
		
		
		if(isInPath(x, y, p)) {
			int dTop = y - rectY;
			int dBottom = rectY+rectHeight-1 - y;
			
			int dLeft = x - rectX;
			int dRight = rectX+rectWidth-1 - x;
			
			return Math.min(Math.min(dTop, dBottom), Math.min(dLeft, dRight));
		} else {
			
			int dX = 0;
			int dY = 0;
			
			if(!(x >= rectX && x < rectX+rectWidth-1)) {
				int dLeft = Math.abs(rectX - x);
				int dRight = Math.abs(x - (rectX+rectWidth-1));
				dX = Math.min(dLeft, dRight);
			}
			
			if(!(y >= rectY && y < rectY+rectHeight-1)) {
				int dTop = Math.abs(rectY - y);
				int dBottom = Math.abs(y - (rectY+rectHeight-1));
				dY = Math.min(dTop, dBottom);
			}
			
			return dX + dY;
			
		}
	}
	
	
	public boolean isPathPossible(Path p) {
		int startX = p.x;
		int startY = p.y;
		int width = p.width;
		int height = p.height;
		for(int x = startX; x < startX+width; x++) {
			if(grid[x][startY]) {
				//System.out.println(x + ", " + startY + " not possible");
				return false;
			}
			if(grid[x][startY+height-1]) {
				//System.out.println(x + ", " + (startY+height-1) + " not possible");
				return false;
			}
		}
		for(int y = startY; y < startY + height; y++) {
			if(grid[startX][y]) {
				//System.out.println(startX + ", " + y + " not possible");
				return false;
			}
			if(grid[startX+width-1][y]) {
				//System.out.println(startX + ", " + (y+width-1) + " not possible");
				return false;
			}
		}
		return true;
	}
	
	public boolean isInPath(int x, int y, Path p) {
		return x >= p.x && x < p.x + p.width && y >= p.y && y < p.y + p.height;
	}
	
	public void printGrid() {
		int n = grid[0].length;
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < grid.length; x++) {
				System.out.print((grid[x][y] ? "1" : "0") + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void initializeHouses() {
		
		
//		grid[3][0] = true;
//		grid[5][0] = true;
//		grid[0][1] = true;
//		grid[4][2] = true;
//		grid[2][4] = true;
//		grid[5][5] = true;
//		grid[0][7] = true;
//		grid[3][7] = true;
 		
		
		// ---------------
		
		
		grid[6][1] = true;
		
		grid[0][2] = true;
		
		grid[20][3] = true;
		grid[23][3] = true;
		
		grid[4][4] = true;
		grid[14][4] = true;
		
		grid[22][7] = true;
		
		grid[19][9] = true;
		
		grid[1][11] = true;
		grid[4][11] = true;
		grid[10][11] = true;
		
		grid[8][13] = true;
		
		grid[12][15] = true;

		grid[22][16] = true;
		
		grid[2][18] = true;
		grid[7][18] = true;
		grid[16][18] = true;
		
		grid[1][20] = true;
		
		grid[8][21] = true;
		
		grid[8][24] = true;
		
	}
	
	class Path {
		int x, y, width, height, totalDistance;
		
		public Path(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height =height;
		}
	}
}