/**
 * Represents terminal symbol of some grammar.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class TerminalSymbol extends LexicalSymbol {

  /**
   * Creates terminal symbol with given name.
   * 
   * @param name - name of the symbol
   */
  public TerminalSymbol(String name) {
    super(name);
  }

  @Override
  public boolean produce() {
    Parser.moveHeadToRight();

    if (getName().equals("")) {
      Parser.moveHeadToLeft();
      return true;
    } else if (Parser.getHead().isEmpty()) {
      return false;
    }

    return getName().equals(Parser.getHead());
  }

}
