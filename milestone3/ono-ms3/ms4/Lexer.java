import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;


public class Lexer {
	private static ArrayList<String> ibtldata = new ArrayList<String>();
	private static String[][] chars, lexemes;
	private static ArrayList<Tokens> tokens = new ArrayList<Tokens>();
	private static Hashtable<String, String> symbTable = new Hashtable<String, String>();
	
	
	
	public static ArrayList<Tokens> lex(String[] args) {
		Tokens a;
		readFile(args[0]);
		getchars();
		lexemeGroup();
		fillSym();
		
		for(int i=0; i<lexemes.length; i++){
			for(int j=0; j<50; j++){
				if(lexemes[i][j]!=null){
					if(fetchSym(lexemes[i][j])!=null){
						if(lexemes[i][j].compareTo("iff")==0){
							lexemes[i][j]="xor invert";
						}else if(lexemes[i][j].compareTo("not")==0){
							lexemes[i][j]="invert";
						}
						a = new Tokens((i+1), fetchSym(lexemes[i][j]), lexemes[i][j]);
						tokens.add(a);
					}else if(lexemes[i][j].matches("[+-]?[0-9]+")){
						a = new Tokens((i+1), "integer", lexemes[i][j]);
						tokens.add(a);
					}else if(lexemes[i][j].matches("[+/-/*//%/^]")){
						if(lexemes[i][j].compareTo("%")==0){
							lexemes[i][j]="mod";
						}else if(lexemes[i][j].compareTo("^")==0){
							lexemes[i][j] = "**";
						}
						a = new Tokens((i+1), "operator", lexemes[i][j]);
						tokens.add(a);
					}else if(lexemes[i][j].matches("[=<]")){
						a = new Tokens((i+1), "relation", lexemes[i][j]);
						tokens.add(a);
					}else if(lexemes[i][j].matches("(([+-]?[0-9]+[.]?[0-9]*)|([+-]?[.][0-9]+))")){
						a = new Tokens((i+1), "real", lexemes[i][j]);
						tokens.add(a);
					}else if(lexemes[i][j].matches("[()]")){
						a = new Tokens((i+1), "delimiter", lexemes[i][j]);
						tokens.add(a);
					}else if(lexemes[i][j].matches("(([+-]?[0-9]+[.]?[0-9]*)|([+-]?[.][0-9]+))e(([+-]?[0-9]+[.]?[0-9]*)|([+-]?[.][0-9]+))")){ //1.e.2 
						a = new Tokens((i+1), "real", lexemes[i][j]);
						tokens.add(a);
					}else if(lexemes[i][j].matches("[+-]?e[/^][+-]?[0-9]*[.]?[0-9]*")){
						int sub = 2;
						if(lexemes[i][j].charAt(0)=='+'||lexemes[i][j].charAt(0)=='-')
							sub++;
						a = new Tokens((i+1), "realop", "exp");
						tokens.add(a);
						a = new Tokens((i+1), "real", lexemes[i][j].substring(sub,lexemes[i][j].length()));
						tokens.add(a);
					}else{
						a = new Tokens((i+1), "string", lexemes[i][j]);
						tokens.add(a);
					}
							
				}
			}
		}
		
		return tokens;
		
	}
	
	public static void fillSym(){
		symbTable.put("true", "boolean");
		symbTable.put("false", "boolean");
		symbTable.put("and", "boolop");
		symbTable.put("or", "boolop");
		symbTable.put("not", "boolop");
		symbTable.put("iff", "boolop");
		symbTable.put("xor invert", "boolop");
		symbTable.put("invert", "boolop");
		symbTable.put("logn", "realop");
		//symbTable.put("e", "realcons");
		symbTable.put("sin", "realop");
		symbTable.put("cos", "realop");
		symbTable.put("tan", "realop");
		symbTable.put("println", "expression");
		symbTable.put("if", "expression");
		symbTable.put("while", "expression");
		symbTable.put("let", "expression");
		symbTable.put("assign", "expression");


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

