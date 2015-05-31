/**
 * Represents symbol of alphabet. If symbol is empty then it is considered to be <i>epsilon</i>.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class Symbol implements Comparable<Symbol> {

  /** Holds a name of this symbol. */
  private final String name;

  /**
   * Creates symbol with given name.
   * 
   * @param name - name of the symbol
   */
  public Symbol(final String name) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    this.name = name;
  }

  /**
   * Returns name of the symbol.
   * 
   * @return name of the symbol
   */
  public String getName() {
    return name;
  }

  /**
   * Compares two symbols by their name.
   */
  @Override
  public int compareTo(final Symbol symbol) {
    return name.compareTo(symbol.getName());
  }

  /**
   * Represents symbol as <code>String</code> whose value if name of the symbol.
   */
  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Symbol other = (Symbol) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
