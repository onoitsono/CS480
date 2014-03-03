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
		fillSym();
		readFile(args[0]);
		//return tokens;
		printToks();
    }
	public static void printToks(){
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
		return false;
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
				if((c == '\n') && (!state.equals("string"))) line++;
				if(((c == '\n') || (c == '\t') || (c == ' ') || (c == '\r')) && (!state.equals("string")) && (!state.equals(""))){
					if(state.equals("id") && (fetchSym(str) != null)){
						state = fetchSym(str);
					}
					pushToken((line + 1), state, str);
					str = "";
					state = "";
				} else if(((c == '[') || (c == ']')) && (!state.equals("string"))){
					if(state != ""){
						pushToken((line + 1), state, str);
						str = "";
						state = "";
					}
					state = "delimiter";
					str += c;
					pushToken((line + 1), state, str);
					str = "";
					state = "";
				} else if((c == '"')){
					if(state != ""){
						pushToken((line + 1), state, str);
						str = "";
					}
					if(state.equals("string")){
						state = "quote";
						str += c;
						pushToken((line + 1), state, str);
						str = "";
						state = "";
					} else {
						state = "quote";
						str += c;
						pushToken((line + 1), state, str);
						str = "";
						state = "string";
					}
				}else if (isABinOpChar(c) && (!state.equals("string"))){
					if((!state.equals("binop"))){
						if(str.length() > 0){
							pushToken((line + 1), state, str);
						}
						state = "binop";
						str = "";
						str += c;
					} else if((str.length() > 0) && (isAnOpChar(c)) && (fetchSym(str) != null)){
						pushToken((line + 1), fetchSym(str), str);
						str = "";
						str += c;
						state = "binop";
					} else if ((c == '=') && (str.length() == 1) && 
							((str.charAt(0) == '>') || (str.charAt(0) == '<') || (str.charAt(0) == '!'))){
						str += c;
						pushToken((line + 1), fetchSym(str), str);
						str = "";
					}
				} else if (((isANum(c)) || (c == '.') || (c == 'e') /* || (c == '-') */) && (!state.equals("string"))){
					if (isANum(c) && (!state.equals("float"))){
						state = "int";
						str += c;
					} else if (isANum(c) && (state.equals("float"))){
						str += c;
					}else if ((c == '.') && (str.contains("e") || str.contains("."))){
						pushToken((line + 1), state, str);
						state = "float";
						str = "";
						str += c;
					} else if ((c == 'e') && (str.contains("e"))){
						pushToken((line + 1), state, str);
						state = "float";
						str = "";
						str += c;
					}else if (((c == '.') || (c == 'e')) && ((!str.contains("e")) || (!str.contains(".")))){
						state = "float";
						str += c;
					} /*for float, check for e, if e exists, then create token and start a new float also check to see if a period or if e exists, if period create new float token
					} */
				} else if (((str.length() > 0) && (isAnIDChar(c)) || ((str.length() == 0) && (isAnIDChar(c)) && (!isANum(c))))
							&& ((!state.equals("string")) /* || (!state.equals("")) */)){
					str += c;
					state = "id";
				}  else if (state.equals("string")){
					str += c;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
