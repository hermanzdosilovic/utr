import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuringMachineDefinition {

  public static List<State> readStates(BufferedReader reader) throws IOException {
    List<State> states = new ArrayList<>();
    for (String stateName : reader.readLine().split(",")) {
      states.add(new State(stateName));
    }
    return states;
  }

  public static List<Symbol> readAlphabet(BufferedReader reader) throws IOException {
    List<Symbol> alphabet = new ArrayList<>();
    for (String symbolName : reader.readLine().split(",")) {
      alphabet.add(new Symbol(symbolName));
    }
    return alphabet;
  }

  public static List<Symbol> readTapeAlphabet(BufferedReader reader) throws IOException {
    return readAlphabet(reader);
  }

  public static Symbol readEmptyCellSymbol(BufferedReader reader) throws IOException {
    return new Symbol(reader.readLine());
  }

  public static List<Symbol> readInitialTape(BufferedReader reader) throws IOException {
    List<Symbol> tape = new ArrayList<>();
    String line = reader.readLine();
    for (int i = 0; i < line.length(); i++) {
      tape.add(new Symbol(line.substring(i, i + 1)));
    }
    return tape;
  }

  public static List<State> readAcceptableStates(BufferedReader reader) throws IOException {
    List<State> states = new ArrayList<>();
    String line = reader.readLine();
    if (line.isEmpty()) {
      return states;
    }
    for (String stateName : line.split(",")) {
      states.add(new State(stateName));
    }
    return states;
  }

  public static State readInitialState(BufferedReader reader) throws IOException {
    return new State(reader.readLine());
  }

  public static int readInitialPosition(BufferedReader reader) throws IOException {
    return Integer.parseInt(reader.readLine());
  }

  public static Map<Pair<State, Symbol>, Triplet<State, Symbol, String>> readTransitionFunction(
      BufferedReader reader) throws IOException {
    Map<Pair<State, Symbol>, Triplet<State, Symbol, String>> transitionFunction = new HashMap<>();

    String transition;
    while ((transition = reader.readLine()) != null && !transition.isEmpty()) {
      String leftSide = transition.split("->")[0];
      String rightSide = transition.split("->")[1];

      State state = new State(leftSide.split(",")[0]);
      Symbol symbol = new Symbol(leftSide.split(",")[1]);
      State nextState = new State(rightSide.split(",")[0]);
      Symbol nextSymbol = new Symbol(rightSide.split(",")[1]);
      String shift = rightSide.split(",")[2];
      transitionFunction
          .put(new Pair<>(state, symbol), new Triplet<>(nextState, nextSymbol, shift));
    }
    return transitionFunction;
  }
}
