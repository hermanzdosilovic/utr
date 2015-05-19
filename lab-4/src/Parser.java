import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class Parser {
  
  public static Map<NonTerminalSymbol, List<Production>> grammar;
  public static String string;
  public static int index = -1;
  public static boolean prihvati = true;
  public static boolean accept = true;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    string = reader.readLine();
    reader.close();
    
    NonTerminalSymbol A = new NonTerminalSymbol("A");
    NonTerminalSymbol B = new NonTerminalSymbol("B");
    NonTerminalSymbol C = new NonTerminalSymbol("C");
    NonTerminalSymbol S = new NonTerminalSymbol("S");
    TerminalSymbol a = new TerminalSymbol("a");
    TerminalSymbol b = new TerminalSymbol("b");
    TerminalSymbol c = new TerminalSymbol("c");
    TerminalSymbol empty = new TerminalSymbol("");

    grammar = new HashMap<>();
    grammar.put(S, Arrays.asList(new Production(S, a, A, B), new Production(S, b, B, A)));
    grammar.put(A, Arrays.asList(new Production(A, b, C), new Production(A, a)));
    grammar.put(B, Arrays.asList(new Production(B, c, c, S, b, c), new Production(B, empty)));
    grammar.put(C, Arrays.asList(new Production(C, A, A)));
    
    S.produce();
    
    if (accept && index + 1 >= string.length()) {
      System.out.println("\nDA");
    } else {
      System.out.println("\nNE");
    }
  }

}
