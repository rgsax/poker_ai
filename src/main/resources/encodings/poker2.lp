tieni(VALORE, SEME) | nonTieni(VALORE, SEME) :- mano(VALORE, SEME).

%%% PER FARE ANDARE EMBASP 
mano(V, cuori)   :- in_hand_HELPER(V, 3).
mano(V, fiori)    :- in_hand_HELPER(V, 2).
mano(V, quadri) :- in_hand_HELPER(V, 1).
mano(V, picche)   :- in_hand_HELPER(V, 4).

discard_HELPER(V, 3) :- nonTieni(V, cuori).
discard_HELPER(V, 2) :- nonTieni(V, fiori). 
discard_HELPER(V, 1) :- nonTieni(V, quadri).
discard_HELPER(V, 4) :- nonTieni(V, picche).
%%% PER FARE ANDARE EMBASP 

%mano(10,cuori).
%mano(4,fiori).
%mano(4,cuori).
%mano(3,cuori).
%mano(7,cuori).

:- mano(VALORE, SEME), not carta(VALORE, SEME).

%:- N1 = #count{ V, S : tieni(V, S) }, N2 = #count{ V, S : nonTieni(V, S) },N3 = #count{ V, S : mano(V, S) }, N3 != N1 + N2.

coppia(V, S1, S2) :- mano(V, S1), mano(V, S2), S1 < S2.
dCoppia(V1, V2) :- coppia(V1, _, _), coppia(V2, _, _), V1 < V2.
tris(V, S1, S2, S3) :- mano(V, S1), mano(V, S2), mano(V, S3), S1 < S2, S2 < S3.
scala :- mano(V1, _), mano(V2, _), mano(V3, _), mano(V4, _), mano(V5, _), V2 = V1 + 1, V3 = V2 + 1, V4 = V3 + 1, V5 = V4 + 1.
full :- coppia(_, _, _), tris(_, _, _, _).
colore :- mano(V1, S), mano(V2, S), mano(V3, S), mano(V4, S), mano(V5, S), V1 < V2, V2 < V3, V3 < V4, V4 < V5.
poker(V, S1, S2, S3, S4) :- mano(V, S1), mano(V, S2), mano(V, S3), mano(V, S4), S1 < S2, S2 < S3, S3 < S4.
scalaReale :- scala, colore. %probabilmente inutile[una volta che hai la scala è sicuro che non scarti]

:~ nonTieni(V, S), coppia(V, _, _). [1@3, V, S]
:~ nonTieni(V, S), dCoppia(V, _). [2@3, V, S]
:~ nonTieni(V, S), dCoppia(_, V). [2@3, V, S]
:~ nonTieni(V, S), tris(V, _, _, _). [3@3, V, S]
:~ nonTieni(V, S), scala. [4@3, V, S]
:~ nonTieni(V, S), full. [5@3, V, S]
:~ nonTieni(_, _), colore. [6@3]
:~ nonTieni(V, S), poker(V, _, _, _, _). [7@3, V, S]
%:~ scalaReale???

used(V) :- coppia(V, _, _).
used(V) :- tris(V, _, _, _).
used(V) :- poker(V, _, _, _, _).

:~ nonTieni(V, S), V >= #max{ X, Y : tieni(X, Y) }. [1@1, V, S]
:~ tieni(V, S), V < #max{ X, Y : nonTieni(X, Y) }. [1@1, V, S]
:~ tieni(V, S), V < #max{ X, Y : tieni(X, Y) }. [1@1, V, S]

possibileColore(S) :- seme(S), #count{ V : mano(V, S) } >= 3.
:~ possibileColore(S), nonTieni(V, S). [5@2, V, S]
:~ possibileColore(S), tieni(V, SX), S != SX. [5@2, V, SX, S]


carta(VALORE, SEME) :- valore(VALORE), seme(SEME).
valore(1..13).
seme(cuori). seme(quadri). seme(fiori). seme(picche).

%oppure carta(ID, VALORE, SEME).
%id(1..52).
%carta(ID, V, S) :- valore(V), seme(S), id(ID), carta(IDN, _, _), ID != IDN. 