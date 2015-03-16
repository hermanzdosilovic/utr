/**
 * Represents a state of <i>Finite Automaton</i>.
 * 
 * @author Herman Zvonimir Došilović
 */
public class State implements Comparable<State> {

  /** Holds a name of this state. */
  private final String name;

  /**
   * Creates state with given name.
   * 
   * @param name - name of the state
   */
  public State(final String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("name cannot be null nor length of 0");
    }
    this.name = name;
  }

  /**
   * Returns name of the state.
   * 
   * @return name of the state
   */
  public String getName() {
    return name;
  }

  /**
   * Compares two states by their name.
   */
  @Override
  public int compareTo(final State state) {
    return name.compareTo(state.getName());
  }

  /**
   * Represents state as <code>String</code> whose value if name of the state.
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
    State other = (State) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
