import java.util.List;

public class NonTerminalSymbol extends LexicalSymbol {

  public NonTerminalSymbol(String name) {
    super(name);
  }

  @Override
  public boolean produce() {
    if (!Parser.accept) {
      return false;
    }
    System.out.print(getName());
    List<Production> productions = Parser.grammar.get(this);
    for (Production production : productions) {
      if (acceptableProduction(production)) {
        for (int i = 1; i < production.getRightSide().size(); i++) {
          if (!production.getRightSide().get(i).produce()) {
            Parser.accept = false;
            return false;
          }
        }
        return true;
      } else {
        Parser.index--;
      }
    }
    return false;
  }

  private boolean acceptableProduction(Production production) {
    return production.getRightSide().get(0).produce();
  }
}
