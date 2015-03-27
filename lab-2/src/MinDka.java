import java.io.IOException;

/**
 * Main class of lab-2.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class MinDka {
  
  /**
   * Program entry. Command line arguments are not in use.
   * 
   * @param args - command line arguments
   * @throws IOException if I/O errors occurs
   */
  public static void main(String[] args) throws IOException, DFAException {
    DFADefinition dfaDefinition = new DFADefinition(System.in);
    DFA dfa = new DFA(dfaDefinition);
    DFA minimizedDFA = DFAMinimizer.minimize(dfa);
    DFADefinition.write(System.out, minimizedDFA);
  }

}
