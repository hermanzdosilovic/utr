# Simulation of Nondeterministic Finite Automaton With ε-moves

## About

This solution is written in Java.

### Input

The input consists of several lines:

1. Sequences of symbols separated with `|`. Symbols in the sequence are separated with `,`.
2. Alphabetically sorted set of states separated with `,`.
3. Alphabetically sorted set of alphabet symbols separated with `,`.
4. Alphabetically sorted set of acceptable states separated with `,`.
5. Initial state
6. and all other lines specify the transition function:
	* currentState`,`symbolOfAlphabet`->`setOfStates

`$` denotes ε-move, while `#` denotes that transition for given symbol in some state is not defined. Definition of automaton does not have to be complete. If there is no defined set of transition states for some state `S` and input symbol `a` then we know that: `S,a->#`.

### Output

For every input sequence (given in first line) output one line. Each line should consist of multiple set of states separated with `|`. Here, every set tells in which states automaton was after it read the next symbol in the sequence. States should be separated with `,`.

## Example

### Input

	a,pnp,a|pnp,lab2|pnp,a|pnp,lab2,utr,utr
	p5,s3,s4,st6,stanje1,stanje2
	a,lab2,pnp,utr
	p5
	stanje1
	s3,a->stanje2
	s3,lab2->p5,s4
	s4,$->st6
	s4,utr->p5,s3
	stanje1,a->stanje2
	stanje1,pnp->s3
	stanje2,$->st6
	stanje2,a->#

### Output

	stanje1|st6,stanje2|#|#
	stanje1|s3|p5,s4,st6
	stanje1|s3|st6,stanje2
	stanje1|s3|p5,s4,st6|p5,s3|#

See more test data [here](https://github.com/hermanzdosilovic/utr/tree/master/lab-1/test/SimEnka).

## Bad practice alert

All classes are in the same (default) package because assignment told so (probably because of tests they would run afterwards on our solution).
