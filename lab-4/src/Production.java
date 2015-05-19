import java.util.Arrays;
import java.util.List;

/**
 * Class <code>Production</code> represents production of <code>NonTerminalSymbol</code>. <br>
 * Production <i>P</i> is defined as: <br>
 * <br>
 * <i>A -> X1X2X3...XN</i> <br>
 * <br>
 * where <i>A</i> is nonterminal symbol and <i>X1</i>, <i>X2</i>, <i>X3</i>, <i>...</i>, <i>XN</i>
 * are lexical symbols.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class Production {

  /** Nonterminal symbol which represents left side of this production. */
  private NonTerminalSymbol leftSide;

  /** Lexical symbols which represents right side of this production. */
  private List<LexicalSymbol> rightSide;

  /**
   * Constructs new production of nonterminal symbol
   * 
   * @param nonTerminalSymbol nonterminal symbol of left side of production
   * @param lexicalSymbols lexical symbols of right side of production
   */
  public Production(NonTerminalSymbol nonTerminalSymbol, LexicalSymbol... lexicalSymbols) {
    leftSide = nonTerminalSymbol;
    rightSide = Arrays.asList(lexicalSymbols);
  }

  /**
   * Returns left side of production.
   * 
   * @return left side of production
   */
  public NonTerminalSymbol getLeftSide() {
    return leftSide;
  }

  /**
   * Returns right side of production.
   * 
   * @return right side of production
   */
  public List<LexicalSymbol> getRightSide() {
    return rightSide;
  }

}
