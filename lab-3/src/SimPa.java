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
  
  private static final Symbol emptySymbol = new Symbol("$");
  
  /**
   * Program entry. Command line arguments are not in use.
   * 
   * @param args - command line arguments
   * @throws IOException if I/O errors occurs
   */
  public static void main(final String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    List<List<Symbol>> sequences = readSequences(reader);

    readStates(reader); // ignoring
    readAlphabet(reader); // ignoring
    readAlphabet(reader); // ignoring

    Set<State> acceptableStates = readAcceptableStates(reader);
    State initialState = readInitialState(reader);
    Symbol initialStackSymbol = (Symbol) readAlphabet(reader).toArray()[0];
    Map<Triplet<State, Symbol, Symbol>, Pair<State, List<Symbol>>> transitionFunction =
        readTransitionFunction(reader);

    DPA dpa = new DPA(acceptableStates, initialState, initialStackSymbol, transitionFunction);
    
    for (List<Symbol> sequence : sequences) {
      dpa.reset();
      System.out.print(dpa.getCurrentState() + "#" + stackToString(dpa.getStack()) + "|");
      boolean fail = false;
      for (int i = 0; i < sequence.size();) {
        Symbol symbol = sequence.get(i);
        if (dpa.readSymbol(symbol)) {
          System.out.print(dpa.getCurrentState() + "#" + stackToString(dpa.getStack()) + "|");
          i++;
        } else if (!dpa.readSymbol(emptySymbol)){
          System.out.println("fail|0");
          fail = true;
          break;
        } else {
          System.out.print(dpa.getCurrentState() + "#" + stackToString(dpa.getStack()) + "|");
          if (dpa.emptyStack()) {
            System.out.println("fail|0");
            fail = true;
            break;
          }
        }
      }
      if (fail) {
        continue;
      }
      
      if(dpa.accepting()) {
        System.out.println("1");
      } else {
        while(true) {
          if (dpa.readSymbol(emptySymbol)) {
            System.out.print(dpa.getCurrentState() + "#" + stackToString(dpa.getStack()) + "|");
            if (dpa.accepting()) {
              System.out.println("1");
              break;
            } else if (dpa.emptyStack()) {
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

  private static String stackToString(Stack<Symbol> stack) {
    StringBuilder builder = new StringBuilder();
    for (Symbol s : stack)
      builder.append(s);
    builder.reverse();
    if (stack.isEmpty())
      builder.append("$");
    return builder.toString();
  }

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

  private static Set<Symbol> readAlphabet(final BufferedReader reader) throws IOException {
    Set<Symbol> alphabet = new HashSet<>();

    for (String stringSymbol : reader.readLine().split(",")) {
      alphabet.add(new Symbol(stringSymbol));
    }

    return alphabet;
  }

  private static Set<State> readAcceptableStates(final BufferedReader reader) throws IOException {
    return readStates(reader);
  }

  private static State readInitialState(final BufferedReader reader) throws IOException {
    State state = new State(reader.readLine());
    return state;
  }

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
