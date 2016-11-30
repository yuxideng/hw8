import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrinDecoder {

	public static void decode(String infile, String outfile) throws IOException{
		BitInputStream in = new BitInputStream(infile);
		BitOutputStream out = new BitOutputStream(outfile);
		if(in.readBits(32) == 0x736){
			Map<Short, Integer> freqMap = new HashMap<Short, Integer>();
			int count = in.readBits(32);
			for(int i = 0; i < count; i++){
				freqMap.put((short)in.readBits(16), in.readBits(32));
			}
			HuffmanTree tree = new HuffmanTree(freqMap);
			tree.decode(in,out);
		} else {
			throw new IllegalArgumentException();
		}
		in.close();
		out.close();
	}
}
