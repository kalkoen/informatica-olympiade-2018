

import java.util.*;


public class OpdrachtC1
{

  public static void main(String[] args)
  {
    Centroid centroid = new Centroid();
    centroid.execute();
  }
}

class Centroid {

    Node[] nodes;

    public void execute() {
    
        Scanner in = new Scanner(System.in);
        
        int n = in.nextInt();
        nodes = new Node[n];
        
        // initialize nodes
        for(int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        
        // connect nodes to form tree
        for(int i = 0; i < n-1; i++) {
            connect(in.nextInt() - 1, in.nextInt() - 1);
        }
        
        // initialize weightMaps with correct size
        for(int i = 0; i < n; i++) {
            Node node = nodes[i];
            node.initializeWeightMap();
        }
        
        // calculate lightest branch
        int minHeaviestBranch = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            Node node = nodes[i];
            node.calculateHeaviestBranch();
            if(node.heaviestBranch < minHeaviestBranch) {
                minHeaviestBranch = node.heaviestBranch;
            }
        }
        
        // print lightest
        for(int i = 0; i < n; i++) {
            Node node = nodes[i];
            if(node.heaviestBranch == minHeaviestBranch) {
                int realIndex = i + 1;
                System.out.print(realIndex + " ");
            }
        }
    
        in.close();
    
    }
    
    public void connect(int indexOne, int indexTwo) {
        Node nodeOne = nodes[indexOne];
        Node nodeTwo = nodes[indexTwo];
        nodeOne.friends.add(nodeTwo);
        nodeTwo.friends.add(nodeOne);
    }
  
  
  
}

class Node {
	int index;
  	List<Node> friends;
  	Map<Node, Integer> weights;
  	int heaviestBranch = -1;
  
  	public Node(int index) {
      	this.index = index;
  		friends = new ArrayList<Node>();
    }
  
  	public void initializeWeightMap() {
    	weights = new HashMap<Node, Integer>(friends.size());
    }
  
  	public int calculateWeight(Node from) {
  	    if(weights.containsKey(from)) {
  	        return weights.get(from);
  	    }
  	    int weight = 1;
  	    for(Node friend : friends) {
  	        if(friend == from) {continue;}
  	        weight += 1;
  	        weight += friend.calculateWeight(this);
  	    }
  	    weights.put(from, weight);
  	    return weight;
  	}
  	
  	public void calculateHeaviestBranch() {
  	    for(Node friend : friends) {
  	        int weight = friend.calculateWeight(this);
  	        if(weight > heaviestBranch) {
  	            heaviestBranch = weight;
  	        }
  	    }
  	}

}