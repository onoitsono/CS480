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
	String lextok = "";
	int strtok = 0;
	int flttok = 0;
	for(int i = 0; i < lexemes.length; i++){
	    for(int j = 0; j < 50; j++){
		if(lexemes[i][j] != null){
		    if(fetchSym(lexemes[i][j]) != null){
			a = lexemes[i][j]+", "+fetchSym(lexemes[i][j]);
			tokens.add(a);
		    }else{
			for(int k = 0; k < lexemes[i][j].length(); k++){
			    if((lexemes[i][j].charAt(k) == '[') || (lexemes[i][j].charAt(k) == ']')){
				lextok = ", delimiter";
			    }else if(((lexemes[i][j].charAt(k) >= 48) && 
				     (lexemes[i][j].charAt(k) <= 57) || 
				      (lexemes[i][j].charAt(k) == '-')) &&
				     ((!lexemes[i][j].contains(".")) &&
				      (!lexemes[i][j].contains("e")))){
				if((lexemes[i][j].charAt(k) == '-') && (k != 0)){
				    System.out.println("Error on line: " + j + ". Unexpected " + lexemes[i][j].charAt(k));
				}else{
				    lextok = ", integer";
				}
			    }else if(((lexemes[i][j].charAt(k) >= 48) &&
				      (lexemes[i][j].charAt(k) <= 57)) ||
				     (lexemes[i][j].charAt(k) == '-') ||
				     (lexemes[i][j].charAt(k) == '.') ||
				     (lexemes[i][j].charAt(k) == 'e')){
				if((lexemes[i][j].charAt(k) == '-') && (k != 0)){
				    System.out.println("Error on line: " + j + ". Unexpected " + lexemes[i][j].charAt(k));
				}
				if((lexemes[i][j].charAt(k) == '.') && (flttok < 1)){
				    flttok = 1;
				    lextok = ", float";
				}else if((lexemes[i][j].charAt(k) == 'e') && (flttok < 1) && (k > 0)){
				    flttok = 1;
				    lextok = ", float";
				}
			    }else if((lexemes[i][j].charAt(k) == '\"')){
				if(strtok == 1){
				    strtok = 0;
				}else {
				    strtok = 1;
				}
				lextok = ", delimiter";
			    }else if((((lexemes[i][j].charAt(k) >= 65) &&
				      (lexemes[i][j].charAt(k) <= 90)) ||
				     ((lexemes[i][j].charAt(k) >= 97) && 
				      (lexemes[i][j].charAt(k) <= 122)) ||
				     (lexemes[i][j].charAt(k) == '_') ||
				     ((lexemes[i][j].charAt(k) >= 48) &&
				      (lexemes[i][j].charAt(k) <= 57))) &&
				     (strtok == 0)){
				if((k == 0) && ((lexemes[i][j].charAt(k) >= 48) && (lexemes[i][j].charAt(k) <= 57))){
				    System.out.println("Error on line: " + j + ". Unexpected " + lexemes[i][j].charAt(k));
				}else{
				    lextok = ", id";
				}
			    }else if(strtok == 1){
				lextok = ", string";
			    }
			}
			a = lexemes[i][j] + lextok;       
			tokens.add(a);
			flttok = 0;
			lextok = "";
		    }
		}
	    }
	}
	for(int i = 0; i < tokens.size(); i++){
	    System.out.println(tokens.get(i));
	}
		
    }
	
    public static void fillSym(){
	symbTable.put("!=", "binop");
	symbTable.put(">=", "binop");
	symbTable.put("<=", "binop");
	symbTable.put(">", "binop");
	symbTable.put("<", "binop");
	symbTable.put("+", "binop");
	symbTable.put("-", "binop");
	symbTable.put("*", "binop");
	symbTable.put("/", "binop");
	symbTable.put("%", "binop");
	symbTable.put("^", "binop");
	symbTable.put("=", "binop");
	symbTable.put("true", "boolean");
	symbTable.put("false", "boolean");
	symbTable.put("and", "binop");
	symbTable.put("or", "binop");
	symbTable.put("not", "unop");
	symbTable.put("logn", "unop");
	symbTable.put("e", "realcons");
	symbTable.put("sin", "unop");
	symbTable.put("cos", "unop");
	symbTable.put("tan", "unop");
	symbTable.put("stdout", "statement");
	symbTable.put("if", "statement");
	symbTable.put("while", "statement");
	symbTable.put("let", "statement");
	symbTable.put("assign", "statement");
	symbTable.put("int", "type");
	symbTable.put("float", "type");
	symbTable.put("bool", "type");
	symbTable.put("string", "type");
	symbTable.put("real", "type");
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
		if(x.length() > 0){
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
	while(i < ibtldata.size()){
	    data = ibtldata.get(i).split(delimeter);
	    for(int j = 0; j < data.length; j++){
		chars[i][j] = data[j];
	    }
	    i++;
	}
    }
		
    //Group chars into lexemes
    public static void lexemeGroup(){
	int k = 0;
	String x = "";
	lexemes = new String[chars.length][50];
	for(int i = 0; i < chars.length; i++){
	    for(int j = 0; j < 200; j++){
		if(chars[i][j] != null){
		    if(chars[i][j].equalsIgnoreCase("[")){
			lexemes[i][k]=chars[i][j];
			k++;
		    }else if(chars[i][j].equalsIgnoreCase("\"")){
			lexemes[i][k] = chars[i][j];
			k++;
			if(x.length()>0){
			    lexemes[i][k] = x;
			    x = "";
			    k++;
			}
			j++;
			while(!chars[i][j].equalsIgnoreCase("\"")){
			    x = x.concat(chars[i][j]);
			    j++;
			}
			lexemes[i][k] = x;
			x = "";
			k++;
			lexemes[i][k] = chars[i][j];
			k++;
		    }else if(!(chars[i][j].equalsIgnoreCase(" ")) && !(chars[i][j].equalsIgnoreCase("]"))){
			x = x.concat(chars[i][j]);
		    }else if(chars[i][j].equalsIgnoreCase(" ") && x.length()>0){
			lexemes[i][k] = x;
			k++;
			x = "";
		    }else if(chars[i][j].equalsIgnoreCase("]")){
			if(x.length()>0){
			    lexemes[i][k] = x;
			    x = "";
			    k++;
			}
			lexemes[i][k] = chars[i][j];
			k++;
		    }
		}else if(x.length() > 0){
		    lexemes[i][k] = x;
		    x = "";
		    k++;
		}
	    }
	    k = 0;
	}
    }
		
		
}