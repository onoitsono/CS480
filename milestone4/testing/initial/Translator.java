import java.util.ArrayList;


public class Translator {

	public static void main(String[] args){
		for(int i = 0; i < args.length; i++){
			if(!args[i].contains("-")){
				ArrayList<Tokens> tokens = LexAn.lex(args[i]);
				printTree(tokens);
				Parser.parse(tokens);
			}
		}
	}
	public static void printAtDepth(String str, int depth){
		for(int i = 0; i < depth; i++){
			System.out.print("\t");
		}
		System.out.println(str);
	}
	public static void printTree(ArrayList<Tokens> tokens){
		int depth = 0;
		for(int i = 0; i < tokens.size(); i++){
			if(tokens.get(i).tType.equalsIgnoreCase("delimiter") && tokens.get(i).tValue.equalsIgnoreCase("]")){
				depth--;
			}
			printAtDepth(tokens.get(i).tValue, depth);
			if(tokens.get(i).tType.equalsIgnoreCase("delimiter") && tokens.get(i).tValue.equalsIgnoreCase("[")){
				depth++;
			}
		}
	}
}
