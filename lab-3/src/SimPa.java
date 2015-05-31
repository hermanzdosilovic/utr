import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Main class of lab-3.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class SimPa {

  /**
   * Program entry. Command line arguments are not in use.
   * 
   * @param args - command line arguments
   * @throws IOException if I/O errors occurs
   */
  public static void main(final String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    List<List<Symbol>> sequences = readSequences(reader); // 1. line of input
    Set<State> states = readStates(reader); // 2. line of input
    Set<Symbol> inputSymbols = readAlphabet(reader); // 3. line of input
    Set<Symbol> stackSymbols = readAlphabet(reader); // 4. line of input
    Set<State> acceptableStates = readAcceptableStates(reader); // 5. line of input
    State initialState = readInitialState(reader); // 6. line of input
    Symbol initialStackSymbol = (Symbol) readAlphabet(reader).toArray()[0]; // 7. line of input
    Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> transitionFunction =
        readTransitionFunction(reader); // 8. and other lines of input

    DPA dpa =
        new DPA(states, inputSymbols, stackSymbols, transitionFunction, initialState,
            initialStackSymbol, acceptableStates);

    for (List<Symbol> sequence : sequences) {
      dpa.reset();
      System.out.print(dpa.getCurrentState() + "#" + stackToString(dpa.getStack()) + "|");
      boolean fail = false;
      for (int i = 0; i < sequence.size();) {
        Symbol symbol = sequence.get(i);
        if (dpa.readSymbol(symbol)) {
          System.out.print(dpa.getCurrentState() + "#" + stackToString(dpa.getStack()) + "|");
          i++;
        } else if (!dpa.readSymbol(DPA.emptySymbol)) {
          System.out.println("fail|0");
          fail = true;
          break;
        } else {
          System.out.print(dpa.getCurrentState() + "#" + stackToString(dpa.getStack()) + "|");
        }
      }
      if (fail) {
        continue;
      }

      if (dpa.inAcceptingState()) {
        System.out.println("1");
      } else { // dpa is not in accepting state - try using empty transitions to get to the
               // accepting state
        while (true) {
          if (dpa.readSymbol(DPA.emptySymbol)) {
            System.out.print(dpa.getCurrentState() + "#" + stackToString(dpa.getStack()) + "|");
            if (dpa.inAcceptingState()) {
              System.out.println("1");
              break;
            } else if (dpa.hasEmptyStack()) {
              System.out.println("fail|0");
              break;
            }
          } else {
            System.out.println("0");
            break;
          }
        }
      }
    }
    System.out.println();
  }

  /**
   * Returns <code>String</code> from given <code>Stack</code>. Top of the stack is the far most
   * left element in string.
   * 
   * @param stack to convert into string
   * @return string from given stack
   */
  private static String stackToString(Stack<Symbol> stack) {
    StringBuilder builder = new StringBuilder();
    for (Symbol s : stack) {
      builder.append(s);
    }
    builder.reverse();
    if (stack.isEmpty()) {
      builder.append("$");
    }
    return builder.toString();
  }

  /**
   * Reads sequences for DPA to process. Every sequence is represented as list of symbols.
   * 
   * @param reader input stream reader
   * @return list of sequences
   * @throws IOException if I/O error occurs
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
   * Reads states for DPA definition.
   * 
   * @param reader input stream reader
   * @return states for DPA definition
   * @throws IOException if I/O error occurs
   */
  private static Set<State> readStates(final BufferedReader reader) throws IOException {
    Set<State> states = new HashSet<>();

    String line = reader.readLine();
    if (line.isEmpty())
      return states;
    for (String stringState : line.split(",")) {
      states.add(new State(stringState));
    }

    return states;
  }

  /**
   * Reads alphabet for DPA definition. Alphabet is defined as list of symbols.
   * 
   * @param reader input stream reader
   * @return list of symbols for DPA definition
   * @throws IOException if I/O error occurs
   */
  private static Set<Symbol> readAlphabet(final BufferedReader reader) throws IOException {
    Set<Symbol> alphabet = new HashSet<>();

    for (String stringSymbol : reader.readLine().split(",")) {
      alphabet.add(new Symbol(stringSymbol));
    }

    return alphabet;
  }

  /**
   * Reads acceptable states for DPA definition.
   * 
   * @param reader input stream reader
   * @return acceptable states for DPA definition
   * @throws IOException if I/O error occurs
   */
  private static Set<State> readAcceptableStates(final BufferedReader reader) throws IOException {
    return readStates(reader);
  }

  /**
   * Reads initial state of DPA.
   * 
   * @param reader input stream reader
   * @return acceptable states for DPA definition
   * @throws IOException if I/O error occurs
   */
  private static State readInitialState(final BufferedReader reader) throws IOException {
    return new State(reader.readLine());
  }

  /**
   * Reads transition function for DPA definition. Transition function is defined as triplet of
   * current symbol, input symbol, top of stack symbol and pair next state, list of symbols to put
   * on top of stack.
   * 
   * @param reader input stream reader
   * @return transition function for DPA definition
   * @throws IOException if I/O error occurs
   */
  private static Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> readTransitionFunction(
      final BufferedReader reader) throws IOException {
    Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> transitionFunction =
        new HashMap<>();

    String transition;
    while ((transition = reader.readLine()) != null && !transition.isEmpty()) {
      State currentState = new State(transition.split("->")[0].split(",")[0]);
      Symbol inputSymbol = new Symbol(transition.split("->")[0].split(",")[1]);
      Symbol currentStackSymbol = new Symbol(transition.split("->")[0].split(",")[2]);

      State nextState = new State(transition.split("->")[1].split(",")[0]);
      List<Symbol> addStackSymbols = new ArrayList<>();
      String s = transition.split("->")[1].split(",")[1];
      for (int i = s.length(); i > 0; i--)
        addStackSymbols.add(new Symbol(s.substring(i - 1, i)));

      transitionFunction.put(new Triplet<>(currentState, inputSymbol, currentStackSymbol),
          new Pair<>(nextState, addStackSymbols));
    }

    return transitionFunction;
  }

}
