import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents <i>Nondeterministic Finite Automaton</i>.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class NFA {

  /** States of this <i>NFA</i>. */
  private SortedSet<State> states;

  /** Current states of this <i>NFA</i>. */
  private final SortedSet<State> currentState = new TreeSet<>();

  /** Acceptable states of this <i>NFA</i>. */
  private SortedSet<State> acceptableStates;

  /** Represents alphabet of this <i>NFA</i>. */
  private SortedSet<Symbol> alphabet;

  /** Transition function of this <i>NFA</i>. */
  private Map<Pair<State, Symbol>, SortedSet<State>> transitionFunction;

  /** Initial state of this <i>NFA</i>. */
  private State initialState;

  /** Represents <i>epsilon</i> symbol. */
  private static final Symbol epsilonSymbol = new Symbol("");

  public NFA(final SortedSet<State> states, final SortedSet<Symbol> alphabet,
      final Map<Pair<State, Symbol>, SortedSet<State>> transitionFunction,
      final State initialState, final SortedSet<State> acceptableStates) {
    this.states = states;
    this.alphabet = alphabet;
    this.transitionFunction = transitionFunction;
    this.initialState = initialState;
    this.acceptableStates = acceptableStates;
    currentState.addAll(epsilonClosure(this.initialState));
  }

  private SortedSet<State> epsilonClosure(final State state) {
    SortedSet<State> epsilonClosure = new TreeSet<>();

    Map<State, Boolean> visited = new HashMap<>();
    Queue<State> queue = new LinkedBlockingQueue<>();

    visited.put(state, true);
    queue.add(state);

    while (!queue.isEmpty()) {
      State head = queue.remove();
      epsilonClosure.add(head);
      if (transitionFunction.containsKey(new Pair<>(head, epsilonSymbol))) {
        for (State neighbour : transitionFunction.get(new Pair<>(head, epsilonSymbol))) {
          if (!visited.containsKey(neighbour)) {
            visited.put(neighbour, true);
            queue.add(neighbour);
          }
        }
      }
    }

    return epsilonClosure;
  }

  private SortedSet<State> epsilonClosure(final SortedSet<State> states) {
    SortedSet<State> epsilonClosure = new TreeSet<>();
    for (State state : states) {
      epsilonClosure.addAll(epsilonClosure(state));
    }
    return epsilonClosure;
  }

  public List<State> readSymbol(final Symbol symbol) {
    SortedSet<State> states = new TreeSet<>();
    for (State state : currentState) {
      SortedSet<State> neighbourStates = transitionFunction.get(new Pair<>(state, symbol));
      if (neighbourStates != null) {
        states.addAll(epsilonClosure(neighbourStates));
      }
    }
    currentState.clear();
    currentState.addAll(states);
    return new ArrayList<>(currentState);
  }

  public List<List<State>> readSequence(final List<Symbol> sequence) {
    List<List<State>> states = new ArrayList<>();
    for (Symbol symbol : sequence) {
      states.add(new ArrayList<>(readSymbol(symbol)));
    }
    return states;
  }

  public List<State> resetAutomaton() {
    currentState.clear();
    currentState.addAll(epsilonClosure(initialState));
    return new ArrayList<State>(currentState);
  }

  public List<State> getCurrentState() {
    return new ArrayList<State>(currentState);
  }

  public List<State> getInitialState() {
    return new ArrayList<State>(epsilonClosure(initialState));
  }

  public List<State> getStates() {
    return new ArrayList<>(states);
  }

  public List<State> getAcceptableStates() {
    return new ArrayList<>(acceptableStates);
  }

  public List<Symbol> getAlphabet() {
    return new ArrayList<>(alphabet);
  }

  public Map<Pair<State, Symbol>, SortedSet<State>> getTransitionFunction() {
    return new HashMap<>(transitionFunction);
  }

}
