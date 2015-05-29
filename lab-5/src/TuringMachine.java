import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple <i>Turing machine</i> defined as 7-tuple with
 * <code>TuringMachineDefinition</code>.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class TuringMachine {

  /** Definition of <i>Turing Machine<i>. */
  private TuringMachineDefinition definition;

  /** Configuration of <i>Turing Machine</i>. */
  private TuringMachineConfiguration configuration;

  /**
   * Creates new <i>Turing Machine</i> from definition defined as 7-tuple.
   * 
   * @param definition of <i>Turing Machine</i>
   */
  public TuringMachine(final TuringMachineDefinition definition) {
    this.definition = definition;
  }

  /**
   * Runs this <i>Turing Machine</i> (from initial state) on with given tape starting at
   * <code>startPosition</code> of tape.
   * 
   * @param tape initial tape
   * @param startPosition start position on tape
   */
  public TuringMachineConfiguration run(final List<Symbol> tape, final int startPosition) {
    int position = startPosition;
    State currentState = definition.initialState;
    Triplet<State, Symbol, String> transit;
    State nextState;
    Symbol nextSymbol;
    List<Symbol> modifiedTape = new ArrayList<>(tape);
    int shift = 0;

    while (true) {
      if (position < 0 || position >= modifiedTape.size()) {
        configuration =
            new TuringMachineConfiguration(modifiedTape, currentState, position,
                definition.acceptingStates.contains(currentState));
        return configuration;
      }

      transit =
          definition.transitionFunction.get(new Pair<>(currentState, modifiedTape.get(position)));
      if (transit == null) {
        configuration =
            new TuringMachineConfiguration(modifiedTape, currentState, position,
                definition.acceptingStates.contains(currentState));
        return configuration;
      }

      nextState = transit.getFirst();
      nextSymbol = transit.getSecond();
      shift = transit.getThird().equals("L") ? -1 : 1;

      if (position + shift < 0 || position + shift >= modifiedTape.size()) {
        configuration =
            new TuringMachineConfiguration(modifiedTape, currentState, position,
                definition.acceptingStates.contains(currentState));
        return configuration;
      }

      modifiedTape.remove(position);
      modifiedTape.add(position, nextSymbol);
      currentState = nextState;
      position += shift;
    }
  }

  /**
   * Returns definition of this <i>Turing Machine</i>.
   * 
   * @return definition if this <i>Turing Machine</i>
   */
  public TuringMachineDefinition getDefinition() {
    return definition;
  }

  /**
   * Returns configuration of this <i>Turing Machine</i>
   * 
   * @return configuration of this <i>Turing Machine</i>
   */
  public TuringMachineConfiguration getConfiguration() {
    return configuration;
  }

}
