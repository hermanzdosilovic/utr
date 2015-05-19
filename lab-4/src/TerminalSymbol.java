
public class TerminalSymbol extends LexicalSymbol {

  public TerminalSymbol(String name) {
    super(name);
  }

  @Override
  public boolean produce() {
    Parser.index++;
    if (getName().equals("")) {
      Parser.index--;
      return true;
    }
    if (Parser.index >= Parser.string.length()) {
      return false;
    }
    boolean accept = getName().equals(Parser.string.substring(Parser.index, Parser.index + 1));
    return accept;
  }

}
