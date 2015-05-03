#ifndef DPA_DEFINITION_H_
#define DPA_DEFINITION_H_

#include <vector>
#include <iostream>
#include "state.h"
#include "symbol.h"
#include "string.h"

using std::vector;
using std::string;
using std::cin;

class DPADefinition {
 public:
  DPADefinition();
  void Read();
  vector<State> get_states();
  vector<Symbol> get_alphabet();
  vector<Symbol> get_stack_symbols();
  vector<State> get_acceptable_states();
  State get_initial_state();
  Symbol get_initial_stack_symbol();

 private:
  vector<State> ReadStates();
  vector<Symbol> ReadAlphabet();
  vector<Symbol> ReadStackSymbols();
  vector<State> ReadAcceptableStates();

  vector<State> states_;
  vector<Symbol> alphabet_;
  vector<Symbol> stack_symbols_;
  vector<State> acceptable_states_;
  State initial_state_;
  Symbol initial_stack_symbol_;
};

#endif
