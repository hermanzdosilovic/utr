import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class of lab-4.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class Parser {

  /** Represents grammar which this parser users. */
  private static Map<NonTerminalSymbol, List<Production>> grammar;

  /** String for which to check if matches grammar. */
  private static String string;

  /** Represents position of parsers head. */
  private static int index = -1;

  /** Represents acceptability of read string. */
  private static boolean accept = true;

  /**
   * Program entry. Command line arguments are not in use.
   * 
   * @param args - command line arguments
   * @throws IOException if I/O errors occurs
   */
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    string = reader.readLine();
    reader.close();

    // defining nonterminal symbols
    NonTerminalSymbol A = new NonTerminalSymbol("A");
    NonTerminalSymbol B = new NonTerminalSymbol("B");
    NonTerminalSymbol C = new NonTerminalSymbol("C");
    NonTerminalSymbol S = new NonTerminalSymbol("S");

    // defining terminal symbols
    TerminalSymbol a = new TerminalSymbol("a");
    TerminalSymbol b = new TerminalSymbol("b");
    TerminalSymbol c = new TerminalSymbol("c");
    TerminalSymbol empty = new TerminalSymbol("");

    // defining grammar
    grammar = new HashMap<>();
    grammar.put(S, Arrays.asList(new Production(S, a, A, B), new Production(S, b, B, A)));
    grammar.put(A, Arrays.asList(new Production(A, b, C), new Production(A, a)));
    grammar.put(B, Arrays.asList(new Production(B, c, c, S, b, c), new Production(B, empty)));
    grammar.put(C, Arrays.asList(new Production(C, A, A)));

    S.produce();

    if (accept && index + 1 == string.length()) {
      System.out.println("\nDA");
    } else {
      System.out.println("\nNE");
    }
  }

  /**
   * Returns content of parsers head.
   * 
   * @return content of parsers head
   */
  public static String getHead() {
    try {
      return string.substring(index, index + 1);
    } catch (StringIndexOutOfBoundsException e) {
      return "";
    }
  }

  /**
   * Moves parsers head to right.
   */
  public static void moveHeadToRight() {
    index++;
  }

  /**
   * Moves parsers head to left.
   */
  public static void moveHeadToLeft() {
    index--;
  }

  /**
   * Returns grammar of parser.
   * 
   * @return grammar of parser
   */
  public static Map<NonTerminalSymbol, List<Production>> getGrammar() {
    return grammar;
  }

  /**
   * Returns <code>true</code> if parsers accepts given string, <code>false</code> otherwise.
   * 
   * @return <code>true</code> if parsers accepts given string, <code>false</code> otherwise
   */
  public static boolean isAcceptable() {
    return accept;
  }

  /**
   * Sets parsers acceptability of read string.
   * 
   * @param accept acceptability of parser
   */
  public static void setAcceptable(boolean accept) {
    Parser.accept = accept;
  }

}
