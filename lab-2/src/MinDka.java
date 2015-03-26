import java.io.IOException;

/**
 * Main class of lab-2.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class MinDka {

  public static void main(String[] args) throws IOException, DFAException {
    DFADefinition dfaDefinition = new DFADefinition(System.in);
    DFA dfa = new DFA(dfaDefinition);
    DFA minimizedDFA = DFAMinimizer.minimize(dfa);
    
    DFADefinition.output(System.out, minimizedDFA);
  }

}
