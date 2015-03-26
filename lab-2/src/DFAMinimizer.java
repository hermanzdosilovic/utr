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

  public static DFA minimize(DFA dfa) throws DFAException {
    Set<State> states =
        findReachableStates(dfa.getInitialState(), dfa.getAlphabet(), dfa.getTransitionFunction());

    findAllUnequalStates(states, dfa.getAcceptableStates(), dfa.getTransitionFunction(),
        dfa.getAlphabet());
    
    State initialState = removeEqualStates(states, dfa.getInitialState());
    
    Set<State> acceptableStates = removeUnnecessaryAcceptedStates(states, dfa.getAcceptableStates());
    Set<Symbol> alphabet = new TreeSet<>(dfa.getAlphabet());
    
    Map<Pair<State, Symbol>, State> transitionFunction = getNewTransitionFunction(dfa.getTransitionFunction(), dfa.getAlphabet(), states);
    
    DFADefinition dfaDefinition = new DFADefinition(states, alphabet, initialState, acceptableStates, transitionFunction);
    return new DFA(dfaDefinition);
  }

  private static Map<Pair<State, Symbol>, State> getNewTransitionFunction(
      Map<Pair<State, Symbol>, State> transitionFunction, Set<Symbol> alphabet, Set<State> states) {
    Map<Pair<State, Symbol>, State> newTransitionFunction = new HashMap<>(transitionFunction);
    for (Pair<State, Symbol> pair : transitionFunction.keySet()) {
      if (!states.contains(pair.getFirst())) {
        for (Symbol symbol : alphabet) {
          newTransitionFunction.remove(new Pair<>(pair.getFirst(), symbol));
        }
      }
    }
    return newTransitionFunction;
  }

  private static Set<State> removeUnnecessaryAcceptedStates(final Set<State> states, final Set<State> acceptableStates) {
    Set<State> newAcceptableStates = new TreeSet<>(acceptableStates);
    for(State state : acceptableStates) {
      if (!states.contains(state)) {
        newAcceptableStates.remove(state);
      }
    }
    return newAcceptableStates;
  }

  private static State removeEqualStates(Set<State> states, State initialState) {
    Object[] stateList = states.toArray();
    State newInitialState = initialState;
    
    for (int i = 0; i < stateList.length - 1; i++) {
      State firstState = (State) stateList[i];
      for (int j = i + 1; j < stateList.length; j++) {
        State secondState = (State) stateList[j];
        if (!unequalStates.contains(new Pair<>(firstState, secondState))) {
          states.remove(secondState);
          if (newInitialState.equals(secondState)) {
            newInitialState = firstState;
          }
        }
      }
    }
    
    return newInitialState;
  }

  private static void findAllUnequalStates(Set<State> states, Set<State> acceptableStates,
      Map<Pair<State, Symbol>, State> transitionFunction, Set<Symbol> alphabet) {
    unequalStates = new HashSet<>();
    
    Object[] stateList = states.toArray();
    for (int i = 0; i < stateList.length - 1; i++) {
      State firstState = (State) stateList[i];
      for (int j = i + 1; j < stateList.length; j++) {
        State secondState = (State) stateList[j];
        if (acceptableStates.contains(firstState) != acceptableStates.contains(secondState)) {
          unequalStates.add(new Pair<>(firstState, secondState));
        }
      }
    }

    dependencyMap = new HashMap<>();
    for (int i = 0; i < stateList.length - 1; i++) {
      State firstState = (State) stateList[i];
      
      for (int j = i + 1; j < stateList.length; j++) {
        State secondState = (State) stateList[j];
        
        if (!unequalStates.contains(new Pair<>(firstState, secondState))) {
          boolean unequal = false;
          
          for (Symbol symbol : alphabet) {
            State firstTransitionState = transitionFunction.get(new Pair<>(firstState, symbol));
            State secondTransitionState = transitionFunction.get(new Pair<>(secondState, symbol));
            
            if (unequalStates.contains(new Pair<>(firstTransitionState, secondTransitionState))) {
              markAsUnequal(new Pair<>(firstState, secondState));
              unequal = true;
              break;
            }
          }
          
          if (!unequal) {
            for (Symbol symbol : alphabet) {
              State firstTransitionState = transitionFunction.get(new Pair<>(firstState, symbol));
              State secondTransitionState = transitionFunction.get(new Pair<>(secondState, symbol));
              
              if (!firstTransitionState.equals(secondTransitionState)) {
                Pair<State, State> pair = new Pair<>(firstTransitionState, secondTransitionState);
                
                if (!dependencyMap.containsKey(pair)) {
                  dependencyMap.put(pair, new HashSet<>());
                }
                
                dependencyMap.get(pair).add(new Pair<>(firstState, secondState));
              }
            }
          }
        }
      }
    }
    
  }

  private static void markAsUnequal(Pair<State, State> statePair) {
    unequalStates.add(statePair);
    Set<Pair<State, State>> dependencyPairs = dependencyMap.get(statePair);
    if (dependencyPairs != null) {
      for (Pair<State, State> pair : dependencyPairs) {
        markAsUnequal(pair);
      }
    }
  }

  private static Set<State> findReachableStates(State initialState, Set<Symbol> alphabet,
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
