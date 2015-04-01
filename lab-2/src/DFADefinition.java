import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents definition of <i>Deterministic Finite Automaton</i>.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class DFADefinition {

  /** States of definition. */
  private Set<State> states;

  /** Alphabet of definition. */
  private Set<Symbol> alphabet;

  /** Initial state of definition. */
  private State initialState;

  /** Acceptable states of definition. */
  private Set<State> acceptableStates;

  /** Transition function. */
  private Map<CommutativePair<State, Symbol>, State> transitionFunction;

  /**
   * Creates new <code>DFADefinition</code> from given arguments.
   * 
   * @param states definition states
   * @param alphabet definition alphabet
   * @param initialState definition initial state
   * @param acceptableStates definition acceptable states
   * @param transitionFunction definition transition function
   * @throws DFAException if <i>DFA</i> definition error occurs.
   */
  public DFADefinition(final Collection<State> states, final Collection<Symbol> alphabet,
      final State initialState, final Collection<State> acceptableStates,
      final Map<CommutativePair<State, Symbol>, State> transitionFunction) throws DFAException {
    setStates(states);
    setAlphabet(alphabet);
    setInitialState(initialState);
    setAcceptableStates(acceptableStates);
    setTransitionFunction(transitionFunction);
  }

  /**
   * Reads <i>DFA</i> definition from given <code>inputStream</code>.
   * 
   * @param inputStream input stream from which to read definition
   * @throws IOException if I/O error occurs
   * @throws DFAException if <i>DFA</i> definition error occurs
   */
  public DFADefinition(final InputStream inputStream) throws IOException, DFAException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

    setStates(readStates(reader));
    setAlphabet(readAlphabet(reader));
    setAcceptableStates(readAcceptableStates(reader));
    setInitialState(readInitialState(reader));
    setTransitionFunction(readTransitionFunction(reader));

    reader.close();
  }

  /**
   * Writes definition of given automaton to output stream.
   * 
   * @param outputStream output stream for write
   * @param dfa <i>DFA</i> which definition will be written
   */
  public static void write(final OutputStream outputStream, final DFA dfa) {
    PrintStream stream = new PrintStream(outputStream);

    writeStates(stream, dfa.getStates());
    writeAlphabet(stream, dfa.getAlphabet());
    writeAcceptableStates(stream, dfa.getAcceptableStates());
    stream.println(dfa.getInitialState());
    writeTransitionFunction(stream, dfa.getTransitionFunction());

    stream.close();
  }

  /**
   * Writes states with given print stream.
   * 
   * @param stream print stream for write
   * @param states state which will be written
   */
  private static void writeStates(PrintStream stream, Set<State> states) {
    writeCollection(stream, states.toArray());
  }

  /**
   * Writes alphabet with given print stream.
   * 
   * @param stream print stream for write
   * @param alphabet alphabet which will be written
   */
  private static void writeAlphabet(PrintStream stream, Set<Symbol> alphabet) {
    writeCollection(stream, alphabet.toArray());
  }

  /**
   * Writes acceptable states with given print stream.
   * 
   * @param stream print stream for write
   * @param acceptableStates acceptable states which will be written
   */
  private static void writeAcceptableStates(PrintStream stream, Set<State> acceptableStates) {
    writeCollection(stream, acceptableStates.toArray());
  }

  /**
   * Writes transition function with given print stream.
   * 
   * @param stream print stream for write
   * @param transitionFunction transition function which will be written
   */
  private static void writeTransitionFunction(PrintStream stream,
      Map<CommutativePair<State, Symbol>, State> transitionFunction) {
    for (CommutativePair<State, Symbol> pair : new TreeSet<>(transitionFunction.keySet())) {
      State state = pair.getFirst();
      Symbol symbol = pair.getSecond();
      stream.println(state + "," + symbol + "->" + transitionFunction.get(pair));
    }
  }

  /**
   * Writes given array to specified print stream. <br>
   * On every object in this array method <code>toString()</code> is called. Objects are separeted
   * with ',' (comma).
   * 
   * @param stream print stream for write
   * @param array array of objects
   */
  private static void writeCollection(PrintStream stream, Object[] array) {
    for (int i = 0; i < array.length; i++) {
      stream.print(array[i]);
      if (i < array.length - 1) {
        stream.print(",");
      }
    }
    stream.println();
  }

  /**
   * Returns states of definition.
   * 
   * @return states of definition
   */
  public Set<State> getStates() {
    return states;
  }

  /**
   * Sets new states of this definition.
   * 
   * @param states new states for definition
   * @throws DFAException if given states is <code>null</code>
   */
  private void setStates(final Collection<State> states) throws DFAException {
    if (states == null) {
      throw new DFAException("states cannot be null");
    }
    this.states = new TreeSet<>(states);
  }

  /**
   * Returns alphabet of this definition.
   * 
   * @return alphabet of this definition
   */
  public Set<Symbol> getAlphabet() {
    return alphabet;
  }

  /**
   * Sets new alphabet of this definition.
   * 
   * @param alphabet new alphabet for this definition
   * @throws DFAException if given alphabet is <code>null</code>
   */
  private void setAlphabet(final Collection<Symbol> alphabet) throws DFAException {
    if (alphabet == null) {
      throw new DFAException("alphabet cannot be null");
    }
    this.alphabet = new TreeSet<>(alphabet);
  }

  /**
   * Returns initial state of this definition.
   * 
   * @return initial state of this definition
   */
  public State getInitialState() {
    return initialState;
  }

  /**
   * Sets new initial state of this definition.
   * 
   * @param initialState new initial state for this definition
   * @throws DFAException if initial state is <code>null</code>
   */
  private void setInitialState(final State initialState) throws DFAException {
    if (initialState == null) {
      throw new DFAException("initial state cannot be null");
    }
    this.initialState = initialState;
  }

  /**
   * Returns acceptable states of this definition.
   * 
   * @return acceptable states of this definition
   */
  public Set<State> getAcceptableStates() {
    return acceptableStates;
  }

  /**
   * Sets new acceptable states of this definition.
   * 
   * @param acceptableStates new acceptable states for this definition
   * @throws DFAException if acceptable states is <code>null</code>
   */
  private void setAcceptableStates(final Collection<State> acceptableStates) throws DFAException {
    if (acceptableStates == null) {
      throw new DFAException("acceptable states cannot be null");
    }
    this.acceptableStates = new TreeSet<>(acceptableStates);
  }

  /**
   * Returns transition function of this definition.
   * 
   * @return transition function of this definition
   */
  public Map<CommutativePair<State, Symbol>, State> getTransitionFunction() {
    return transitionFunction;
  }

  /**
   * Sets transition function for this definition.
   * 
   * @param transitionFunction new transition function for this definition
   * @throws DFAException if given transition function is <code>null</code>
   */
  private void setTransitionFunction(
      final Map<CommutativePair<State, Symbol>, State> transitionFunction) throws DFAException {
    if (transitionFunction == null) {
      throw new DFAException("transition function cannot be null");
    }
    this.transitionFunction = transitionFunction;
  }

  /**
   * Reads states from given <code>BufferedReader</code>
   * 
   * @param reader reader from which states will be read
   * @return set of read states
   * @throws IOException if I/O error occurs
   */
  private Set<State> readStates(final BufferedReader reader) throws IOException {
    Set<State> states = new TreeSet<>();
    String line = reader.readLine();
    if (line.isEmpty()) {
      return states;
    }
    for (String state : line.split(",")) {
      states.add(new State(state));
    }
    return states;
  }

  /**
   * Reads alphabet from given <code>BufferedReader</code>
   * 
   * @param reader reader from which states will be read
   * @return set of read alphabet
   * @throws IOException if I/O error occurs
   */
  private Set<Symbol> readAlphabet(final BufferedReader reader) throws IOException {
    Set<Symbol> alphabet = new TreeSet<>();
    for (String symbol : reader.readLine().split(",")) {
      alphabet.add(new Symbol(symbol));
    }
    return alphabet;
  }

  /**
   * Reads initial state from given <code>BufferedReader</code>
   * 
   * @param reader reader from which states will be read
   * @return read initial state
   * @throws IOException if I/O error occurs
   */
  private State readInitialState(final BufferedReader reader) throws IOException {
    return new State(reader.readLine());
  }

  /**
   * Reads acceptable states from given <code>BufferedReader</code>
   * 
   * @param reader reader from which states will be read
   * @return set of read acceptable states
   * @throws IOException if I/O error occurs
   */
  private Set<State> readAcceptableStates(final BufferedReader reader) throws IOException {
    return readStates(reader);
  }

  /**
   * Reads transition function from given <code>BufferedReader</code>
   * 
   * @param reader reader from which states will be read
   * @return read transition function
   * @throws IOException if I/O error occurs
   */
  private Map<CommutativePair<State, Symbol>, State> readTransitionFunction(
      final BufferedReader reader) throws IOException {
    Map<CommutativePair<State, Symbol>, State> transitionFunction = new HashMap<>();
    String transition;
    while ((transition = reader.readLine()) != null && !transition.isEmpty()) {
      State state = new State(transition.split("->")[0].split(",")[0]);
      Symbol symbol = new Symbol(transition.split("->")[0].split(",")[1]);
      transitionFunction.put(new CommutativePair<>(state, symbol), new State(
          transition.split("->")[1]));
    }
    return transitionFunction;
  }

}
