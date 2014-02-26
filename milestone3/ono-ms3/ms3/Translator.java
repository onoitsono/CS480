import java.util.ArrayList;


public class Translator {
	
	
	public static void main(String[] args){
		ArrayList<Tokens> tokens = Lexer.lex(args);
		
		Parser.parse(tokens);
		//Hashtable<String, String[]> userSymb = Parser.parse(tokens);
		
		
		//for(int i=0; i<tokens.size(); i++){
		//	System.out.println(tokens.get(i).lineNum+", "+tokens.get(i).tType+", "+tokens.get(i).tValue);
		//}
		//System.out.println(userSymb.get("x")[0]+", "+userSymb.get("x")[1]);
	}
}
