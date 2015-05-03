#include "state.h"

State::State(string state_name) {
  state_name_ = state_name;
}

State::State() {
}

string State::get_state_name() {
  return state_name_;
}

void State::set_state_name(string state_name) {
  state_name_ = state_name;
}

bool State::operator==(State& state) {
  return !state_name_.compare(state.get_state_name());
}

