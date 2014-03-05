import java.util.ArrayList;


public class Translator {

	public static void main(String[] args){
		for(int i = 0; i < args.length; i++){
			if(!args[i].contains("-")){
				ArrayList<Tokens> tokens = LexAn.lex(args[i]);
				//printTree(tokens);
				Parser.parse(tokens);
				ParseTree tree = new ParseTree();
				int pos = 0;
				while(pos < tokens.size()){
					ArrayList<Tokens> lineToks = new ArrayList<Tokens>();
					int curline = tokens.get(pos).lineNum;
					while(tokens.size()>pos&&tokens.get(pos).lineNum==curline){
						lineToks.add(tokens.get(pos));
						pos++;
					}
					
					Nodes root = tree.makeTree(lineToks, 0);
					tree.postTraverse(root);
					System.out.println();
					lineToks.clear();
				}
				//lineToks.clear();
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
