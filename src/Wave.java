import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class describes the Tree Wave Algorithm.
 * 
 * @author Alexander Chetcuti
 */
public class Wave {
	
	//A static final integer to multiply the number of iterations the algorithm with run.
	private static final int ITERATION_MULTIPLIER = 100;

	/**
	 * Constructor of class Wave.
	 */
	public Wave() { }
	
	/**
	 * A method that simulates a Tree Wave Algorithm Process using a matrix of integers to store tokens
	 * 
	 * @param nodesArr An array of nodes that should be arranged in a binary tree format using Node class methods
	 */
	public static void algorithm(List<Node> nodesArr)
	{
		Helpers.printTree(nodesArr);
		
		System.out.println("");
		System.out.println("---------- Algorithm Actions -----------");
		
		//Number of processes in the simulated tree
		int size = nodesArr.size();
		
		//Random number
		Random rand = new Random();
		
		//initialise matrix. 0 = Not a neighbour, 1 = Neighbour, 2 = Received token
		int[][] tokenMatrix = new int[size][size];
		tokenMatrix = Helpers.setNeighboursInMatrix(tokenMatrix, nodesArr);
		
		//Counting Iterations
		int iCount = 0;
		
		//Iterate for 100 * N
		for (int k = 0; k < ITERATION_MULTIPLIER * size; k++) {
			
			//Choosing R <= N nodes randomly
			int countNodes = rand.nextInt(size) + 1;
			
			//Array holds information of which node has already executed the algorithm in this process.
			int[] nodesAlreadyExecuted = new int[countNodes];
			Arrays.fill(nodesAlreadyExecuted, -1);
			
			//Iterate one for every node to be chosen
			//boolean for action taken
			boolean actionTaken = false;
			for (int i = 0; i < countNodes; i++) 
			{
				
				//Selecting a node that hasn't already run the algorithm in this Iteration of i
				int process = Helpers.getNextInteger(nodesAlreadyExecuted, rand.nextInt(size), size);
				nodesAlreadyExecuted[i] = process;
				
				Node node = nodesArr.get(process);
				Iterator<Node> tokenBuffer = node.getTokenBuffer().iterator();
				
				//if neigh = 1, then we set silent neigh as that neigh, (either we identified a leaf)
				if (node.getNeighbours().size() == 1) {
					node.setSilentNeighbour(nodesArr.get(node.getNeighbours().get(0).getValue()));
				}
				
				if (Helpers.countPendingTokens(tokenMatrix[process]) > 0) {
					//Get the row of current node from matrix to receive any incoming messages and checking if its the last 
					while (tokenBuffer.hasNext() && node.getSilentNeighbour() == null) {
						Node tokenReceived = tokenBuffer.next();
						tokenMatrix[process][tokenReceived.getValue()] = 2;
		
						System.out.println(process + " received token from neighbour " + tokenReceived.getValue());
						
						//check for silent neighbour
						int silentNeighValue = Helpers.getSilentNeighbour(tokenMatrix[process]);
						if (silentNeighValue > -1) {
							Node silentNeigh = nodesArr.get(silentNeighValue);
							node.setSilentNeighbour(silentNeigh);
						}
						
						tokenBuffer.remove();
						actionTaken = true;
					}
					
					//Check if current node has a silent neighbour to continue algorithm else no action is taken
					if (node.getSilentNeighbour() != null) {
						Node silentNeigh = nodesArr.get(node.getSilentNeighbour().getValue());
						if (!node.sentTokenToSilentNeigh()) { //send once
							node.setSentTokenToSilentNeigh();
							silentNeigh.addTokenBuffer(node);
							System.out.println(process + " sent token to silent neighbour " + silentNeigh.getValue());
							actionTaken = true;
						}
						
						//If silent neighbour sent a token back receive the token and decide
						if (tokenBuffer.hasNext()) //Silent neighbour sent a token
						{
							Node tokenReceived = tokenBuffer.next();
							if (tokenReceived.equals(node.getSilentNeighbour())) {
								tokenMatrix[process][tokenReceived.getValue()] = 2;
								tokenBuffer.remove();
								System.out.println(process + " received token from silent neighbour " + node.getSilentNeighbour().getValue());
								
								System.out.println(process + " decided");
								actionTaken = true;
							}
						}
						
						//Diffusion part omitted
					}
				}
			}
			
			if (actionTaken) {
				iCount++;
				System.out.println("");
				System.out.println("-------- Round " + iCount + " just finished ---------");
				System.out.println("");
			}
		}
		
		System.out.println("Number of rounds: " + iCount);

		//Print matrix result for confirmation purposes.
		Helpers.printMatrix(tokenMatrix);
	}

}
