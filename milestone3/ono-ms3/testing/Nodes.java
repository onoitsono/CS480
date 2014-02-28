
public class Nodes {
	public Tokens value;
	public Nodes rchild;
	public Nodes lchild;
	public Nodes parent;
	public int pos;
	public String primType;
	
	public Nodes(Tokens input){
		value=input;
		rchild=null;
		lchild=null;
		parent = null;
		primType = null;
	}
	
	public void addL(Tokens left){
		this.lchild=new Nodes(left);
		this.lchild.parent = this;
	}
	
	public void addR(Tokens right){
		this.rchild=new Nodes(right);
		this.rchild.parent = this;
	}
}
