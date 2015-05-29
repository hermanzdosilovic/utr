# Minimization of Deterministic Finite Automaton

## About

This solution is written in Java.

### Input

The input consists of several lines:

1. Alphabetically sorted set of states separated with `,`.
2. Alphabetically sorted set of alphabet symbols separated with `,`.
3. Alphabetically sorted set of acceptable states separated with `,`.
4. Initial state
5. and all other lines specify the transition function:
  * currentState`,`symbolOfAlphabet`->`setOfStates

### Output

For given DFA definition output definition of minimized DFA. Output format should
be the same as input format.

## Example

### Input

	p1,p2,p3,p4,p5,p6,p7
	c,d
	p5,p6,p7
	p1
	p1,c->p6
	p1,d->p3
	p2,c->p7
	p2,d->p3
	p3,c->p1
	p3,d->p5
	p4,c->p4
	p4,d->p6
	p5,c->p7
	p5,d->p3
	p6,c->p4
	p6,d->p1
	p7,c->p4
	p7,d->p2

### Output

	p1,p3,p4,p5,p6
	c,d
	p5,p6
	p1
	p1,c->p6
	p1,d->p3
	p3,c->p1
	p3,d->p5
	p4,c->p4
	p4,d->p6
	p5,c->p6
	p5,d->p3
	p6,c->p4
	p6,d->p1

See more test data [here](https://github.com/hermanzdosilovic/utr/tree/master/lab-2/test/MinDka).

## Bad practice alert

All classes are in the same (default) package because assignment told so (probably because of tests they would run afterwards on our solution).
