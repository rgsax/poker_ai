% Discard Strategy
suit(hearts).
suit(diamonds).
suit(clubs).
suit(spades).
value(2..14).

keep(S, V) | discard(S, V) :- in_hand(S, V).
score(1000) :- #count{S, V: keep(S, V)} = 0.

high_card(V) :- V = #max{Va: keep(Va, _)}, keep(_, _).
score(100) :- high_card(_).

% Se non ho niente ma 3 carte dello stesso colore provo a fare colore
% ==> se non ho niente ma 3 carte dello stesso colore, non scarto niente di quel colore
:- highestPoint(100), suit(S), #count{V: in_hand(V, S)} >= 3, discard(S, _).

% Se non ho niente ma max 2 buchi sullo straight, provo a fare scala 
% TODO 
% TODO: predicati "used_in"

one_pair(V) :- keep(V, S1), keep(V, S2), S1 != S2.
score(90) :- one_pair(_).

% Se ho una coppia "piccola", tieni il kicker 

highestPoint(N) :- N = #min{T: score(T)}.

two_pair(V, W) :- one_pair(V), one_pair(W), V > W.
score(80) :- two_pair(_ ,_).

% Se ho due coppie, vai per FH o Tris 

three_of_a_kind(V) :- value(V), #count{S: keep(V, S)} = 3.
score(70) :- three_of_a_kind(_).

% Se ho Tris, provo FH e Poker (automatico) a meno di non avere un buon kicker
:- highestPoint(70), discard(V, _), V = high_card(V).

next(L, L+1) :- keep(L, _), keep(L+1, _).
chain(L, U) :- next(L, LN), chain(LN, U).
chain(L, L) :- keep(L, _).

straight(L, U) :- chain(L, U), U - L = 4.
score(60) :- straight(_, _).

% Se hai straight, tieni 

flush(S) :- suit(S), #count{V: keep(V, S)} = 5.
score(50) :- flush(_).

% Se hai flush, tieni 

full_house(P, T) :- one_pair(P), three_of_a_kind(T), P != T.
score(40) :- full_house(_, _).

% Se fai full house, tieni 

four_of_a_kind(V) :- value(V), #count{S: keep(V, S)} = 4.
score(30) :- four_of_a_kind(_).

% Se fai poker, tieni un buon kicker altrimenti scarta
:- highestPoint(30), four_of_a_kind(V), in_hand(W, S), W != V, W > 10, discard(W, S).

% Sono inutili, tengo già sflush e rflush e non capitano quasi mai
%straight_flush(L, U) :- straight(L, U), flush(_).
%score(20) :- straight_flush(_, _).
%royal_flush(10, 14) :- straight_flush(10, 14).
%score(10) :- royal_flush(_, _).

:~ S = #min{T: score(T)}. [S@2, S]
:~ keep(S, V). [10@1, S, V]

in_hand(V, hearts)   :- in_hand_HELPER(V, 3).
in_hand(V, clubs)    :- in_hand_HELPER(V, 2).
in_hand(V, diamonds) :- in_hand_HELPER(V, 1).
in_hand(V, spades)   :- in_hand_HELPER(V, 4).

discard_HELPER(V, 3) :- discard(V, hearts).
discard_HELPER(V, 2) :- discard(V, clubs). 
discard_HELPER(V, 1) :- discard(V, diamonds).
discard_HELPER(V, 4) :- discard(V, spades).

%discard_helper(V, 3) :- discard(V, hearts).
%discard_helper(V, 2) :- discard(V, clubs).
%discard_helper(V, 1) :- discard(V, diamonds).
%discard_helper(V, 4) :- discard(V, spades).