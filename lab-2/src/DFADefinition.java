import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public final class DFADefinition {

  private List<State> states;
  private List<Symbol> alphabet;
  private State initialState;
  private List<State> acceptableStates;
  private Map<Pair<State, Symbol>, State> transitionFunction;
  
  public DFADefinition(final Collection<State> states, final Collection<Symbol> alphabet,
      final State initialState, final Collection<State> acceptableStates,
      final Map<Pair<State, Symbol>, State> transitionFunction) throws DFAException {
    if (states == null) {
      throw new DFAException("states cannot be null");
    }
    this.states = new ArrayList<>(states);
    Collections.sort(this.states);
    
    if (alphabet == null) {
      throw new DFAException("alphabet cannot be null");
    }
    this.alphabet = new ArrayList<>(alphabet);
    Collections.sort(this.alphabet);
    
    if (initialState == null) {
      throw new DFAException("initial state cannot be null");
    }
    this.initialState = initialState;
    
    if (acceptableStates == null) {
      throw new DFAException("acceptable states cannot be null");
    }
    this.acceptableStates = new ArrayList<>(acceptableStates);
    Collections.sort(this.acceptableStates);
    
    if (transitionFunction == null) {
      throw new DFAException("transition function cannot be null");
    }
    this.transitionFunction = new HashMap<>(transitionFunction);
  }

  public DFADefinition(final InputStream inputStream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

    states = readStates(reader);
    alphabet = readAlphabet(reader);
    acceptableStates = readAcceptableStates(reader);
    initialState = readInitalState(reader);
    transitionFunction = readTransitionFunction(reader);
    reader.close();
  }
  
  public static void write(OutputStream outputStream, DFA dfa) {
    PrintStream stream = new PrintStream(outputStream);
    int cnt = 0;
    for (State state : dfa.getStates()) {
      stream.print(state);
      if (++cnt != dfa.getStates().size()) {
        stream.print(",");
      }
    }
    stream.println();
    
    cnt = 0;
    for (Symbol symbol : dfa.getAlphabet()) {
      stream.print(symbol);
      if (++cnt != dfa.getAlphabet().size()) {
        stream.print(",");
      }
    }
    stream.println();
    
    cnt = 0;
    for (State state : dfa.getAcceptableStates()) {
      stream.print(state);
      if (++cnt != dfa.getAcceptableStates().size()) {
        stream.print(",");
      }
    }
    stream.println();
    
    stream.println(dfa.getInitialState());
    
    Map<Pair<State, Symbol>, State> transitionFunction = dfa.getTransitionFunction();
    Set<Pair<State, Symbol>> keys = new TreeSet<>();
    keys.addAll(transitionFunction.keySet());
    for (Pair<State, Symbol> pair : keys) {
      stream.println(pair.getFirst() + "," + pair.getSecond() + "->" + transitionFunction.get(pair));
    }
    
    stream.close();
  }
  
  public List<State> getStates() {
    return states;
  }

  public List<Symbol> getAlphabet() {
    return alphabet;
  }

  public State getInitialState() {
    return initialState;
  }

  public List<State> getAcceptableStates() {
    return acceptableStates;
  }

  public Map<Pair<State, Symbol>, State> getTransitionFunction() {
    return transitionFunction;
  }

  private List<State> readStates(final BufferedReader reader) throws IOException {
    List<State> states = new ArrayList<>();
    String line = reader.readLine();
    if (line.isEmpty()) {
      return states;
    }
    for (String state : line.split(",")) {
      states.add(new State(state));
    }
    return states;
  }

  private List<Symbol> readAlphabet(final BufferedReader reader) throws IOException {
    List<Symbol> alphabet = new ArrayList<>();
    for (String symbol : reader.readLine().split(",")) {
      alphabet.add(new Symbol(symbol));
    }
    return alphabet;
  }


  private State readInitalState(final BufferedReader reader) throws IOException {
    return new State(reader.readLine());
  }

  private List<State> readAcceptableStates(final BufferedReader reader) throws IOException {
    return readStates(reader);
  }

  private Map<Pair<State, Symbol>, State> readTransitionFunction(final BufferedReader reader)
      throws IOException {
    Map<Pair<State, Symbol>, State> transitionFunction = new HashMap<>();
    String transition;
    while ((transition = reader.readLine()) != null && !transition.isEmpty()) {
      State state = new State(transition.split("->")[0].split(",")[0]);
      Symbol symbol = new Symbol(transition.split("->")[0].split(",")[1]);
      transitionFunction.put(new Pair<>(state, symbol), new State(transition.split("->")[1]));
    }

    return transitionFunction;
  }


}
