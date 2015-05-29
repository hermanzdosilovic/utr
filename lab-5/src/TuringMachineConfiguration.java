import java.util.List;

/**
 * Represents configuration of <i>Turing Machine</i>.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class TuringMachineConfiguration {

  /** Content of tape. */
  private List<Symbol> tape;

  /** State of machine. */
  private State state;

  /** Position of machines head over tape. */
  private int position;

  /** <code>true</code> if machine accepts given tape, <code>false</code> otherwise. */
  private boolean accepted;

  /**
   * Creates new <i>Turing Machine</i> configuration.
   * 
   * @param tape content of tape
   * @param state of machine
   * @param position of machines head over given tape
   * @param accepted <code>true</code> if machine accepts given tape, <code>false</code> otherwise
   */
  public TuringMachineConfiguration(final List<Symbol> tape, final State state, final int position,
      final boolean accepted) {
    this.tape = tape;
    this.state = state;
    this.position = position;
    this.accepted = accepted;
  }

  /**
   * Returns tape of this configuration.
   * 
   * @return tape of this configuration
   */
  public List<Symbol> getTape() {
    return tape;
  }

  /**
   * Returns state of this configuration.
   * 
   * @return state of this configuration
   */
  public State getState() {
    return state;
  }

  /**
   * Returns position of this configuration.
   * 
   * @return position of this configuration
   */
  public int getPosition() {
    return position;
  }

  /**
   * Returns <code>true</code> if machine accepts tape, <code>false</code> otherwise.
   * 
   * @return <code>true</code> if machine accepts tape, <code>false</code> otherwise
   */
  public boolean isAccepted() {
    return accepted;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(state + "|").append(position + "|");
    for (Symbol symbol : tape) {
      builder.append(symbol);
    }
    builder.append("|").append(accepted ? 1 : 0);
    return builder.toString();
  }

}
