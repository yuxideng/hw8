public class Grin {
	public static void main(String[] args){
		String command = args[2];
		String in = args[3];
		String out = args[4];
		if(command.equals("encode")){
			encode(in,out);
		}else if (command.equals("decode")){
			decode(in,out);
		}else{
			System.out.printf("Enter either encode or decode");
		}
	}
}
