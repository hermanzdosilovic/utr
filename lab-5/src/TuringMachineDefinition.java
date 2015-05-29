import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents definition of <i>Turing Machine</i> as 7-tuple.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class TuringMachineDefinition {

  /** Non-empty set of states. */
  public List<State> states = new ArrayList<>();

  /** Non-empty set of tape alphabet symbols. Contains <i>blank symbol</i>. */
  public List<Symbol> tapeAlphabetSymbols = new ArrayList<>();

  /** Represents <i>blank symbol</i>. */
  public Symbol blankSymbol;

  /** Set of input symbols. Does not contain <i>blank symbol</i>. */
  public List<Symbol> inputSymbols = new ArrayList<>();

  /** Transition function. */
  public Map<Pair<State, Symbol>, Triplet<State, Symbol, String>> transitionFunction =
      new HashMap<>();

  /** Initial state. */
  public State initialState;

  /** Set of accepting states. */
  public List<State> acceptingStates = new ArrayList<>();

  /**
   * Creates new empty <i>Turing Machine</i> definition.
   */
  public TuringMachineDefinition() {}

  /**
   * Creates new <i>Turing Machine</i> definition as 7-tuple.
   * 
   * @param states non empty set of states
   * @param tapeSymbols tape alphabet symbols
   * @param blankSymbol blank symbol (in theory - the only symbol allowed to occur on the tape
   *        infinitely often at any step during the computation)
   * @param inputSymbols set of input symbols
   * @param transitionFunction transition function
   * @param initialState initial state
   * @param acceptingStates set of <i>final</i> or <i>accepting states</i>
   */
  public TuringMachineDefinition(final List<State> states, final List<Symbol> tapeAlphabetSymbols,
      final Symbol blankSymbol, final List<Symbol> inputSymbols,
      final Map<Pair<State, Symbol>, Triplet<State, Symbol, String>> transitionFunction,
      final State initialState, final List<State> acceptingStates) {
    this.states = states;
    this.tapeAlphabetSymbols = tapeAlphabetSymbols;
    this.blankSymbol = blankSymbol;
    this.inputSymbols = inputSymbols;
    this.transitionFunction = transitionFunction;
    this.initialState = initialState;
    this.acceptingStates = acceptingStates;
  }

  /**
   * Reads states for this definition.
   * 
   * @param reader input stream reader
   * @throws IOException if I/O error occurs.
   */
  public void readStates(BufferedReader reader) throws IOException {
    for (String stateName : reader.readLine().split(",")) {
      states.add(new State(stateName));
    }
  }

  /**
   * Reads input symbols for this definition.
   * 
   * @param reader input stream reader
   * @throws IOException if I/O error occurs.
   */
  public void readInputSymbols(BufferedReader reader) throws IOException {
    for (String symbolName : reader.readLine().split(",")) {
      inputSymbols.add(new Symbol(symbolName));
    }
  }

  /**
   * Reads tape alphabet symbols for this definition.
   * 
   * @param reader input stream reader
   * @throws IOException if I/O error occurs.
   */
  public void readTapeAlphabetSymbols(BufferedReader reader) throws IOException {
    for (String symbolName : reader.readLine().split(",")) {
      tapeAlphabetSymbols.add(new Symbol(symbolName));
    }
  }

  /**
   * Reads blank symbol for this definition.
   * 
   * @param reader input stream reader
   * @throws IOException if I/O error occurs.
   */
  public void readBlankSymbol(BufferedReader reader) throws IOException {
    blankSymbol = new Symbol(reader.readLine());
  }

  /**
   * Reads accepting states for this definition.
   * 
   * @param reader input stream reader
   * @throws IOException if I/O error occurs.
   */
  public void readAcceptingStates(BufferedReader reader) throws IOException {
    String line = reader.readLine();
    if (line.isEmpty()) {
      return;
    }
    for (String stateName : line.split(",")) {
      acceptingStates.add(new State(stateName));
    }
  }

  /**
   * Reads initial state for this definition.
   * 
   * @param reader input stream reader
   * @throws IOException if I/O error occurs.
   */
  public void readInitialState(BufferedReader reader) throws IOException {
    initialState = new State(reader.readLine());
  }

  /**
   * Reads transition function for this definition.
   * 
   * @param reader input stream reader
   * @throws IOException if I/O error occurs.
   */
  public void readTransitionFunction(BufferedReader reader) throws IOException {
    String transition;
    while ((transition = reader.readLine()) != null && !transition.isEmpty()) {
      String leftSide = transition.split("->")[0];
      String rightSide = transition.split("->")[1];

      State state = new State(leftSide.split(",")[0]);
      Symbol symbol = new Symbol(leftSide.split(",")[1]);
      State nextState = new State(rightSide.split(",")[0]);
      Symbol nextSymbol = new Symbol(rightSide.split(",")[1]);
      String shift = rightSide.split(",")[2];
      transitionFunction
          .put(new Pair<>(state, symbol), new Triplet<>(nextState, nextSymbol, shift));
    }
  }

}
