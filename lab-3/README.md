# Simulation of Deterministic Pushdown Automaton

## About

Create DPA from given definition and simulate its work for given input sequences. 

This solution is written in Java.

### Input

Input consists of several lines:

1. Sequences separated with `|`. Symbols in each sequence are separated with `,`
2. Set of states separated with `,`
3. Set of input symbols separated with `,`
4. Set of stack symbols separated with `,`
5. Set of accepting states separated with `,`
6. Initial state
7. Initial stack symbol
8. and all other lines specify the transition function:
	* currentState`,`inputSymbol`,`symbolOnTopOfTheStack`->`nextState`,`symbolsToPutOnStack
		* `$` denotes *empty symbol*
		* In *symbolsToPutOnStack* left most symbol is on top of the stack

### Output

For every input sequence (given in first line) output one line. Implemented DPA simulator should output: in which state DPA was after reading each symbol from sequence, content of the stack and acceptance of given sequence (0 or 1) after processing given sequence. State of DPA and stack content should be separated with `#` while pair `state#content` should be separated with `|`. If DPA stack is empty use `$` to denote that. If DPA fails in processing given sequence, output `fail`.

## Example

### Input

	0|0,2,0|1,2,0
	q1,q2,q3
	0,1,2
	J,N,K
	q3
	q1
	K
	q1,0,K->q1,NK
	q1,1,K->q1,JK
	q1,0,N->q1,NN
	q1,1,N->q1,JN
	q1,0,J->q1,NJ
	q1,1,J->q1,JJ
	q1,2,K->q2,K
	q1,2,N->q2,N
	q1,2,J->q2,J
	q2,0,N->q2,$
	q2,1,J->q2,$
	q2,$,K->q3,$

### Output

	q1#K|q1#NK|0
	q1#K|q1#NK|q2#NK|q2#K|q3#$|1
	q1#K|q1#JK|q2#JK|fail|0

See more test data [here](https://github.com/hermanzdosilovic/utr/tree/master/lab-3/test/SimPa)

## Bad practice alert

All classes are in the same (default) package because assignment told so (probably because of tests they would run afterwards on our solution).
