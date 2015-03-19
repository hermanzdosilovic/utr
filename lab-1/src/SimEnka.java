import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Main class of lab-1.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class SimEnka {

  /**
   * Program entry. Command line arguments are not in use.
   * 
   * @param args - command line arguments
   * @throws IOException if I/O errors occurs
   */
  public static void main(final String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    List<List<Symbol>> sequences = readSequences(reader);
    SortedSet<State> states = readStates(reader);
    SortedSet<Symbol> alphabet = readAlphabet(reader);
    SortedSet<State> acceptableStates = readAcceptableStates(reader);
    State initalState = readInitialState(reader);
    Map<Pair<State, Symbol>, SortedSet<State>> transitionFunction = readTransitionFunction(reader);

    NFA nfa = new NFA(states, alphabet, transitionFunction, initalState, acceptableStates);

    for (List<Symbol> sequence : sequences) {
      List<List<State>> allNkaStates = nfa.readSequence(sequence);
      allNkaStates.add(0, nfa.getInitialState());
      nfa.resetAutomaton();

      int statesCount = 1;
      for (List<State> nkaStates : allNkaStates) {
        if (nkaStates.isEmpty()) {
          System.out.print("#");
        } else {
          int stateCount = 1;
          for (State state : nkaStates) {
            System.out.print(state);
            if (stateCount++ != nkaStates.size())
              System.out.print(",");
          }
        }

        if (statesCount++ != allNkaStates.size())
          System.out.print("|");
      }

      System.out.println();
    }

  }

  /**
   * Reads and parse first line of input.
   * 
   * @param reader - reader from <i>stdin</i>
   * @return list of sequences read from first line
   * @throws IOException if I/O errors occurs
   */
  private static List<List<Symbol>> readSequences(final BufferedReader reader) throws IOException {
    List<List<Symbol>> sequences = new ArrayList<>();

    for (String stringSequence : reader.readLine().split("\\|")) {
      List<Symbol> sequence = new ArrayList<>();
      for (String stringSymbol : stringSequence.split(",")) {
        sequence.add(new Symbol(stringSymbol));
      }
      sequences.add(sequence);
    }

    return sequences;
  }

  /**
   * Reads and parse second line of input.
   * 
   * @param reader - reader from <i>stdin</i>
   * @return set of states for <i>NFA</i>
   * @throws IOException if I/O errors occurs
   */
  private static SortedSet<State> readStates(final BufferedReader reader) throws IOException {
    SortedSet<State> states = new TreeSet<>();

    for (String stringState : reader.readLine().split(",")) {
      states.add(new State(stringState));
    }

    return states;
  }

  /**
   * Reads and parse third line of input.
   * 
   * @param reader - reader from <i>stdin</i>
   * @return set of alphabet symbols for <i>NFA</i>
   * @throws IOException if I/O errors occurs
   */
  private static SortedSet<Symbol> readAlphabet(final BufferedReader reader) throws IOException {
    SortedSet<Symbol> alphabet = new TreeSet<>();

    for (String stringSymbol : reader.readLine().split(",")) {
      alphabet.add(new Symbol(stringSymbol));
    }

    return alphabet;
  }

  /**
   * Reads and parse fourth line of input.
   * 
   * @param reader - reader from <i>stdin</i>
   * @return set of alphabet symbols for <i>NFA</i>
   * @throws IOException if I/O errors occurs
   */
  private static SortedSet<State> readAcceptableStates(final BufferedReader reader)
      throws IOException {
    return readStates(reader);
  }

  /**
   * Reads and parse fifth line of input.
   * 
   * @param reader - reader from <i>stdin</i>
   * @return inital state for <i>NFA</i>
   * @throws IOException if I/O errors occurs
   */
  private static State readInitialState(final BufferedReader reader) throws IOException {
    State state = new State(reader.readLine());
    return state;
  }

  /**
   * Reads and parse transition function of input. Definition of transition function is starting at
   * sixth line.
   * 
   * @param reader - reader from <i>stdin</i>
   * @return transition function of <i>NFA</i>
   * @throws IOException if I/O errors occurs
   */
  private static Map<Pair<State, Symbol>, SortedSet<State>> readTransitionFunction(
      final BufferedReader reader) throws IOException {
    Map<Pair<State, Symbol>, SortedSet<State>> transitionFunction = new HashMap<>();

    String transition;
    while ((transition = reader.readLine()) != null && !transition.isEmpty()) {
      State state = new State(transition.split("->")[0].split(",")[0]);

      String stringSymbol = transition.split("->")[0].split(",")[1];
      Symbol symbol = new Symbol(stringSymbol.equals("$") ? "" : stringSymbol);

      SortedSet<State> states = new TreeSet<>();
      for (String stringState : transition.split("->")[1].split(",")) {
        if (!stringState.equals("#")) {
          states.add(new State(stringState));
        }
      }

      if (states.size() != 0) {
        Pair<State, Symbol> pair = new Pair<State, Symbol>(state, symbol);
        if (transitionFunction.get(pair) != null) {
          transitionFunction.get(pair).addAll(states);
        } else {
          transitionFunction.put(new Pair<State, Symbol>(state, symbol), states);
        }
      }
    }

    return transitionFunction;
  }

}
