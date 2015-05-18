#include <stdio.h>
#include <string.h>

char s[201];
int i = -1, len, prihvati = 1;

void A();
void B();
void C();
void S();

void A() {
  if (!prihvati)
    return;
  printf("A");
  if (++i < len && s[i] == 'b')
    C();
  else if (i < len && s[i] == 'a');
  else
    prihvati = 0;
}

void B() {
  if (!prihvati)
    return;
  printf("B");
  if (++i < len && s[i] == 'c') {
    if (++i < len && s[i] == 'c') {
      S();
      if (++i < len && s[i] == 'b')
        if (++i < len && s[i] == 'c')
          return;
    }
    prihvati = 0;
  }
  else
    i--;
}

void C() {
  if (!prihvati)
    return;
  printf("C");
  A(); A();
}

void S() {
  if (!prihvati)
    return;
  printf("S");
  if (++i < len && s[i] == 'a') {
    A(); B(); return;
  } else if (i < len && s[i] == 'b') {
    B(); A(); return;
  }
  prihvati = 0;
}

int main(void) {
  scanf("%s", s);
  len = strlen(s);
  S();
  if (prihvati && i + 1 >= len)
    printf("\nDA\n");
  else
    printf("\nNE\n");
  return 0;
}
