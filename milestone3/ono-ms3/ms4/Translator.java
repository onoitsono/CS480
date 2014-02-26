import java.util.ArrayList;


public class Translator {


	public static void main(String[] args){
		ArrayList<Tokens> tokens = Lexer.lex(args);

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
	}
}
