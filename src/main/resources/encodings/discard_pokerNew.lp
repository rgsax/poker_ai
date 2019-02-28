mano(V, cuori) :- in_hand_HELPER(V, hearts).
mano(V, fiori) :- in_hand_HELPER(V, clubs).
mano(V, quadri) :- in_hand_HELPER(V, diamonds).
mano(V, picche) :- in_hand_HELPER(V, spades).

discard_HELPER(V, hearts) :- scarta(V, cuori).
discard_HELPER(V, clubs) :- scarta(V, fiori).
discard_HELPER(V, diamonds) :- scarta(V, quadri).
discard_HELPER(V, spades) :- scarta(V, picche).


seme(cuori). 
seme(quadri). 
seme(fiori). 
seme(picche).

tieni(V, S) | scarta(V, S) :- mano(V, S).

%:- N1 = #count{ V, S : tieni(V, S) }, N2 = #count{ V, S : scarta(V, S) }, N1 + N2 != 5.

cartaAlta(V) :- V = #max{ X : mano(X, S) }.
coppia(V) :- mano(V, S1), mano(V, S2), S1 != S2.
doppiaCoppia(V1, V2) :- coppia(V1), coppia(V2), V1 != V2.
tris(V) :- mano(V, S1), mano(V, S2), mano(V, S3), S1 != S2, S2 != S3, S1 != S3.
scala :- mano(V1, _), mano(V2, _), mano(V3, _), mano(V4, _), mano(V5, _),
			V1 = V2 + 1, V2 = V3 + 1, V3 = V4 + 1, V4 = V5 + 1.
colore(S) :- mano(V1, S), mano(V2, S), mano(V3, S), mano(V4, S), mano(V5, S), 
			V1 < V2, V2 < V3, V3 < V4, V4 < V5.
full(VC, VT) :- coppia(VC), tris(VT), VC != VT.
poker(V) :- mano(V, S1), mano(V, S2), mano(V, S3), mano(V, S4), 
			S1 != S2, S1 != S3, S1 != S4, S2 != S3, S2 != S4, S3 != S4.
scalaReale :- scala, colore(_).

:~ scalaReale, scarta(_, _). [90@10]
:~ poker(V), scarta(V, _). [80@10]
:~ full(VC, VT), scarta(VC, _), scarta(VT, _). [70@10]
:~ colore(S), scarta(_, S). [60@10]
:~ scala, scarta(_, _). [50@10]
:~ tris(V), scarta(V, _). [40@10]
:~ doppiaCoppia(V1, V2), scarta(V1, _), scarta(V2, _). [30@10]
:~ coppia(V), scarta(V, _). [20@10]
:~ cartaAlta(V), tieni(V, S), V < 12. [10@5, V]
:~ cartaAlta(V), scarta(V, S), V >= 12. [10@5, V]
:~ tieni(V, _), not cartaAlta(V). [5@5, V]

%possibileColore(S) :- seme(S), #count{ V : tieni(V, S) } >= 4.
%:~ possibileColore(S), scarta(_, S). [45@10]

%segue(V1, V2) :- mano(V1, _), mano(V2, _), V1 = V2 + 1.
%catena(V1, V2) :- segue(V1, V2).
%catena(V1, V3) :- catena(V1, V2), catena(V2, V3).
%maxLunghezza(VMIN, VMAX) :- catena(VMIN, VMAX), VMAX = VMIN + N, N = #max{ X : catena(V1, V2), X = V2 - V1 }.
%scalaVicina(VMIN, VMAX) :- maxLunghezza(VMIN, VMAX), VMAX - VMIN >= 4.
%:~ scalaVicina(VMIN, VMAX), scarta(V, _), V >= VMIN, V <= VMAX. [45@10, VMIN]




