import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrinEncoder {

	public static Map<Short, Integer> createFrequencyMap(String file) throws IOException {
		BitInputStream in = new BitInputStream(file);
		Map<Short,Integer> map = new HashMap<Short,Integer>();
		while(in.hasBits()){
			short k = (short) in.readBits(8);
			if(map.containsKey(k)){
				map.put(k, map.get(k)+1);
			}else{
				map.put(k, 1);
			}
		}
		in.close();
		return map;
	}
	
	public static void encode(String infile, String outfile) throws IOException{
		
		Map<Short,Integer> freqMap = createFrequencyMap(infile);
		BitInputStream in = new BitInputStream(infile);
		BitOutputStream out = new BitOutputStream(outfile);
		out.writeBits(0x736,32);
		out.writeBits(freqMap.size(), 32);
		for(Map.Entry<Short,Integer> entry: freqMap.entrySet()){
			out.writeBits(entry.getKey(), 16);
			out.writeBits(entry.getValue(), 32);
		}
		
		HuffmanTree tree = new HuffmanTree(freqMap);
		tree.encode(in, out);
		in.close();
		out.close();
	}
}
