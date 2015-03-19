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

  /**
   * Creates new <i>Nondeterministic Finite Automaton</i> with defined 5-tuple.
   * 
   * @param states - states that <i>NFA</i> has
   * @param alphabet - alphabet upon <i>NFA</i> is defined
   * @param transitionFunction - transition function of <i>NFA</i> between states
   * @param initialState - initial state of <i>NFA</i>
   * @param acceptableStates - acceptable states of <i>NFA</i>
   */
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

  /**
   * Returns epsilon-closure of given state. Epsilon-closure of given state <i>q</i> is defined as
   * set of states in which every state <i>p</i> can be visited using <b>only</b>
   * <i>epsilon-transition</i>.
   * 
   * @param state - state for which epsilon-closure will be calculated
   * @return epsilon-closure of given state
   */
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

  /**
   * Returns epsilon-closure of given set of states. Epsilon-closure of set of states is defined as
   * union of epsilon-closures of each state.
   * 
   * @param states - set of states for which epsilon-closure will be calculated
   * @return epsilon-closure of given set of states
   */
  private SortedSet<State> epsilonClosure(final SortedSet<State> states) {
    SortedSet<State> epsilonClosure = new TreeSet<>();
    for (State state : states) {
      epsilonClosure.addAll(epsilonClosure(state));
    }
    return epsilonClosure;
  }

  /**
   * Returns states in which <i>NFA</i> will be after it reads given symbol
   * 
   * @param symbol - symbol for <i>NFA</i> to read
   * @return returns states in <i>NFA</i> will be after it reads given symbol
   */
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

  /**
   * Returns states in which <i>NFA</i> will be after it reads given sequence of symbols
   * 
   * @param symbol - sequence of symbols for <i>NFA</i> to read
   * @return returns states in <i>NFA</i> will be after it reads given sequence of symbols
   */
  public List<List<State>> readSequence(final List<Symbol> sequence) {
    List<List<State>> states = new ArrayList<>();
    for (Symbol symbol : sequence) {
      states.add(new ArrayList<>(readSymbol(symbol)));
    }
    return states;
  }

  /**
   * Resets <i>NFA</i>. Sets its currents state to initial state.
   * 
   * @return
   */
  public List<State> resetAutomaton() {
    currentState.clear();
    currentState.addAll(epsilonClosure(initialState));
    return new ArrayList<>(currentState);
  }

  /**
   * Returns current state of <i>NFA</i>.
   * 
   * @return current state of <i>NFA</i>
   */
  public List<State> getCurrentState() {
    return new ArrayList<>(currentState);
  }

  /**
   * Returns initial state of <i>NFA</i>.
   * 
   * @return initial state of <i>NFA</i>
   */
  public List<State> getInitialState() {
    return new ArrayList<>(epsilonClosure(initialState));
  }

  /**
   * Returns all states of <i>NFA</i> on which automaton is defined.
   * 
   * @return all states of <i>NFA</i> on which automaton is defined
   */
  public List<State> getStates() {
    return new ArrayList<>(states);
  }

  /**
   * Returns acceptable states of this automaton.
   * 
   * @return acceptable states of this automaton
   */
  public List<State> getAcceptableStates() {
    return new ArrayList<>(acceptableStates);
  }

  /**
   * Returns alphabet on which this automaton is defined.
   * 
   * @return alphabet on which this automaton is defined
   */
  public List<Symbol> getAlphabet() {
    return new ArrayList<>(alphabet);
  }

  /**
   * Returns transition function of this automaton.
   * 
   * @return transition function of this automaton
   */
  public Map<Pair<State, Symbol>, SortedSet<State>> getTransitionFunction() {
    return transitionFunction;
  }

}
