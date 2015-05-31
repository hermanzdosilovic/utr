import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of lab-5.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class SimTS {

  /**
   * Program entry. Command line arguments are not in use.
   * 
   * @param args - command line arguments
   * @throws IOException if I/O errors occurs
   */
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    TuringMachineDefinition definition = new TuringMachineDefinition(); // Turing Machine definition
    definition.readStates(reader); // 1. line of input
    definition.readInputSymbols(reader); // 2. line of input
    definition.readTapeAlphabetSymbols(reader); // 3. line of input
    definition.readBlankSymbol(reader); // 4. line of input
    List<Symbol> tape = readTape(reader); // 5. line of input
    definition.readAcceptingStates(reader); // 6. line of input
    definition.readInitialState(reader); // 7. line of input
    int position = Integer.parseInt(reader.readLine()); // 8. line of input
    definition.readTransitionFunction(reader); // 9. and other lines of input

    reader.close();

    TuringMachine turingMachine = new TuringMachine(definition);
    TuringMachineConfiguration configuration = turingMachine.run(tape, position);

    System.out.println(configuration); // solution
  }

  /**
   * Reads tape with given <code>BufferedReader</code>.
   * 
   * @param reader input stream reader
   * @return list of symbols representing tape
   * @throws IOException if I/O error occurs
   */
  private static List<Symbol> readTape(BufferedReader reader) throws IOException {
    List<Symbol> tape = new ArrayList<>();
    String line = reader.readLine();
    for (int i = 0; i < line.length(); i++) {
      tape.add(new Symbol(line.substring(i, i + 1)));
    }
    return tape;
  }

}
