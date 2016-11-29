
public class Node {
	private Node left;
	private Node right;
	private int val;
	private Character ch;

	//Leaf
	public Node(int v, Character ch){
		this.val = v;
		this.left = null;
		this.right = null;
		this.ch = ch;
	}

	//Internal 
	public Node(int v, Node l, Node r){
		this.val = v;
		this.left = l;
		this.right = r;
		this.ch = null;
	}
	
	public int getVal(){
		return this.val;
	}
	
	public Character getChar(){
		return this.ch;
	}
	
	public Node getLeft(){
		return this.left;
	}
	
	public Node getRight(){
		return this.right;
	}
	

}
