

public abstract class LexicalSymbol implements Comparable<LexicalSymbol> {
  
  /** Holds a name of this symbol. */
  private final String name;

  /**
   * Creates symbol with given name.
   * 
   * @param name - name of the symbol
   */
  public LexicalSymbol(final String name) {
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
  
  public abstract boolean produce();
  
  /**
   * Compares two symbols by their name.
   */
  @Override
  public int compareTo(final LexicalSymbol state) {
    return name.compareTo(state.getName());
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
    LexicalSymbol other = (LexicalSymbol) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
