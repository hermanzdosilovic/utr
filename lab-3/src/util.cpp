#include "string.h"

vector<string> Split(string s, string delimiter) {
  vector<string> splitted_string;
  size_t found = 0;
  while ((found = s.find(delimiter)) != string::npos) {
    splitted_string.push_back(s.substr(0, found));
    s.erase(0, found + delimiter.size());
  }
  splitted_string.push_back(s);
  return splitted_string;
}
