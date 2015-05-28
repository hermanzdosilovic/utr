import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


public final class SimTC {

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    List<State> states = TuringMachineDefinition.readStates(reader);
    List<Symbol> alphabet = TuringMachineDefinition.readAlphabet(reader);
    List<Symbol> tapeAlphabet = TuringMachineDefinition.readTapeAlphabet(reader);
    Symbol emptyCellSymbol = TuringMachineDefinition.readEmptyCellSymbol(reader);
    List<Symbol> tape = TuringMachineDefinition.readInitialTape(reader);
    List<State> acceptableStates = TuringMachineDefinition.readAcceptableStates(reader);
    State initialState = TuringMachineDefinition.readInitialState(reader);
    int initialPosition = TuringMachineDefinition.readInitialPosition(reader);
    Map<Pair<State, Symbol>, Triplet<State, Symbol, String>> transitionFunction =
        TuringMachineDefinition.readTransitionFunction(reader);

    TuringMachine turingMachine =
        new TuringMachine(states, alphabet, tapeAlphabet, emptyCellSymbol, tape, acceptableStates,
            initialState, initialPosition, transitionFunction);

    turingMachine.run();

    System.out.println(turingMachine.getCurrentState() + "|" + turingMachine.getPosition() + "|"
        + turingMachine.getTapeString() + "|" + turingMachine.isAcceptableTape());

    reader.close();
  }
}
