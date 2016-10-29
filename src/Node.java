
import java.util.ArrayList;
import java.util.List;

/**
 * This class describes a Node. It holds pointers to any children, parent and silent neighbour
 * of Node.
 * 
 * @author Alexander Chetcuti
 *
 */
public class Node {
	
	//A list of nodes. Since node has a list the children could have a list of children
	private List<Node> children = null;

	private List<Node> neighbours = null;
	
	//parent of node
	private Node parent = null;
	
	//silent neighbour
	private Node silentNeigh = null;
	
	//Value of that particular node.
	private int value;

	//A token buffer used to receive tokens
	private List<Node> tokenBuffer = new ArrayList<Node>();

	private boolean sentTokenToSilentNeigh = false;
	
	private boolean sentDiffusionToken = false;
	
	/**
	 * Constructor of class node
	 * 
	 * @param val The value to be given to the node
	 */
	public Node(int value) {
		this.children = new ArrayList<>();
		this.value = value;
	}
	
	/**
	 * Adds a node to the to the children of this node, and sets parent of this node.
	 * 
	 * @param Node Is the node to be added to the list.
	 */
	public void addNode(Node node)
	{
		children.add(node);
		node.parent = this;
	}

	/**
	 * Gets the value of this node
	 * 
	 * @return An integer representing the value of the node
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Gets the children of this node.
	 * 
	 * @return returns The children of this node
	 */
	public List<Node> getChildren()
	{
		return this.children;
	}
	
	/**
	 * Gets the list of Neighbours of a node.
	 * 
	 * @return returns A list of nodes that are the neighbours of this node.
	 */
	public List<Node> getNeighbours()
	{
		if (this.neighbours == null) {
			this.neighbours = this.children;
			if (this.parent != null) {
				this.neighbours.add(this.parent);
			}
		}
		return this.neighbours;
	}
	
	/**
	 * Gets an integer array of Neighbours of a node.
	 * 
	 * @return An Integer array of nodes that are the neighbours of this node.
	 */
	public int[] getNeighboursValues()
	{
		List<Node> neighbours = this.getNeighbours();
		int[] neighValues = new int[neighbours.size()];
		int indexCount = 0;
		for (Node node : neighbours) {
			neighValues[indexCount] = node.value;
			indexCount++;
		}
		
		return neighValues;
	}
	
	/**
	 * Gets a string from an the integer array of Neighbours of a node.
	 * 
	 * @return A string of the Integer array of nodes that are the neighbours of this node.
	 */
	public String getNeighboursValuesString()
	{
		return java.util.Arrays.toString(getNeighboursValues());
	}
	
	/**
	 * Set the silent neighbour to a node.
	 * 
	 * @param node The silent neighbour to be set.
	 */
	public void setSilentNeighbour(Node node)
	{
		this.silentNeigh = node;
	}
	
	/**
	 * Get the silent neighbour of a node.
	 * 
	 * @return The silent neighbour of the node.
	 */
	public Node getSilentNeighbour()
	{
		return this.silentNeigh;
	}
	
	/**
	 * Set the sent token to silent neighbour.
	 * 
	 */
	public void setSentTokenToSilentNeigh()
	{
		this.sentTokenToSilentNeigh = true;
	}
	
	/**
	 * Checks if the diffusion token  was sent
	 * 
	 * @return True if diffusion token sent, false if not.
	 */
	public boolean sentDiffusionToken()
	{
		return this.sentDiffusionToken;
	}
	
	/**
	 * Set the sent token to silent neighbour.
	 * 
	 */
	public void setSentDiffusionToken()
	{
		this.sentDiffusionToken = true;
	}
	
	/**
	 * Checks if the token to silent neighbour was sent
	 * 
	 * @return True if token to silent neighbour sent, false if not.
	 */
	public boolean sentTokenToSilentNeigh()
	{
		return this.sentTokenToSilentNeigh;
	}
	
	
	/**
	 * Add a node to to token buffer of this node.
	 * 
	 * @param node The node to be added to the token buffer.
	 */
	public void addTokenBuffer(Node node)
	{
		this.tokenBuffer.add(node);
	}
	
	/**
	 * Gets the current list of nodes in the token buffer.
	 * 
	 * @return The current list of tokens in the buffer.
	 */
	public List<Node> getTokenBuffer()
	{
		return this.tokenBuffer;
	}

	/**
	 * Empty the token buffer for next use.
	 */
	public void resetTokenBuffer()
	{
		this.tokenBuffer = new ArrayList<Node>();
	}
 }
