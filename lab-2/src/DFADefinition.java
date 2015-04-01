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


public final class DFADefinition {

  private Set<State> states;
  private Set<Symbol> alphabet;
  private State initialState;
  private Set<State> acceptableStates;
  private Map<CommutativePair<State, Symbol>, State> transitionFunction;

  public DFADefinition(final Collection<State> states, final Collection<Symbol> alphabet,
      final State initialState, final Collection<State> acceptableStates,
      final Map<CommutativePair<State, Symbol>, State> transitionFunction) throws DFAException {
    setStates(states);
    setAlphabet(alphabet);
    setInitialState(initialState);
    setAcceptableStates(acceptableStates);
    setTransitionFunction(transitionFunction);
  }

  public DFADefinition(final InputStream inputStream) throws IOException, DFAException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

    setStates(readStates(reader));
    setAlphabet(readAlphabet(reader));
    setAcceptableStates(readAcceptableStates(reader));
    setInitialState(readInitialState(reader));
    setTransitionFunction(readTransitionFunction(reader));

    reader.close();
  }

  public static void write(final OutputStream outputStream, final DFA dfa) {
    PrintStream stream = new PrintStream(outputStream);

    writeStates(stream, dfa.getStates());
    writeAlphabet(stream, dfa.getAlphabet());
    writeAcceptableStates(stream, dfa.getAcceptableStates());
    stream.println(dfa.getInitialState());
    writeTransitionFunction(stream, dfa.getTransitionFunction());

    stream.close();
  }

  private static void writeStates(PrintStream stream, Set<State> states) {
    writeCollection(stream, states.toArray());
  }

  private static void writeAlphabet(PrintStream stream, Set<Symbol> alphabet) {
    writeCollection(stream, alphabet.toArray());
  }

  private static void writeAcceptableStates(PrintStream stream, Set<State> acceptableStates) {
    writeCollection(stream, acceptableStates.toArray());
  }

  private static void writeTransitionFunction(PrintStream stream,
      Map<CommutativePair<State, Symbol>, State> transitionFunction) {
    for (CommutativePair<State, Symbol> pair : new TreeSet<>(transitionFunction.keySet())) {
      State state = pair.getFirst();
      Symbol symbol = pair.getSecond();
      stream.println(state + "," + symbol + "->" + transitionFunction.get(pair));
    }
  }

  private static void writeCollection(PrintStream stream, Object[] collection) {
    for (int i = 0; i < collection.length; i++) {
      stream.print(collection[i]);
      if (i < collection.length - 1) {
        stream.print(",");
      }
    }
    stream.println();
  }

  public Set<State> getStates() {
    return states;
  }

  private void setStates(final Collection<State> states) throws DFAException {
    if (states == null) {
      throw new DFAException("states cannot be null");
    }
    this.states = new TreeSet<>(states);
  }

  public Set<Symbol> getAlphabet() {
    return alphabet;
  }

  private void setAlphabet(final Collection<Symbol> alphabet) throws DFAException {
    if (alphabet == null) {
      throw new DFAException("alphabet cannot be null");
    }
    this.alphabet = new TreeSet<>(alphabet);
  }

  public State getInitialState() {
    return initialState;
  }

  private void setInitialState(final State initialState) throws DFAException {
    if (initialState == null) {
      throw new DFAException("initial state cannot be null");
    }
    this.initialState = initialState;
  }

  public Set<State> getAcceptableStates() {
    return acceptableStates;
  }

  private void setAcceptableStates(final Collection<State> acceptableStates) throws DFAException {
    if (acceptableStates == null) {
      throw new DFAException("acceptable states cannot be null");
    }
    this.acceptableStates = new TreeSet<>(acceptableStates);
  }

  public Map<CommutativePair<State, Symbol>, State> getTransitionFunction() {
    return transitionFunction;
  }

  private void setTransitionFunction(final Map<CommutativePair<State, Symbol>, State> transitionFunction)
      throws DFAException {
    if (transitionFunction == null) {
      throw new DFAException("transition function cannot be null");
    }
    this.transitionFunction = transitionFunction;
  }

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

  private Set<Symbol> readAlphabet(final BufferedReader reader) throws IOException {
    Set<Symbol> alphabet = new TreeSet<>();
    for (String symbol : reader.readLine().split(",")) {
      alphabet.add(new Symbol(symbol));
    }
    return alphabet;
  }

  private State readInitialState(final BufferedReader reader) throws IOException {
    return new State(reader.readLine());
  }

  private Set<State> readAcceptableStates(final BufferedReader reader) throws IOException {
    return readStates(reader);
  }

  private Map<CommutativePair<State, Symbol>, State> readTransitionFunction(final BufferedReader reader)
      throws IOException {
    Map<CommutativePair<State, Symbol>, State> transitionFunction = new HashMap<>();
    String transition;
    while ((transition = reader.readLine()) != null && !transition.isEmpty()) {
      State state = new State(transition.split("->")[0].split(",")[0]);
      Symbol symbol = new Symbol(transition.split("->")[0].split(",")[1]);
      transitionFunction.put(new CommutativePair<>(state, symbol), new State(transition.split("->")[1]));
    }

    return transitionFunction;
  }

}
