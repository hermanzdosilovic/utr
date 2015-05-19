import java.util.List;

/**
 * Represents nonterminal symbol of some grammar.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class NonTerminalSymbol extends LexicalSymbol {

  /**
   * Creates nonterminal symbol with given name.
   * 
   * @param name - name of the symbol
   */
  public NonTerminalSymbol(String name) {
    super(name);
  }

  @Override
  public boolean produce() {
    if (!Parser.isAcceptable()) {
      return false;
    }

    System.out.print(this);

    List<Production> productions = Parser.getGrammar().get(this);
    for (Production production : productions) {
      List<LexicalSymbol> rightSide = production.getRightSide();
      if (acceptableProduction(production)) {
        for (int i = 1; i < rightSide.size(); i++) {
          if (!rightSide.get(i).produce()) {
            Parser.setAcceptable(false);
            return false;
          }
        }
        return true;
      } else {
        Parser.moveHeadToLeft();
      }
    }

    return false;
  }

  /**
   * Returns true if rule of first symbol of this production matches parsers head.
   * 
   * @param production production for which to check the first rule
   * @return <code>true</code> if rule of first symbol of this production matches parsers head,
   *         <code>false</code> otherwise
   */
  private boolean acceptableProduction(Production production) {
    return production.getRightSide().get(0).produce();
  }

}
