import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public final class DFA {
  
  private Set<State> states;
  private Set<Symbol> alphabet;
  private Set<State> acceptableStates;
  private State initialState;
  private State currentState;
  private Map<Pair<State, Symbol>, State> transitionFunction;
  
  public DFA(DFADefinition dfaDefinition) throws DFAException {
    if (dfaDefinition == null) {
      throw new DFAException("DFA definition cannot be null");
    }
    states = new HashSet<>(dfaDefinition.getStates());
    alphabet = new HashSet<>(dfaDefinition.getAlphabet());
    acceptableStates = new HashSet<>(dfaDefinition.getAcceptableStates());
    initialState = dfaDefinition.getInitialState();
    transitionFunction = new HashMap<>(dfaDefinition.getTransitionFunction());
    currentState = initialState;
  }
  
  public State readSymbol(Symbol symbol) throws DFAException {
    if (symbol == null) {
      throw new DFAException("symbol passed to DFA cannot be null");
    }
    currentState = transitionFunction.get(new Pair<>(currentState, symbol));
    return currentState;
  }
  
  public State readSequence(List<Symbol> sequence) throws DFAException {
    if (sequence == null) {
      throw new DFAException("sequence passed to DFA cannot be null");
    }
    for (Symbol symbol : sequence) {
      readSymbol(symbol);
    }
    return currentState;
  }
  
  public State reset() {
    currentState = initialState;
    return currentState;
  }
  
  public boolean isAcceptable(State state) throws DFAException {
    if (state == null) {
      throw new DFAException("state cannot be null");
    }
    return acceptableStates.contains(state);
  }
  
  public Set<State> getStates() {
    return states;
  }

  public Set<Symbol> getAlphabet() {
    return alphabet;
  }

  public Set<State> getAcceptableStates() {
    return acceptableStates;
  }

  public State getInitialState() {
    return initialState;
  }

  public State getCurrentState() {
    return currentState;
  }

  public Map<Pair<State, Symbol>, State> getTransitionFunction() {
    return transitionFunction;
  }
  
}
