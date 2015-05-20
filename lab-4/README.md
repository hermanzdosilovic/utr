# LL(1) Parser

#### Official Score: 100%

## About

For fixed grammar:

  S -> aAB | bBA
  A -> bC | a
  B -> ccSbc | ε
  C -> AA

parse given string.

[This](https://github.com/hermanzdosilovic/utr/tree/master/lab-4/src) solution is written in Java and works for any LL(1) grammar.

There are also very simple [C](https://github.com/hermanzdosilovic/utr/blob/master/lab-4/Parser.c) and [Java](https://github.com/hermanzdosilovic/utr/blob/master/lab-4/Parser.java) solutions.

### Input

The input consists of one line:

* String to parse

### Output

In the first line output names of nonterminal symbols as you enter in each of them recursively.

## Example

### Input

  aa

### Output

  SAB
  DA

See more test data [here](https://github.com/hermanzdosilovic/utr/tree/master/lab-4/test/Parser).

## Bad practice alert

All classes are in the same (default) package because assignment told so (probably because of tests they would run afterwards on our solution).
