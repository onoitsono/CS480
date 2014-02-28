import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseTree {
	public static Nodes root;

	public Nodes makeTree(ArrayList<Tokens> tokens, int inpos){
		int pos=inpos;
		//while(pos < tokens.size()){
			root = recursive(tokens, pos);
			
		//}
		return root;
	}



	private List<String> singleOps = Arrays.asList("not","sin","cos","tan","exp","not invert", "expression");



	public Nodes recursive(ArrayList<Tokens> tokens, int pos){
		if(tokens.size()<=pos)
			System.exit(0);
		Nodes node=new Nodes(tokens.get(++pos));
		if(tokens.get(++pos).tValue.equalsIgnoreCase("[")){
			makeLeft(node, tokens, pos);
			pos = node.lchild.pos+1;
		}else{
			node.addL(tokens.get(pos));
		}
		++pos;
		if(pos < tokens.size()){
			if( tokens.get(pos).tValue.equalsIgnoreCase("[")){
				makeRight(node, tokens, pos);
				pos = node.rchild.pos+1;
			}else if(singleOps.contains(node.value.tValue) || singleOps.contains(node.value.tType)){
				pos++;
			}else{
				node.addR(tokens.get(pos));
			}
		}
		node.pos = pos;
		return node;
	}

	public void makeLeft(Nodes node, ArrayList<Tokens> tokens, int pos){
		node.lchild=recursive(tokens, pos);
		node.lchild.parent = node;
	}

	public void makeRight(Nodes node, ArrayList<Tokens> tokens, int pos){
		node.rchild=recursive(tokens, pos);
		node.rchild.parent = node;
	}

	public void postTraverse(Nodes root){
		Nodes current = root;
		if(current.lchild!=null){
			postTraverse(current.lchild);
		}
		if(current.rchild!=null){
			postTraverse(current.rchild);
		}
		if(current.lchild==null){
			current.primType=current.value.tType;
		}else if(current.rchild==null){
			current.primType=current.lchild.primType;
		}else if(current.lchild.primType.equalsIgnoreCase(current.rchild.primType)){
			current.primType=current.lchild.primType;
		}else if((current.lchild.primType.equalsIgnoreCase("real")&&current.rchild.primType.equalsIgnoreCase("integer"))||
				(current.rchild.primType.equalsIgnoreCase("real")&&current.lchild.primType.equalsIgnoreCase("integer"))){
			current.primType="real";
			System.out.print("s>f ");
		}else {
			System.err.println("Error encountered in post-traversal of tree.");
			System.exit(-1);
		}
		String prefix = "";
		String postfix = "";
		if(!current.primType.equalsIgnoreCase("real")&&current.value.tType.equalsIgnoreCase("realop")){
			System.err.println("Type mismatch");
			System.exit(-1);
		}
		if(current.value.tType.equalsIgnoreCase("expression")){
			if(current.lchild.primType.equalsIgnoreCase("real")){
				if(current.lchild.value.tType.equalsIgnoreCase("relation"))
					current.value.tValue=".";
				else
				current.value.tValue = "f.";
			}else if(current.lchild.primType.equalsIgnoreCase("string")){
				current.value.tValue = "type";
			}else{
				current.value.tValue = ".";
			}
		}else if(current.primType.equalsIgnoreCase("real")){
			if(current.value.tType.equalsIgnoreCase("operator")||
					current.value.tType.equalsIgnoreCase("realop")||
					current.value.tType.equalsIgnoreCase("relation")){
				prefix = "f";
			}else
				postfix = "e";
		}else if(current.primType.equalsIgnoreCase("string")){
			if(current.value.tType.equalsIgnoreCase("string")){
				prefix = "s\" ";
				postfix = "\"";
			}else
				prefix = "s";
		}
		
		System.out.print(prefix + current.value.tValue + postfix + " ");
		return;
	}			
}



