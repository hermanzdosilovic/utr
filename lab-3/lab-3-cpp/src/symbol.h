#ifndef SYMBOL_H_
#define SYMBOL_H_

#include <string>

using std::string;
using std::ostream;
using std::istream;

class Symbol {

  friend ostream& operator<<(ostream& out, const Symbol& symbol) {
    out << symbol.symbol_name_;
    return out;
  }

  friend istream& operator>>(istream& in, Symbol& symbol) {
    in >> symbol.symbol_name_;
    return in;
  }

 public:
  Symbol(string symbol_name);
  Symbol();
  string get_symbol_name();
  void set_symbol_name(string symbol_name);
  bool operator==(Symbol& symbol);

 private:
  string symbol_name_;
};

#endif
