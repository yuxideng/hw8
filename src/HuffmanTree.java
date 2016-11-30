import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
	//private Map<Character,String> m;
	//TODO make private
	public Node root;

	public HuffmanTree(Map<Short, Integer> map){
		map.put((short) 256, 1);

		PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>(){
			public int compare(Node n1,Node n2){
				return n1.getVal() - n2.getVal();
			}
		});

		for (Map.Entry<Short, Integer> entry : map.entrySet()) {
			Node n = new Node(entry.getValue(), (char) (int) entry.getKey());
			//System.out.println("Entering: " + n.getChar() + " => " + n.getVal());
			queue.add(n);
		}

		while (queue.size() >= 2) {
			Node first = queue.poll();
			Node second = queue.poll();
			//System.out.println("1: " + first.getChar() + " => " + first.getVal());
			//System.out.println("2: " + second.getChar() + " => " + second.getVal());
			Node addition = new Node(first.getVal()+second.getVal(),first,second);
			queue.add(addition);
		}

		//System.out.println(queue.poll().getVal());
		this.root = queue.poll(); 
	}

	//TODO: test
	private static Map<Character, String> makeHuffmanChart(Node node, String code, Map<Character,String> map) {
		if (node.getLeft() == null && node.getRight() == null) {
			map.put(node.getChar(), code);
			return map;
		} else if (node.getLeft() == null) {
			return makeHuffmanChart(node.getRight(), code + "1", map);
		} else if (node.getRight() == null){
			return makeHuffmanChart(node.getLeft(), code + "0", map);
		} else {
			Map<Character, String> temp = makeHuffmanChart(node.getLeft(), code + "0", map);
			return makeHuffmanChart(node.getRight(), code + "1", temp);
		}
	}

	public void encode(BitInputStream in, BitOutputStream out){

		//TODO: test
		Map<Character, String> map = new HashMap<Character, String>();
		map = makeHuffmanChart(this.root, "", map);
		while(in.hasBits()){
			char c = (char) in.readBits(8);
			String bits = map.get(c); //when c is 'b' bits = "00"
			for (int i = 0; i < bits.length(); i++) {
				out.writeBit(((int) bits.charAt(i)) - 48);
			}
		}
		String bits = map.get((char) 256);
		for (int i = 0; i < bits.length(); i++) {
			out.writeBit(((int) bits.charAt(i)) - 48);
		}

	}

	//TODO: check out what happens with the EOF character
	//TODO: also test
	public void decode(BitInputStream in, BitOutputStream out) {
		while(in.hasBits()){
			Node cur = this.root;
			while(cur.getLeft() != null || cur.getRight() != null){
				if( in.readBit() == 0){
					cur = cur.getLeft();
				}else{
					cur = cur.getRight();
				}
			}

			if ((int) cur.getChar() == 256) {
				break;
			} else {
				out.writeBits( (int) cur.getChar(), 8);
			}
		}
	}

	public static void main(String[] args) throws IOException{
		Map<Short, Integer> map = new HashMap<Short, Integer>();
		map.put((short)'z', 1);
		map.put((short)256, 1);
		map.put((short)' ', 2);
		map.put((short)'b', 2);
		map.put((short)'a', 3);
		HuffmanTree tree = new HuffmanTree(map);
		System.out.println((int) '0');
		//System.out.println(tree.root.getLeft().getRight().getChar());
		Map<Character, String> result = new HashMap<Character, String>();
		makeHuffmanChart(tree.root,"", result);
		//System.out.println(result.get('b'));
		BitInputStream origin = new BitInputStream("/home/dengyuxi/workspace/hw8/src/origin.txt");
		//BitOutputStream decoded = new BitOutputStream("/home/dengyuxi/workspace/hw8/src/decoded.txt");
		BitOutputStream encoded = new BitOutputStream("/home/dengyuxi/workspace/hw8/src/encoded.txt");
		//BitInputStream encodedIn = new BitInputStream("/home/dengyuxi/workspace/hw8/src/encoded.txt");
		tree.encode(origin, encoded);
		//tree.decode(encodedIn, decoded);
	
	}
}
