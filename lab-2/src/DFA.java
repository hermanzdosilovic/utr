import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a simple model of <i>Deterministic Finite Automaton</i>. Created automaton does not
 * have to be minimized nor it will be minimized if created.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class DFA {

  /** All states of automaton. */
  private Set<State> states;

  /** Alphabet which automaton accepts. */
  private Set<Symbol> alphabet;

  /** Acceptable states of automaton. */
  private Set<State> acceptableStates;

  /** Initial state of automaton. When automaton is reset, current state is set to initial state. */
  private State initialState;

  /** Current state of automaton. If <code>null</code> automaton is not in any state. */
  private State currentState;

  /** Transition function of automaton. */
  private Map<CommutativePair<State, Symbol>, State> transitionFunction;

  /**
   * Constructs new <i>Deterministic Finite Automaton</i> from arbitrary <i>DFA</i> definition. If
   * given definition changes thru time automaton will also change and vice versa.
   * 
   * @param dfaDefinition definition of automaton
   * @throws DFAException if <i>DFA</i> definition error occurs.
   */
  public DFA(DFADefinition dfaDefinition) throws DFAException {
    if (dfaDefinition == null) {
      throw new DFAException("DFA definition cannot be null");
    }
    states = dfaDefinition.getStates();
    alphabet = dfaDefinition.getAlphabet();
    acceptableStates = dfaDefinition.getAcceptableStates();
    initialState = dfaDefinition.getInitialState();
    transitionFunction = dfaDefinition.getTransitionFunction();
    currentState = initialState;
  }

  /**
   * Reads symbol and returns state in which automaton will be after it reads given symbol.
   * 
   * @param symbol symbol to read
   * @return state in which automaton will be after it reads given symbol
   * @throws DFAException if given symbol is <code>null</code>
   */
  public State readSymbol(Symbol symbol) throws DFAException {
    if (symbol == null) {
      throw new DFAException("symbol passed to DFA cannot be null");
    }
    currentState = transitionFunction.get(new CommutativePair<>(currentState, symbol));
    return currentState;
  }

  /**
   * Reads sequence of symbols and returns state in which automaton will be after it reads given
   * sequence.
   * 
   * @param sequence sequence of symbols for automaton to read
   * @return state in which automaton will be after it reads given sequence
   * @throws DFAException if given sequence is <code>null</code>
   */
  public State readSequence(List<Symbol> sequence) throws DFAException {
    if (sequence == null) {
      throw new DFAException("sequence passed to DFA cannot be null");
    }
    for (Symbol symbol : sequence) {
      readSymbol(symbol);
    }
    return currentState;
  }

  /**
   * Resets automaton.
   * 
   * @return returns current state (initial state) after reset
   */
  public State reset() {
    currentState = initialState;
    return currentState;
  }

  /**
   * Checks if given state is acceptable state of this automaton
   * 
   * @param state state for which you want to check if it is acceptable by this automaton
   * @return <code>true</code> if given state is acceptable by this automaton, <code>false</code>
   *         otherwise
   * @throws DFAException if given state is <code>null</code>
   */
  public boolean isAcceptable(State state) throws DFAException {
    if (state == null) {
      throw new DFAException("state cannot be null");
    }
    return acceptableStates.contains(state);
  }

  /**
   * Returns all states of this automaton.
   * 
   * @return all states of this automaton.
   */
  public Set<State> getStates() {
    return states;
  }

  /**
   * Returns alphabet of this automaton.
   * 
   * @return alphabet of this automaton
   */
  public Set<Symbol> getAlphabet() {
    return alphabet;
  }

  /**
   * Returns acceptable states of this automaton.
   * 
   * @return acceptable states of this automaton.
   */
  public Set<State> getAcceptableStates() {
    return acceptableStates;
  }

  /**
   * Return initial state of this automaton.
   * 
   * @return inital state of this automaton
   */
  public State getInitialState() {
    return initialState;
  }

  /**
   * Set initial state of this automaton to some other state.
   * 
   * @param initialState state which you want to be initial
   * @throws DFAException if state does not exists in all states of automaton
   */
  public void setInitialState(final State initialState) throws DFAException {
    if (!states.contains(initialState)) {
      throw new DFAException("initial state must be in set of all states");
    }
    this.initialState = initialState;
  }

  /**
   * Returns current state of this automaton.
   * 
   * @return current state of this automaton
   */
  public State getCurrentState() {
    return currentState;
  }

  /**
   * Returns transition function of this automaton.
   * 
   * @return transition function of this automaton
   */
  public Map<CommutativePair<State, Symbol>, State> getTransitionFunction() {
    return transitionFunction;
  }

}
