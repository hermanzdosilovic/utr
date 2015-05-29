#include "symbol.h"

Symbol::Symbol(string symbol_name) {
  symbol_name_ = symbol_name;
}

Symbol::Symbol() {
}

void Symbol::set_symbol_name(string symbol_name) {
  symbol_name_ = symbol_name;
}

string Symbol::get_symbol_name() {
  return symbol_name_;
}

bool Symbol::operator==(Symbol& symbol) {
  return !symbol_name_.compare(symbol.get_symbol_name());
}
