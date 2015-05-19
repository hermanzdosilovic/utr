/*
Simple C solution.
*/
#include <stdio.h>
#include <string.h>

char s[201];
int i = -1, len, accept = 1;

void A();
void B();
void C();
void S();

void A() {
  if (!accept)
    return;
  printf("A");
  if (++i < len && s[i] == 'b')
    C();
  else if (i < len && s[i] == 'a');
  else
    accept = 0;
}

void B() {
  if (!accept)
    return;
  printf("B");
  if (++i < len && s[i] == 'c') {
    if (++i < len && s[i] == 'c') {
      S();
      if (++i < len && s[i] == 'b')
        if (++i < len && s[i] == 'c')
          return;
    }
    accept = 0;
  }
  else
    i--;
}

void C() {
  if (!accept)
    return;
  printf("C");
  A(); A();
}

void S() {
  if (!accept)
    return;
  printf("S");
  if (++i < len && s[i] == 'a') {
    A(); B(); return;
  } else if (i < len && s[i] == 'b') {
    B(); A(); return;
  }
  accept = 0;
}

int main(void) {
  scanf("%s", s);
  len = strlen(s);
  S();
  if (accept && i + 1 >= len)
    printf("\nDA\n");
  else
    printf("\nNE\n");
  return 0;
}
