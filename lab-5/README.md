# Turing Machine

## About

Given the Turing Machine definition, initial tape content and position of the head over the tape, output following after machine halts (it is guaranteed it will halt):

* State of machine
* Position of head (`0 <= position < tape.size()`)
* Content of the tape
* `1` if machine accepts given tape, `0` otherwise

This solution is writen in Java.

### Input

The input consists of several lines:

1. Set of states separated with `,`
2. Set of input symbols separated with `,`. This **does not** include `blank symbol`.
3. Set of tape alphabet symbols separated with `,`. This **does** include `blank symbol`.
4. Blank symbol
5. String representing initial tape
6. Set of accepting states separated with `,`
7. Initial state
8. Initial position of read/write head.
9. and all other lines specify the transition function:
	* currentState`,`symbolOnTape`->`nextState`,`nextSymbol`,`direction
		* _direction_ can be `L` or `R` denoting left or right direction for head to move.

Machine halts if for pair (`currentState`,`symbolOnTape`) transition function is not defined.

### Output

In one line output following information (separated with `|`):

* State of machine
* Position of head (`0 <= position < tape.size()`)
* Content of the tape
* `1` if machine accepts given tape, `0` otherwise

## Example

### Input

	q0,q1,q2,q3,q4
	0,1
	0,1,X,Y,B
	B
	0011BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
	q4
	q0
	0
	q0,0->q1,X,R
	q1,0->q1,0,R
	q2,0->q2,0,L
	q1,1->q2,Y,L
	q2,X->q0,X,R
	q0,Y->q3,Y,R
	q1,Y->q1,Y,R
	q2,Y->q2,Y,L
	q3,Y->q3,Y,R
	q3,B->q4,B,R

### Output

	q4|5|XXYYBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB|1

See more test data [here](https://github.com/hermanzdosilovic/utr/tree/master/lab-5/test/SimTC).

## Bad practice alert

All classes are in the same (default) package because assignment told so (probably because of tests they would run afterwards on our solution).

