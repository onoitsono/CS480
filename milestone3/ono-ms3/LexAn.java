import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;


public class LexAn {
    private static ArrayList<String> ibtldata = new ArrayList<String>();
    private static String[][] chars, lexemes;
    private static ArrayList<Tokens> tokens = new ArrayList<Tokens>();
    private static Hashtable<String, String> symbTable = new Hashtable<String, String>();
    //public static ArrayList<Tokens> lex(String[] args) {
    public static void main(String[] args) {

	//Tokens a;
	fillSym();
	readFile(args[0]);
	//getchars();
	//lexemeGroup();
	/*fillSym();
	lexemeGroup();
	String lextok = "";
	int strtok = 0;
	int flttok = 0;
	int mincount = 0;
	int ecount = 0;
	for(int i = 0; i < lexemes.length; i++){
	    for(int j = 0; j < 50; j++){
		if(lexemes[i][j] != null){
		    if(fetchSym(lexemes[i][j]) != null){
			if(lexemes[i][j].compareTo("not") == 0){
			    lexemes[i][j] = "invert";
			} else if (lexemes[i][j].compareTo("iff") == 0){
			    lexemes[i][j] = "xor invert";
			}
			a = new Tokens((i + 1), fetchSym(lexemes[i][j]), lexemes[i][j]);
			tokens.add(a);
		    }else{
			for(int k = 0; k < lexemes[i][j].length(); k++){
			    if((lexemes[i][j].charAt(k) == '[') || (lexemes[i][j].charAt(k) == ']')){
				lextok = "delimiter";
			    }else if(((lexemes[i][j].charAt(k) >= 48) && 
			    (lexemes[i][j].charAt(k) <= 57) || 
				      (lexemes[i][j].charAt(k) == '-')) &&
				     ((!lexemes[i][j].contains(".")) &&
				      (!lexemes[i][j].contains("e")))){
				if((lexemes[i][j].charAt(k) == '-') || (lexemes[i][j].charAt(k) == 45)){
				    mincount++;
				}else if(mincount > 1){
				    lextok = "unknown";
				}else{
				    lextok = "integer";
				}
			    }else if(((lexemes[i][j].charAt(k) >= 48) &&
				      (lexemes[i][j].charAt(k) <= 57)) ||
				     (lexemes[i][j].charAt(k) == '-') ||
				     (lexemes[i][j].charAt(k) == '.') ||
				     (lexemes[i][j].charAt(k) == 'e')){
				if((lexemes[i][j].charAt(k) == '-') || (lexemes[i][j].charAt(k) == 45)){
				    if((k > 0) && (lexemes[i][j].charAt(k-1) == 'e')){
					mincount++;
				    }
				    else if(k == 0){
					mincount++;
				    }
				    else{
					mincount += 3;
				    }
				}
				if((lexemes[i][j].charAt(k) == '.')){
				    flttok++;
				}
				if(lexemes[i][j].indexOf("e") != lexemes[i][j].lastIndexOf("e")){
				    ecount++;
				}
				if((ecount > 1) || (flttok > 2) || (mincount > 2)){
				    lextok = "unknown";
				}
				else{
				    lextok = "float";
				}
			    }else if((lexemes[i][j].charAt(k) == '\"')){
				if(strtok == 1){
				    strtok = 0;
				}else {
				    strtok = 1;
				}
				lextok = "delimiter";
			    }else if((((lexemes[i][j].charAt(k) >= 65) &&
				      (lexemes[i][j].charAt(k) <= 90)) ||
				     ((lexemes[i][j].charAt(k) >= 97) && 
				      (lexemes[i][j].charAt(k) <= 122)) ||
				     (lexemes[i][j].charAt(k) == '_') ||
				     ((lexemes[i][j].charAt(k) >= 48) &&
				      (lexemes[i][j].charAt(k) <= 57))) &&
				     (strtok == 0)){
				if((k == 0) && ((lexemes[i][j].charAt(k) >= 48) && (lexemes[i][j].charAt(k) <= 57))){
				    //System.out.println("Error on line: " + j + ". Unexpected " + lexemes[i][j].charAt(k));
				    lextok = "unknown";
				}else{
				    lextok = "id";
				}
			    }else if(strtok == 1){
				lextok = "string";
			    }
			}
			a = new Tokens((i + 1), lextok, lexemes[i][j]);
			tokens.add(a);
			flttok = 0;
			mincount = 0;
			ecount = 0;
			lextok = "";
		    }
		}
	    }
	}
	*/
	//return tokens;
		
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
		symbTable.put("and", "boolean");
		symbTable.put("or", "boolean");
		symbTable.put("not", "unop");
		symbTable.put("logn", "unop");
		symbTable.put("xor invert", "boolean");
		symbTable.put("invert", "boolean");
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
		symbTable.put(":=", "assignment");
    }
	

    public static String fetchSym(String keyName){
		return symbTable.get(keyName);
    }
    public static boolean isANum(char a){
		if(a >= 48 && a <=57){
			return true;
		}
		return false;
	}
	public static boolean isAnIDChar(char a){
		if((a >= 65 && a <=90) ||
			(a == 95) ||
			(a >= 97 && a <= 122) ||
			(isANum(a))){
			return true;
		}
		return false;
	}
	public static boolean isAnOpChar(char a){
		if ((a == '+') || (a == '-') || (a == '*') || (a == '/') || (a == '%') ||
			(a == '^')){
			return true;
		}
	}
	public static boolean isAThingChar(char a){
		if((a == '!') || (a == '>') || (a == '<') || (a == '=')){
			return true;
		}
		return false;
	}
	public static boolean isABinOpChar(char a){
		if((isAThingChar(a)) || (isAnOpChar(a))){
			return true;
		}
		return false;
	}
	public static void pushToken(int line, String state, String str){
		Tokens a = new Tokens(line, state, str);
		tokens.add(a);
	}
    //Read input data from .in file and store in ArrayList ibtldata
    public static void readFile(String inFile){
		try {
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			int x, line;
			line = 0;
			char c;
			String state = "";
			String str = "";
			while((x = br.read()) != -1){
				c = (char) x;
				//System.out.println(c);
				if(c == '\n') line++;
				if(((c == '\n') || (c == '\t') || (c == ' ') || (c == '\r')) && (!state.equals("string"))){
					if(state.equals("id") && (fetchSym(str) != null)){
						state = fetchSym(str);
					}
					pushToken((line + 1), state, str);
					str = "";
				} else if(((c == '[') || (c == ']')) && (!state.equals("string"))){
					if(state != ""){
						pushToken((line + 1), state, str);
						str = "";
						state = "";
					}
					state = "delimiter";
					str = str.concat(c);
					pushToken((line + 1), state, str);
					str = "";
					state = "";
				} else if((c == '"')){
					if(state != ""){
						pushToken((line + 1), state, str);
						str = "";
					}
					if(state.equals("string")){
						state = "quote"
						str = str.concat(c);
						pushToken((line + 1), state, str);
						str = "";
						state = "";
					} else {
						state = "quote"
						str = str.concat(c);
						pushToken((line + 1), state, str);
						str = "";
						state = "string";
					}
				} else if (((str.length() > 0) && (isAnIDChar(c)) ||
							((str.length() == 0) && (isAnIDChar(c)) && (!isANum(c))))
							&& (!state.equals("string"))){
					str = str.concat(c);
					state = "id";
				} else if (isABinOpChar(c) && (!state.equals("string"))){
					if((str.length() > 0) && (isAnOpChar(c)) && (fetchSym(str) != null)){
						pushToken((line + 1), fetchSym(str), str);
						str = "";
						str = str.concat(c);
					} else if ((str.length() == 1) && (isAThingChar(str.charAt(0)))){
						
					}
				}
				
				 else if (state.equals("string")){
					str = str.concat(c);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	// if(state.equals("unknown") && (fetchSym(str) != null)){
		// state = fetchSym(str);
	// }
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
			lexemes[i][k] = chars[i][j];
			k++;
		    }else if(chars[i][j].equals("\"")){
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
		    }else if(!(chars[i][j].equalsIgnoreCase(" ")) && !(chars[i][j].equalsIgnoreCase("]"))){
			x = x.concat(chars[i][j]);
		    }else if(chars[i][j].equalsIgnoreCase(" ") && x.length() > 0){
			lexemes[i][k] = x;
			k++;
			x = "";
		    }else if(chars[i][j].equalsIgnoreCase("]")){
			if(x.length() > 0){
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