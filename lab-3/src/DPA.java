import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class DPA {
  
  private static final Symbol emptySymbol = new Symbol("$");
  
  private Set<State> acceptableStates;
  private State initialState;
  private Symbol initialStackSymbol;
  private Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> transitionFunction;
  
  private Stack<Symbol> stack = new Stack<>();
  private State currentState;
  
  public DPA(Set<State> acceptableStates, State initialState, Symbol initialStackSymbol,
      Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> transitionFunction) {
    this.acceptableStates = acceptableStates;
    this.initialState = initialState;
    this.initialStackSymbol = initialStackSymbol;
    this.transitionFunction = transitionFunction;
    stack.push(initialStackSymbol);
    currentState = initialState;
  }
  
  public boolean readSymbol(Symbol symbol) {
    Pair<State, List<Symbol>> pair = transitionFunction.get(new Triplet<>(currentState, symbol, stack.peek()));
    if (pair == null)
      return false;
    currentState = pair.getFirst();
    stack.pop();
    for (Symbol stackSymbol : pair.getSecond())
      if (!stackSymbol.equals(emptySymbol))
        stack.push(stackSymbol);
    return true;
  }
  
  public boolean accepting() {
    return acceptableStates.contains(currentState);
  }
  
  public void reset() {
    stack.clear();
    stack.push(initialStackSymbol);
    currentState = initialState;
  }
  
  public State getCurrentState() {
    return currentState;
  }
  
  public Stack<Symbol> getStack() {
    return stack;
  }
  
  public boolean emptyStack() {
    return stack.isEmpty();
  }
}
