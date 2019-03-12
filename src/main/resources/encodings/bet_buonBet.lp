fold | raise | call | allin | check.
:- fold, not can_fold.
:- check, not can_check.
:- allin, not can_allin.
:- raise, not can_raise.
:- call, not can_call.

betCoefficient(50) :- high_card.
betCoefficient(15) :- one_pair.
betCoefficient(10) :- two_pair.
betCoefficient(2) :- three_of_a_kind.
betCoefficient(2) :- straight.
betCoefficient(2) :- flush.
betCoefficient(2) :- full_house.
betCoefficient(1) :- four_of_a_kind.
betCoefficient(1) :- straight_flush.
betCoefficient(1) :- royal_flush.

% FOLD
expectedValue(-V) :- fold, myBet(V).

% RAISE
raiseAmount(V) :- raise, betCoefficient(BC), 
	V = (MIN + MAX)/BC, minRaise(MIN), maxRaise(MAX).
expectedValue(V) :- raise, raiseAmount(RA), toCall(C), pot(POT), probability(P), 
	V = P * (POT + RA + C) - (100 - P) * (RA + C).

% CALL
expectedValue(V) :- call, toCall(C), pot(POT), probability(P), 
	V = P * (POT + C) - (100 - P) * C.
	
% CHECK
expectedValue(V) :- check, myBet(B), pot(POT), probability(P), 
	V = P * POT.

% ALLIN
expectedValue(V) :- allin, probability(P), chips(C), pot(POT), 
	V = P * POT - (100 - P) * C.

:~ expectedValue(V), N = 300000 - V. [N@10, V]
:~ fold. [1@10]

:- allin, probability(P), P < 80.

% FOLD
fold_expectedValue(-V) :- can_fold, myBet(V).

test_raiseAmount(V) :- can_raise, betCoefficient(BC), 
	V = (MIN + MAX)/BC, minRaise(MIN), maxRaise(MAX).
raise_expectedValue(V) :- can_raise, test_raiseAmount(RA), toCall(C), pot(POT), probability(P), 
	V = P * (POT + RA + C) - (100 - P) * (RA + C).

% CALL
call_expectedValue(V) :- can_call, toCall(C), pot(POT), probability(P), 
	V = P * POT - (100 - P) * C.
	
% CHECK
check_expectedValue(V) :- can_check, myBet(B), pot(POT), probability(P), 
	V = P * POT.

% ALLIN
allin_expectedValue(V) :- can_allin, probability(P), chips(C), pot(POT), 
	V = P * POT - (100 - P) * C.