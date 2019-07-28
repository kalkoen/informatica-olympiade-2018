

import java.util.*;


public class OpdrachtB1
{

	public static void main(String[] args)
	{
		Path path = new Path();
		path.execute();
	}
}

class Path {

	Point[] points;

	public void execute() {

		points = new Point[16];

		initializePoints();
//		for (int i = 0; i < points.length; i++) {
//			System.out.println(i + ": " + points[i].letter);
//		}
		
		initializeConnections();
	
		Point a = getPoint("A");
		
		for (int i = 1; i < points.length; i++) {
			beginCalculating(points[i], a);
			System.out.println(points[i].letter + " has " + points[i].friends.size() + " friends.");
		}
		
		Point maxTravelPoint = null;
		for (int i = 1; i < points.length; i++) {
			Point p = points[i];
			
			System.out.println(p.letter + " to A: " + p.shortestDistanceToA);
			
			if(maxTravelPoint == null) {
				maxTravelPoint = p;
			} else if(p.shortestDistanceToA > maxTravelPoint.shortestDistanceToA) {
				maxTravelPoint = p;
			}
		}
		
		System.out.println("Winner: ");
		System.out.println(maxTravelPoint.letter + " to A: " + maxTravelPoint.shortestDistanceToA);


	}
	
	public void beginCalculating(Point origin, Point destination) {
		calculateTravelDistance(origin, origin, 0, new ArrayList<Point>(), destination);
	}
	
	public void calculateTravelDistance(Point origin, Point from, int currentDistance, List<Point> stack, Point destination) {

		Set<Point> keys = from.friends.keySet();
		for (Point friend : keys) {
			if(stack.contains(friend)) {
				continue;
			}
			
			int addedDistance = from.friends.get(friend);
			int newDistance = currentDistance + addedDistance;
			
			if(friend == destination) {
				
				if(newDistance < origin.shortestDistanceToA) {
					origin.shortestDistanceToA = newDistance;
				}
				
			} else {
				
				List<Point> newStack = new ArrayList<Point>(stack);
				newStack.add(from);
				
				calculateTravelDistance(origin, friend, newDistance, newStack, destination);
				
			}
		
			
			
		}
	}
	
	public void initializePoints() {
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(i, getLetter(i));
		};
	}
	
	public void initializeConnections() {
		
		// horizontal
		
		connect("A", "B", 19);
		connect("B", "C", 15);
		connect("C", "D", 22);
		
		connect("E", "F", 22);
		connect("F", "G", 11);
		connect("G", "H", 7);
		
		connect("I", "J", 26);
		connect("J", "K", 27);
		connect("K", "L", 18);
		
		connect("M", "N", 15);
		connect("N", "O", 24);
		connect("O", "P", 5);
		
		// vertical
		
		connect("A", "E", 9);
		connect("E", "I", 3);
		connect("I", "M", 6);

		connect("B", "F", 10);
		connect("F", "J", 21);
		connect("J", "N", 27);
		
		connect("C", "G", 16);
		connect("G", "K", 13);
		connect("K", "O", 10);
		
		connect("D", "H", 24);
		connect("H", "L", 20);
		connect("L", "P", 12);
		
		// diagonal
		
		connect("A", "F", 21);
		connect("F", "C", 21);
		connect("C", "H", 32);
		
		connect("E", "J", 22);
		connect("J", "G", 23);
		connect("G", "L", 38);
		
		connect("I", "N", 22);
		connect("N", "K", 38);
		connect("K", "P", 10);
	}

	public void connect(String letterOne, String letterTwo, int distance) {
		Point pointOne = points[getIndex(letterOne)];
		Point pointTwo = points[getIndex(letterTwo)];
		pointOne.friends.put(pointTwo, distance);
		pointTwo.friends.put(pointOne, distance);
	}
	
	public char getIndex(String letter) {
		return (char) (letter.toCharArray()[0] - 65);
	}
	
	public String getLetter(int i) {
		return String.valueOf((char) (i + 65));
	}
	
	public Point getPoint(String letter) {
		return points[getIndex(letter)];
	}



}

class Point {
	int index;
	String letter;
	Map<Point, Integer> friends;
	
	int shortestDistanceToA = Integer.MAX_VALUE;

	public Point(int index, String letter) {
		this.index = index;
		this.letter = letter;
		friends = new HashMap<Point, Integer>();
	}

}