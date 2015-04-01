import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Minimizer of Deterministic Finite Automaton <i>DFA</i>. It first removes unreachable states and
 * then uses algorithm described <a
 * href="https://www.cs.umd.edu/class/fall2009/cmsc330/lectures/discussion2.pdf">here</a>.
 * 
 * @author Herman Zvonimir Dosilovic
 *
 */
public final class DFAMinimizer {

  /** Contains information about which state will replace some other state in minimized <i>DFA</i>. */
  private static Map<State, State> swapMap;

  /** Unequal states of the last <i>DFA</i> who called <code>findUnequalStates</code> method. */
  private static Set<CommutativePair<State, State>> unequalStates;

  /**
   * Holds dependency map of states of last <i>DFA</i> who called <code>findUnequalStates</code>
   * method.
   */
  private static Map<CommutativePair<State, State>, Set<CommutativePair<State, State>>> dependencyMap;

  /**
   * Returns new minimized representation of given <i>DFA</i>
   * 
   * @param dfa <i>DFA</i> who will be minimized
   * @return new minimized representation of given <i>DFA</i>
   * @throws DFAException if <i>DFA</i> definition error occurs
   */
  public static DFA minimize(DFA dfa) throws DFAException {
    DFA minimizedDfa = getDFAWithOnlyReachableStates(dfa);
    findUnequalStates(minimizedDfa);
    removeEqualStatesFromDFA(unequalStates, minimizedDfa);
    return minimizedDfa;
  }

  /**
   * Returns unequal states of given <i>DFA</i>.
   * 
   * @param dfa <i>DFA</i>
   * @return unequal states of given <i>DFA</i>
   */
  public Set<CommutativePair<State, State>> getUnequalStates(DFA dfa) {
    findUnequalStates(dfa);
    return new HashSet<>(unequalStates);
  }

  /**
   * Finds all unequal states in given <i>DFA</i>.
   * 
   * @param dfa <i>DFA</i> for which to find unequal states
   */
  private static void findUnequalStates(DFA dfa) {
    unequalStates = new HashSet<>();
    dependencyMap = new HashMap<>();
    Set<State> states = dfa.getStates();
    Set<State> acceptableStates = dfa.getAcceptableStates();
    Set<Symbol> alphabet = dfa.getAlphabet();
    Map<CommutativePair<State, Symbol>, State> transitionFunction = dfa.getTransitionFunction();

    for (State firstState : states)
      for (State secondState : states)
        if (acceptableStates.contains(firstState) != acceptableStates.contains(secondState))
          unequalStates.add(new CommutativePair<>(firstState, secondState));

    for (State firstState : states) {
      for (State secondState : states) {
        CommutativePair<State, State> pair = new CommutativePair<>(firstState, secondState);
        if (unequalStates.contains(pair) || firstState.equals(secondState))
          continue;

        boolean markedAsUnequal = false;
        for (Symbol symbol : alphabet) {
          State firstTransitionState =
              transitionFunction.get(new CommutativePair<>(firstState, symbol));
          State secondTransitionState =
              transitionFunction.get(new CommutativePair<>(secondState, symbol));
          if (unequalStates.contains(new CommutativePair<>(firstTransitionState,
              secondTransitionState))) {
            markedAsUnequal = true;
            markAsUnequal(pair);
            break;
          }
        }

        if (markedAsUnequal)
          continue;

        for (Symbol symbol : alphabet) {
          State firstTransitionState =
              transitionFunction.get(new CommutativePair<>(firstState, symbol));
          State secondTransitionState =
              transitionFunction.get(new CommutativePair<>(secondState, symbol));

          if (firstTransitionState == null || secondTransitionState == null)
            continue;

          if (!firstTransitionState.equals(secondTransitionState)) {
            CommutativePair<State, State> transitionPair =
                new CommutativePair<>(firstTransitionState, secondTransitionState);
            if (!dependencyMap.containsKey(transitionPair))
              dependencyMap.put(transitionPair, new HashSet<>());

            dependencyMap.get(transitionPair).add(pair);
          }
        }
      }
    }
  }

  /**
   * Marks pair of states as unequal.
   * 
   * @param pair pair of states
   */
  private static void markAsUnequal(CommutativePair<State, State> pair) {
    unequalStates.add(pair);
    if (dependencyMap.containsKey(pair)) {
      for (CommutativePair<State, State> states : dependencyMap.get(pair)) {
        markAsUnequal(states);
      }
    }
  }

  /**
   * From given unequal states of <i>DFA</i> this will remove all equal states in given <i>DFA</i>.
   * 
   * @param unequalStates set of unequal states for some <i>DFA</i>
   * @param dfa <i>DFA</i> from which to remove equal states
   * @throws DFAException if <i>DFA</i> definition error occurs
   */
  public static void removeEqualStatesFromDFA(Set<CommutativePair<State, State>> unequalStates,
      DFA dfa) throws DFAException {
    swapMap = new HashMap<>();

    State initialState = dfa.getInitialState();
    Set<State> states = dfa.getStates();
    Object[] stateArray = states.toArray();
    for (Object firstObjectState : stateArray) {
      State firstState = (State) firstObjectState;
      for (Object secondObjectState : stateArray) {
        State secondState = (State) secondObjectState;
        if (unequalStates.contains(new CommutativePair<>(firstState, secondState))
            || firstState.equals(secondState))
          continue;

        if (firstState.compareTo(secondState) > 0) {
          State tmp = firstState;
          firstState = secondState;
          secondState = tmp;
        }

        swapMap.put(secondState, firstState);
        states.remove(secondState);
        if (initialState.equals(secondState))
          initialState = firstState;
      }
    }

    dfa.setInitialState(initialState);

    Map<CommutativePair<State, Symbol>, State> transitionFunction =
        new HashMap<>(dfa.getTransitionFunction());
    for (CommutativePair<State, Symbol> pair : transitionFunction.keySet()) {
      State state = pair.getFirst();
      Symbol symbol = pair.getSecond();
      State transitionState = transitionFunction.get(pair);
      if (!states.contains(state) || !states.contains(transitionState)) {
        dfa.getTransitionFunction().remove(pair);
        dfa.getTransitionFunction().put(new CommutativePair<>(findSwapState(state), symbol),
            findSwapState(transitionState));
      }
    }

    Set<State> acceptableStates = dfa.getAcceptableStates();
    Object[] acceptableStateArray = acceptableStates.toArray();
    for (Object state : acceptableStateArray) {
      if (!states.contains(state))
        acceptableStates.remove(state);
    }
  }

  /**
   * Returns the state which will replace given state in the minimized <i>DFA</i>. If state
   * <i>s1</i> is equal to state <i>s2</i> in some <i>DFA</i>, then <i>s1</i> will replace <i>s2</i>
   * in minimum representation of <i>DFA</i>.
   * 
   * @param state state for which to find replacing state
   * @return state which will replace given state in the minimized <i>DFA</i>
   */
  private static State findSwapState(State state) {
    if (!swapMap.containsKey(state))
      return state;
    return findSwapState(swapMap.get(state));
  }

  /**
   * Removes unreachable states from given <i>DFA</i>.
   * 
   * @param dfa <i>DFA</i> from which to remove unreachable states
   */
  public static void removeUnreachableStatesFromDFA(DFA dfa) {
    Set<State> reachableStates = getReachableStates(dfa);

    Set<State> dfaStates = dfa.getStates();
    Object[] dfaArrayStates = dfaStates.toArray();
    for (Object state : dfaArrayStates) {
      if (!reachableStates.contains(state)) {
        dfaStates.remove(state);
      }
    }

    Set<State> dfaAcceptableStates = dfa.getAcceptableStates();
    Object[] dfaArrayAcceptableStates = dfaAcceptableStates.toArray();
    for (Object state : dfaArrayAcceptableStates) {
      if (!reachableStates.contains(state)) {
        dfaAcceptableStates.remove(state);
      }
    }

    Map<CommutativePair<State, Symbol>, State> dfaTransitionFunction = dfa.getTransitionFunction();
    Object[] dfaArrayTransitionFunction = dfaTransitionFunction.keySet().toArray();
    for (Object pair : dfaArrayTransitionFunction) {
      State state = (State) ((CommutativePair<?, ?>) pair).getFirst();
      if (!reachableStates.contains(state)
          || !reachableStates.contains(dfaTransitionFunction.get(pair))) {
        dfaTransitionFunction.remove(pair);
      }
    }
  }

  /**
   * Constructs new <i>DFA</i> of the given <i>DFA</i> but only with reachable states.
   * 
   * @param dfa <i>DFA</i> from which to construct new <i>DFA</i>
   * @return new <i>DFA</i> of the given <i>DFA</i> but only with reachable states
   * @throws DFAException if <i>DFA</i> definition error occurs
   */
  public static DFA getDFAWithOnlyReachableStates(final DFA dfa) throws DFAException {
    DFADefinition dfaDefinition =
        new DFADefinition(dfa.getStates(), dfa.getAlphabet(), dfa.getInitialState(),
            dfa.getAcceptableStates(), dfa.getTransitionFunction());
    DFA newDfa = new DFA(dfaDefinition);
    removeUnreachableStatesFromDFA(newDfa);
    return newDfa;
  }

  /**
   * Returns reachable states of <i>DFA</i>.
   * 
   * @param dfa <i>DFA</i> from which to get reachable states
   * @return reachable states of <i>DFA</i>
   */
  public static Set<State> getReachableStates(DFA dfa) {
    Set<State> states = new TreeSet<>();
    Queue<State> queue = new LinkedBlockingQueue<>();

    states.add(dfa.getInitialState());
    queue.add(dfa.getInitialState());

    while (!queue.isEmpty()) {
      State head = queue.poll();
      for (Symbol symbol : dfa.getAlphabet()) {
        State state = dfa.getTransitionFunction().get(new CommutativePair<>(head, symbol));
        if (state != null && !states.contains(state)) {
          states.add(state);
          queue.add(state);
        }
      }
    }

    return states;
  }

}
