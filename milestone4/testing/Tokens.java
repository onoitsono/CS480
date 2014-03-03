
public class Tokens {
	public int lineNum;
	public String tValue;
	public String tType;

	public Tokens(int myLine, String myType, String myValue){
		this.lineNum = myLine;
		this.tType = myType;
		this.tValue = myValue;
	}
	public String toString(){
		return Integer.toString(lineNum) + ", " + tType + ", " + tValue;
	}
}
