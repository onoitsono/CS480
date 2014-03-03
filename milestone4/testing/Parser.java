import java.util.ArrayList;


public class Parser {
  /* Parser constructor */
  public static void parse(ArrayList<Tokens> tokens) {
    int pos = 0;
    int result;
    result = recurse(tokens, pos);

    if(result == 0) {
      System.err.println("Success");
    }
    return;
  }



  public static int recurseHelper(ArrayList<Tokens> tokens, int pos) {
  
    while(pos < tokens.size() && !tokens.get(pos).tType.equalsIgnoreCase("delimiter")) { ++pos; }
    
    if(pos < tokens.size() && tokens.get(pos).tValue.equalsIgnoreCase("[")) {
      pos++;
      
      while(pos < tokens.size() && !tokens.get(pos).tType.equalsIgnoreCase("delimiter")) { ++pos; }
      
      if(pos < tokens.size() && tokens.get(pos).tValue.equalsIgnoreCase("[")) {
        pos = recurseHelper(tokens, pos);
      }
      
      if(pos < tokens.size() && tokens.get(pos).tValue.equalsIgnoreCase("]")) {
        return ++pos;
      }

      while(pos < tokens.size() && !tokens.get(pos).tType.equalsIgnoreCase("delimiter")) { ++pos; }

      while(pos < tokens.size() && tokens.get(pos).tValue.equalsIgnoreCase("[")) {
        pos = recurseHelper(tokens, pos);
        
        while(pos < tokens.size() && !tokens.get(pos).tType.equalsIgnoreCase("delimiter")) { ++pos; }
        
        if(pos < tokens.size() && tokens.get(pos).tValue.equalsIgnoreCase("]")) {
          return ++pos;
          
        } else if(pos < tokens.size() && tokens.get(pos).tValue.equalsIgnoreCase("[")) {
            continue;
        } else {
            System.err.println("(I) Parentheses error at line: "+ tokens.get(pos-1).lineNum);
            System.exit(-1);
            return -1;
          }
      }
      
      System.err.println("(II) Parentheses error at line: "+ tokens.get(pos-1).lineNum);
      System.exit(-1);
      return -1;
      
    } else {
        System.err.println("(III) Parentheses error at line: "+tokens.get(pos).lineNum);
        System.exit(-1);
        return -1;
      }
  }

  public static int recurse(ArrayList<Tokens> tokens, int pos){
    if(!tokens.get(pos).tValue.equalsIgnoreCase("[")){
      System.err.println("File doesn't start with a \"[\"");
      System.exit(-1);
    }
    while(pos < tokens.size()){
      if(tokens.get(pos).tValue.equalsIgnoreCase("[")){ 
        if(tokens.get(pos+1).tType.equalsIgnoreCase("delimiter") && tokens.get(pos+1).tValue.equalsIgnoreCase("]")){
          System.err.println("Empty root parentheses on line: " + tokens.get(pos).lineNum);
          System.exit(-1);
        }

        ++pos;
        while(pos < tokens.size() && 
            !tokens.get(pos).tType.equalsIgnoreCase("delimiter"))
          ++pos;
        if(pos < tokens.size() &&
            tokens.get(pos).tValue.equalsIgnoreCase("]")){
          ++pos;
          continue;
        }
        while(pos < tokens.size() && tokens.get(pos).tValue.equalsIgnoreCase("[")) {
          pos = recurseHelper(tokens, pos);
          
          while(pos < tokens.size() && !tokens.get(pos).tType.equalsIgnoreCase("delimiter")) { ++pos; }
          
          if(pos < tokens.size() && tokens.get(pos).tValue.equalsIgnoreCase("]")) {
            ++pos;
            break;
          } else if(pos < tokens.size() &&  tokens.get(pos).tValue.equalsIgnoreCase("[")) {
              continue;
            } else {
                System.err.println("Parentheses error at line: "+ tokens.get(pos-1).lineNum);
                System.exit(-1);
              }
        }
      } else {
        System.err.println("Parentheses error at line: "+ tokens.get(pos-1).lineNum);
        System.exit(-1);
      }
    }
    return 0;
  }
}
