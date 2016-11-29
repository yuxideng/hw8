public class Grin {
	public static void main(String[] args){
		String command = args[0];
		String in = args[1];
		String out = args[2];
		if(command.equals("encode")){
			GrinEncode(in,out);
		}else if (command.equals("decode")){
			GrinDecode(in,out);
		}else{
			System.out.printf("Enter either encode or decode");
		}
	}
}
