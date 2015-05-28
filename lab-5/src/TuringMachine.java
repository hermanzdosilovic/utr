import java.util.List;
import java.util.Map;


public class TuringMachine {

  private List<State> states;
  private List<Symbol> alphabet;
  private List<Symbol> tapeAlphabet;
  private Symbol emptyCellSymbol;
  private List<Symbol> tape;
  private List<State> acceptableStates;
  private State initialState;
  private Integer initialPosition;
  private Map<Pair<State, Symbol>, Triplet<State, Symbol, String>> transitionFunction;
  private State currentState;
  private int position;

  public TuringMachine(List<State> states, List<Symbol> alphabet, List<Symbol> tapeAlphabet,
      Symbol emptyCellSymbol, List<Symbol> tape, List<State> acceptableStates, State initialState,
      int initialPosition,
      Map<Pair<State, Symbol>, Triplet<State, Symbol, String>> transitionFunction) {
    this.states = states;
    this.alphabet = alphabet;
    this.tapeAlphabet = tapeAlphabet;
    this.emptyCellSymbol = emptyCellSymbol;
    this.tape = tape;
    this.acceptableStates = acceptableStates;
    this.initialState = initialState;
    this.initialPosition = initialPosition;
    this.transitionFunction = transitionFunction;
    this.currentState = initialState;
    this.position = initialPosition;
  }

  public void run() {
    Triplet<State, Symbol, String> transit;
    State nextState;
    Symbol nextSymbol;
    int shift = 0;

    while (true) {
      if (position < 0 || position >= tape.size()) {
        return;
      }

      transit = transitionFunction.get(new Pair<>(currentState, tape.get(position)));
      if (transit == null) {
        return;
      }

      nextState = transit.getFirst();
      nextSymbol = transit.getSecond();
      shift = transit.getThird().equals("L") ? -1 : 1;

      if (position + shift < 0 || position + shift >= tape.size()) {
        return;
      }

      tape.remove(position);
      tape.add(position, nextSymbol);
      currentState = nextState;
      position += shift;
    }
  }

  public List<State> getStates() {
    return states;
  }

  public List<Symbol> getAlphabet() {
    return alphabet;
  }

  public List<Symbol> getTapeAlphabet() {
    return tapeAlphabet;
  }

  public Symbol getEmptyCellSymbol() {
    return emptyCellSymbol;
  }

  public List<Symbol> getTape() {
    return tape;
  }

  public List<State> getAcceptableStates() {
    return acceptableStates;
  }

  public State getInitialState() {
    return initialState;
  }

  public Integer getInitialPosition() {
    return initialPosition;
  }

  public State getCurrentState() {
    return currentState;
  }

  public Integer getPosition() {
    return position;
  }

  public Integer isAcceptableTape() {
    return acceptableStates.contains(currentState) ? 1 : 0;
  }

  public String getTapeString() {
    StringBuilder builder = new StringBuilder();
    for (Symbol symbol : tape) {
      builder.append(symbol);
    }
    return builder.toString();
  }

}
