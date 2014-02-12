import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;


public class LexAn {
    private static ArrayList<String> ibtldata = new ArrayList<String>();
    private static String[][] chars, lexemes;
    private static ArrayList<String> tokens = new ArrayList<String>();
    private static Hashtable<String, String> symbTable = new Hashtable<String, String>();
	
    public static void main(String[] args) {
	String a;
	readFile(args[0]);
	getchars();
	lexemeGroup();
	fillSym();
	/*for(int i=0; i<chars.length; i++){
	  for(int j=0; j<200; j++){
	  if(chars[i][j]!=null){
	  System.out.println(chars[i][j]);
	  }
	  }
	  }*/
	for(int i=0; i<lexemes.length; i++){
	    for(int j=0; j<50; j++){
		if(lexemes[i][j]!=null){
		    if(fetchSym(lexemes[i][j])!=null){
			a = lexemes[i][j]+", "+fetchSym(lexemes[i][j]);
			tokens.add(a);
		    }else if(lexemes[i][j].matches("[+-]?[0-9]+")){
			a = lexemes[i][j]+", integer";
			tokens.add(a);
		    }else if(lexemes[i][j].matches("[+/-/*//%/^]")){
			a = lexemes[i][j]+", operator";
			tokens.add(a);
		    }else if(lexemes[i][j].matches("[=<]")){
			a = lexemes[i][j]+", relation";
			tokens.add(a);
		    }else if(lexemes[i][j].matches("(([+-]?[0-9]+[.]?[0-9]*)|([+-]?[.][0-9]+))")){
			a = lexemes[i][j]+", real";
			tokens.add(a);
		    }else if(lexemes[i][j].matches("[()]")){
			a = lexemes[i][j]+", delimiter";
			tokens.add(a);
		    }else if(lexemes[i][j].matches("(([+-]?[0-9]+[.]?[0-9]*)|([+-]?[.][0-9]+))e(([+-]?[0-9]+[.]?[0-9]*)|([+-]?[.][0-9]+))")){ //1.e.2 
			a = lexemes[i][j]+", real";
			tokens.add(a);
		    }else{
			a = lexemes[i][j]+", string";
			tokens.add(a);
		    }
							
		}
	    }
	}
	for(int i=0; i<tokens.size(); i++){
	    System.out.println(tokens.get(i));
	}
		
    }
	
    public static void fillSym(){
	symbTable.put("true", "boolean");
	symbTable.put("false", "boolean");
	symbTable.put("and", "boolop");
	symbTable.put("or", "boolop");
	symbTable.put("not", "boolop");
	symbTable.put("iff", "boolop");
	symbTable.put("logn", "realop");
	symbTable.put("e", "realcons");
	symbTable.put("sin", "realop");
	symbTable.put("cos", "realop");
	symbTable.put("tan", "realop");
	symbTable.put("println", "statement");
	symbTable.put("if", "statement");
	symbTable.put("while", "statement");
	symbTable.put("let", "statement");
	symbTable.put("assign", "statement");


    }
	

    public static String fetchSym(String keyName){

	return symbTable.get(keyName);

    }
	
	
    //Read input data from .in file and store in ArrayList ibtldata
    public static void readFile(String inFile){
	try {
	    BufferedReader br = new BufferedReader(new FileReader(inFile));
	    String x;
	    while((x = br.readLine()) != null){
		if(x.length()>0){
		    //System.out.println(x);
		    //System.out.println(x.length());
		    ibtldata.add(x);
		}
	    }
			
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
	
    //Turn ibtldata array list into double array of chars
    public static void getchars(){
	int i = 0;
	String delimeter = "";
	String[] data;
	chars = new String[ibtldata.size()][200];
	while(i<ibtldata.size()){
	    data = ibtldata.get(i).split(delimeter);
	    for(int j=0; j<data.length; j++){
		chars[i][j] = data[j];
	    }
	    i++;
	}
    }
		
    //Group chars into lexemes
    public static void lexemeGroup(){
	int k=0;
	String x="";
	lexemes = new String[chars.length][50];
	for(int i=0; i<chars.length; i++){
	    for(int j=0; j<200; j++){
		if(chars[i][j]!=null){
		    if(chars[i][j].equalsIgnoreCase("(")){
			lexemes[i][k]=chars[i][j];
			k++;
		    }else if(chars[i][j].equals("\"")){
			if(x.length()>0){
			    lexemes[i][k]=x;
			    x="";
			    k++;
			}
			j++;
			while(!chars[i][j].equals("\"")){
			    x=x.concat(chars[i][j]);
			    j++;
			}
			lexemes[i][k]=x;
			x="";
			k++;
		    }else if(!(chars[i][j].equalsIgnoreCase(" ")) && !(chars[i][j].equalsIgnoreCase(")"))){
			x=x.concat(chars[i][j]);
		    }else if(chars[i][j].equalsIgnoreCase(" ") && x.length()>0){
			lexemes[i][k]=x;
			k++;
			x="";
		    }else if(chars[i][j].equalsIgnoreCase(")")){
			if(x.length()>0){
			    lexemes[i][k]=x;
			    x="";
			    k++;
			}
			lexemes[i][k]=chars[i][j];
			k++;
		    }
		}else if(x.length()>0){
		    lexemes[i][k]=x;
		    x="";
		    k++;
		}
	    }
	    k=0;
	}
    }
		
		
}