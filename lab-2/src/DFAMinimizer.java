import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;


public final class DFAMinimizer {

  private static Set<Pair<State, State>> unequalStates;

  private static Map<Pair<State, State>, Set<Pair<State, State>>> dependencyMap;
  
  private static Map<State, State> swapMap;
  
  public static DFA minimize(DFA dfa) throws DFAException {
    Set<State> states =
        getReachableStates(dfa.getInitialState(), dfa.getAlphabet(), dfa.getTransitionFunction());
    
    Map<Pair<State, Symbol>, State> transitionFunction =
        removeUnreachableStatesFromTransitionFunction(states, dfa.getTransitionFunction());
    
    Set<State> acceptableStates =
        removeUnreachableStatesFromAcceptableStates(states, dfa.getAcceptableStates());
    
    markUnequalStates(states, acceptableStates, transitionFunction, dfa.getAlphabet());
    
    State initialState = removeEqualStates(states, dfa.getInitialState());
    
    transitionFunction = convertTransitionFunction(states, transitionFunction);
    
    acceptableStates = removeUnreachableStatesFromAcceptableStates(states, acceptableStates);
    
    DFADefinition dfaDefinition = new DFADefinition(states, dfa.getAlphabet(), initialState, acceptableStates, transitionFunction);
    
    return new DFA(dfaDefinition);
  }

  private static Map<Pair<State, Symbol>, State> convertTransitionFunction(Set<State> states,
      Map<Pair<State, Symbol>, State> transitionFunction) {
    Map<Pair<State, Symbol>, State> newTransitionFunction = new HashMap<>();
    for (Pair<State, Symbol> pair : transitionFunction.keySet()) {
      State transitionState = transitionFunction.get(pair);
      newTransitionFunction.remove(pair);
      newTransitionFunction.put(new Pair<>(findSwapState(pair.getFirst()), pair.getSecond()), findSwapState(transitionState));
    }
    return newTransitionFunction;
  }

  private static State findSwapState(State state) {
    if (!swapMap.containsKey(state)) {
      return state;
    }
    return findSwapState(swapMap.get(state));
  }

  private static State removeEqualStates(Set<State> states, State initialState) {
    State newInitialState = initialState;
    swapMap = new HashMap<>();
    Object[] stateList = states.toArray();
    for (int i = 0; i < stateList.length - 1; i++) {
      State firstState = (State) stateList[i];
      for (int j = i + 1; j < stateList.length; j++) {
        State secondState = (State) stateList[j];
        if (!unequalStates.contains(new Pair<>(firstState, secondState))) {
          states.remove(secondState);
          swapMap.put(secondState, firstState);
          if (newInitialState.equals(secondState)) {
            newInitialState = firstState;
          }
        }
      }
    }
    
    return newInitialState;
  }


  private static void markUnequalStates(Set<State> states, Set<State> acceptableStates,
      Map<Pair<State, Symbol>, State> transitionFunction, Set<Symbol> alphabet) {
    unequalStates = new TreeSet<>();
    dependencyMap = new HashMap<>();
    
    for (State firstState : states) {
      for (State secondState : states) {
          if (acceptableStates.contains(firstState) != acceptableStates.contains(secondState)) {
            unequalStates.add(new Pair<>(firstState, secondState));
          }
      }
    }
    
    for (State firstState : states) {
      for (State secondState : states) {
        Pair<State, State> pair = new Pair<>(firstState, secondState);
        if (unequalStates.contains(pair) || firstState.equals(secondState))
          continue;
        
        boolean unequal = false;
        for (Symbol symbol : alphabet) {
          State firstTransitionState = transitionFunction.get(new Pair<>(firstState, symbol));
          State secondTransitionState = transitionFunction.get(new Pair<>(secondState, symbol));
          if (unequalStates.contains(new Pair<>(firstTransitionState, secondTransitionState))) {
            unequal = true;
            markUnequal(pair);
            break;
          }
        }
        
        if(!unequal) {
          for (Symbol symbol : alphabet) {
            State firstTransitionState = transitionFunction.get(new Pair<>(firstState, symbol));
            State secondTransitionState = transitionFunction.get(new Pair<>(secondState, symbol));
            if (!firstTransitionState.equals(secondTransitionState)) {
              Pair<State, State> transitionPair = new Pair<>(firstTransitionState, secondTransitionState);
              if (!dependencyMap.containsKey(transitionPair)) {
                dependencyMap.put(transitionPair, new HashSet<>());
              }
              dependencyMap.get(transitionPair).add(pair);
            }
          }
        }
      }
    }
  }

  private static void markUnequal(Pair<State, State> pair) {
    unequalStates.add(pair);
    unequalStates.add(Pair.reverse(pair));
    if (dependencyMap.containsKey(pair)) {
      for (Pair<State, State> states : dependencyMap.get(pair)) {
        markUnequal(states);
      }
    }
    
  }

  private static Set<State> removeUnreachableStatesFromAcceptableStates(Set<State> states,
      Set<State> acceptableStates) {
    Set<State> newAcceptableStates = new TreeSet<>(acceptableStates);
    for (State state : acceptableStates) {
      if (!states.contains(state)) {
        newAcceptableStates.remove(state);
      }
    }
    return newAcceptableStates;
  }

  private static Map<Pair<State, Symbol>, State> removeUnreachableStatesFromTransitionFunction(
      Set<State> states, Map<Pair<State, Symbol>, State> transitionFunction) {
    Map<Pair<State, Symbol>, State> newTransitionFunction = new HashMap<>(transitionFunction);
    for (Pair<State, Symbol> statePair : transitionFunction.keySet()) {
      if (!states.contains(statePair.getFirst())
          || !states.contains(transitionFunction.get(statePair))) {
        newTransitionFunction.remove(statePair);
      }
    }
    return newTransitionFunction;
  }

  private static Set<State> getReachableStates(State initialState, Set<Symbol> alphabet,
      Map<Pair<State, Symbol>, State> transitionFunction) {
    Set<State> states = new TreeSet<>();
    Queue<State> queue = new LinkedBlockingQueue<>();

    states.add(initialState);
    queue.add(initialState);

    while (!queue.isEmpty()) {
      State head = queue.poll();
      for (Symbol symbol : alphabet) {
        State state = transitionFunction.get(new Pair<>(head, symbol));
        if (state != null && !states.contains(state)) {
          states.add(state);
          queue.add(state);
        }
      }
    }

    return states;
  }
}
