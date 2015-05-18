#include "dpa_definition.h"

DPADefinition::DPADefinition() {
}

void DPADefinition::Read() {
  states_ = ReadStates();
  alphabet_ = ReadAlphabet();
  stack_symbols_ = ReadStackSymbols();
  acceptable_states_ = ReadAcceptableStates();
  initial_state_ = ReadInitialState();
  initial_stack_symbol_ = ReadInitialStackSymbol();
}

vector<State> DPADefinition::ReadStates() {
  vector<State> states;
  string line;
  cin >> line;
  for (string state_name : Split(line, ","))
    states.push_back(State(state_name));
  return states;
}

vector<Symbol> DPADefinition::ReadAlphabet() {
  vector<Symbol> alphabet;
  string line;
  cin >> line;
  for (string symbol_name : Split(line, ","))
    alphabet.push_back(Symbol(symbol_name));
  return alphabet;
}

vector<Symbol> DPADefinition::ReadStackSymbols() {
  return ReadAlphabet();
}

vector<State> DPADefinition::ReadAcceptableStates() {
  return ReadStates();
}

State DPADefinition::ReadInitialState() {
  string line;
  cin >> line;
  return State(line);
}

Symbol DPADefinition::ReadInitialStackSymbol() {
  string line;
  cin >> line;
  return Symbol(line);
}
