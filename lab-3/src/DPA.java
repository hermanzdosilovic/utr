import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Represents <i>Deterministic Pushdown Automaton</i> defined as 7-tuple:<br>
 * <ul>
 * <li>finite set of states
 * <li>finite set of input symbols
 * <li>finite set of stack symbols
 * <li>transition function
 * <li>start or initial state
 * <li>start or initial stack symbol
 * <li>finite set of accepting states
 * </ul>
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class DPA {

  /** Represents empty symbol. */
  public static final Symbol emptySymbol = new Symbol("$");

  /** Finite set of states. */
  private Set<State> states;

  /** Finite set of input symbols. */
  private Set<Symbol> inputSymbols;

  /** Finite set of stack symbols. */
  private Set<Symbol> stackSymbols;

  /** Transition function. */
  private Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> transitionFunction;

  /** Initial state of this automaton. */
  private State initialState;

  /** Initial stack symbol of this automaton. */
  private Symbol initialStackSymbol;

  /** Finite set of accepting states. */
  private Set<State> acceptableStates;

  /** Stack of this <i>Deterministic Pushdown Automaton</i>. */
  private Stack<Symbol> stack = new Stack<>();

  /** Current state of this automaton. */
  private State currentState;

  /**
   * Creates new <i>Deterministic Pushdown Automaton</i>
   * 
   * @param states finite set of states
   * @param inputSymbols finite set of input symbols
   * @param stackSymbols finite set of stack symbols
   * @param transitionFunction transition function
   * @param initialState initial state
   * @param initialStackSymbol stack symbol
   * @param acceptableStates finite set of acceptable states
   */
  public DPA(final Set<State> states, final Set<Symbol> inputSymbols,
      final Set<Symbol> stackSymbols,
      final Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> transitionFunction,
      final State initialState, final Symbol initialStackSymbol, final Set<State> acceptableStates) {
    this.states = states;
    this.inputSymbols = inputSymbols;
    this.stackSymbols = stackSymbols;
    this.transitionFunction = transitionFunction;
    this.initialState = initialState;
    this.initialStackSymbol = initialStackSymbol;
    this.acceptableStates = acceptableStates;

    currentState = initialState;
    stack.push(this.initialStackSymbol);
  }

  /**
   * Returns <code>true</code> if this automaton can change its state after reading given symbol.
   * 
   * @param symbol to read
   * @return <code>true</code> if this automaton can change its state after reading given symbol,
   *         <code>false</code> otherwise
   */
  public boolean readSymbol(final Symbol symbol) {
    if (stack.isEmpty()) {
      return false; // cannot read given symbol because stack is empty
    }
    Pair<State, List<Symbol>> transit =
        transitionFunction.get(new Triplet<>(currentState, symbol, stack.peek()));
    if (transit == null) {
      return false; // cannot read given symbol because there is no defined transition
    }
    currentState = transit.getFirst();
    stack.pop();
    for (Symbol stackSymbol : transit.getSecond()) {
      if (!stackSymbol.equals(emptySymbol)) {
        stack.push(stackSymbol);
      }
    }
    return true;
  }

  /**
   * Returns <code>true</code> if automaton is currently in accepting state
   * 
   * @return <code>true</code> if automaton is currently in accepting state, <code>false</code>
   *         otherwise
   */
  public boolean inAcceptingState() {
    return acceptableStates.contains(currentState);
  }

  /**
   * Resets this automaton. Its current state is set to initial state. Stack is cleared and only
   * initial stack symbol is pushed on empty stack.
   */
  public void reset() {
    stack.clear();
    stack.push(initialStackSymbol);
    currentState = initialState;
  }

  /**
   * Returns <code>true</code> if stack of this automaton is empty.
   * 
   * @return <code>true</code> if stack of this automaton is empty
   */
  public boolean hasEmptyStack() {
    return stack.isEmpty();
  }

  /**
   * Returns states of this automaton.
   * 
   * @return states of this automaton.
   */
  public Set<State> getStates() {
    return states;
  }

  /**
   * Returns input symbols of this automaton.
   * 
   * @return input symbols of this automaton
   */
  public Set<Symbol> getInputSymbols() {
    return inputSymbols;
  }

  /**
   * Returns stack symbols of this automaton.
   * 
   * @return stack symbols of this automaton
   */
  public Set<Symbol> getStackSymbols() {
    return stackSymbols;
  }

  /**
   * Returns transition function of this automaton.
   * 
   * @return transition function of this automaton
   */
  public Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> getTransitionFunction() {
    return transitionFunction;
  }

  /**
   * Returns initial state of this automaton.
   * 
   * @return initial state of this automaton
   */
  public State getInitialState() {
    return initialState;
  }

  /**
   * Returns initial stack symbol of this automaton.
   * 
   * @return initial stack symbol of this automaton.
   */
  public Symbol getInitialStackSymbol() {
    return initialStackSymbol;
  }

  /**
   * Returns accepting states of this automaton.
   * 
   * @return accepting states of this automaton
   */
  public Set<State> getAcceptableStates() {
    return acceptableStates;
  }

  /**
   * Returns stack of this automaton.
   * 
   * @return stack of this automaton
   */
  public Stack<Symbol> getStack() {
    return stack;
  }

  /**
   * Returns current state of this automaton.
   * 
   * @return current state of this automaton
   */
  public State getCurrentState() {
    return currentState;
  }

}
