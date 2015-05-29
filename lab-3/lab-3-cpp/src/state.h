#ifndef STATE_H_
#define STATE_H_

#include <string>

using std::string;
using std::ostream;
using std::istream;

class State {
  friend ostream& operator<<(ostream& out, const State& state) {
    out << state.state_name_;
    return out;
  }
  friend istream& operator>>(istream& in, State &state) {
    in >> state.state_name_;
    return in;
  }

 public:
  State(string state_name);
  State();
  string get_state_name();
  void set_state_name(string state_name);
  bool operator==(State& state);

 private:
  string state_name_;
};

#endif
