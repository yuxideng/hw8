import java.io.IOException;

public class Grin {
	public static void main(String[] args) throws IOException{
		String command = args[0];
		String in = args[1];
		String out = args[2];
		if(command.equals("encode")){
			GrinEncoder.encode(in,out);
		}else if (command.equals("decode")){
			GrinDecoder.decode(in,out);
		}else{
			System.out.printf("Enter either encode or decode");
		}
	}
}
