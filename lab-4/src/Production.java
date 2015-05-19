import java.util.Arrays;
import java.util.List;

public class Production {
  
  private NonTerminalSymbol leftSide;
  private List<LexicalSymbol> rightSide;
  
  public Production(NonTerminalSymbol nonTerminalSymbol, LexicalSymbol ... lexicalSymbols) {
    leftSide = nonTerminalSymbol;
    rightSide = Arrays.asList(lexicalSymbols);
  }

  public NonTerminalSymbol getLeftSide() {
    return leftSide;
  }

  public List<LexicalSymbol> getRightSide() {
    return rightSide;
  }
  
}
