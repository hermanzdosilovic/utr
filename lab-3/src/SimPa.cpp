// Herman Zvonimir Dosilovic
// Solution of lab-3.

#include <iostream>
#include <vector>
#include "symbol.h"
#include "dpa_definition.h"
#include "string.h"

using namespace std;

int main() {
  string first_line;
  cin >> first_line;
  vector<vector<Symbol>> sequences;

  for (string sequence : Split(first_line, "|")) {
    vector<Symbol> vector_sequence;
    for (string symbol_name : Split(sequence, ","))
      vector_sequence.push_back(Symbol(symbol_name));
    sequences.push_back(vector_sequence);
  }

  DPADefinition *dpa_definition = new DPADefinition();
  dpa_definition->Read();
  return 0;
}
